package com.fiveeus.ancienttweaks.Features.Classic;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Config.ConfigHelper;
import com.fiveeus.ancienttweaks.Events.LiquidListener;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class OldLava extends LiquidFeature {

    private Material lavaMaterial;

    private final Map<Material, Integer> meltingLevels;
    private final Map<Integer, Integer> meltDelays;
    private final Map<Material, Material> meltTransformations;

    private final Set<Material> protectedMaterials;

    public record BlockPos(int x, int y, int z, World world) {

        public Block toBlock() {
            return world.getBlockAt(x, y, z);
        }

        public static BlockPos fromBlock(Block block) {
            return new BlockPos(block.getX(), block.getY(), block.getZ(), block.getWorld());
        }
    }
    private final Set<BlockPos> visited = new HashSet<>();

    public OldLava(FileConfiguration fileCfg) {
        super(fileCfg);

        listener = new LiquidListener(this);
        featureType = FeatureType.OLDLAVA;
        configEnabledStr = "old-lava";
        name = "Old Lava";
        mustReload = true;

        meltingLevels = ConfigHelper.getMeltingLevels(fileCfg, logger);
        meltDelays = ConfigHelper.getMeltDelays(fileCfg, logger);
        meltTransformations = ConfigHelper.getTransformationMap(fileCfg, logger);
        lavaMaterial = Material.LAVA;
        protectedMaterials = ConfigHelper.getProtectedMaterials(fileCfg, logger);

        startTask(false);

        logEnabled();
    }

    @Override
    public void doFeature(Event e) {
        if (!isEnabled || !(e instanceof BlockFromToEvent event)) {
            return;
        }

        Block startBlock = event.getBlock();
        if (startBlock.getType() != lavaMaterial) {
            return;
        }

        event.setCancelled(true);
        BlockPos start = BlockPos.fromBlock(startBlock);

        if (!visited.add(start)) {
            return;
        }

        propagate(startBlock);
    }

    private void propagate(Block startBlock) {
        for (Block rel : getRelatives(startBlock)) {
            Material mat = rel.getType();
            if (isMaterialProtected(mat)) {
                continue;
            }

            Integer delay = getMeltDelay(mat);

            if (mat == Material.AIR || delay == null) {
                rel.setType(lavaMaterial);
            } else {
                startTransformationTask(rel, mat, delay);
            }

            if (airBlocks(rel) == 0) {
                Bukkit.getScheduler().runTaskLater(
                    AncientTweaks.getPluginInstance(), 
                    () -> {
                        propagate(rel);
                    }, 30);
            }
        }
    }

    @Override
    public final void reload(Boolean startNow) {
        startTask(true);
    }

    private void startTask(Boolean startNow) {
        if (!isEnabled) {
            return;
        }

        logger.log(Level.INFO, "Starting OldLava task...");

        int initialMins = startNow ? 0 : 1;

        Bukkit.getScheduler().runTaskTimer(
                AncientTweaks.getPluginInstance(),
                () -> {
                    visited.clear();
                    logger.log(Level.INFO, "OldLava task executed");
                }, // TODO config option for delay
                20L * 60 * initialMins, // BUG startNow not working on init
                20L * 60 * 1
        );
    }

    private void startTransformationTask(Block rel, Material mat, int delay) {
        Bukkit.getScheduler().runTaskLater(AncientTweaks.getPluginInstance(), () -> {
            if (isEnabled() && rel.getType() == mat && mat != Material.AIR && mat != lavaMaterial) {

                Material result = meltTransformations.get(mat);
                if (result == null) {
                    result = lavaMaterial;
                }

                rel.setType(result);
                startTransformationTask(rel, result, 60); // TODO coal delay config
            }
        }, delay);
    }

    private int airBlocks(Block b) {
        int result = 0;
        Block[] relatives = getRelatives(b);
        for (Block rel : relatives) {
            Material mat = rel.getType();
            if (mat == Material.AIR) {
                result++;
            }
        }
        return result;
    }

    public void setLavaMaterial(Material mat) {
        lavaMaterial = mat;
    }

    public Integer getMeltDelay(Material mat) {
        Integer level = meltingLevels.get(mat);
        return level != null ? meltDelays.getOrDefault(level, 0) : null;
    }

    public Boolean isMaterialProtected(Material mat) {
        return protectedMaterials.contains(mat);
    }
}
