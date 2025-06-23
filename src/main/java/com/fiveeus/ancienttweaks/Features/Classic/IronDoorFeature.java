package com.fiveeus.ancienttweaks.Features.Classic;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockDamageEvent;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Events.BlockDamageListener;
import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class IronDoorFeature extends BaseFeature {

    public IronDoorFeature(FileConfiguration fileCfg, Logger logger) {
        super(fileCfg, logger);

        listener = new BlockDamageListener(this);

        featureType = FeatureType.IRON_DOOR;
        configEnabledStr = "iron-door";
        name = "Iron Doors";
    }

    @Override
    public void doFeature(Event e) {

        if (!isEnabled()) {
            return;
        }

        if (e instanceof BlockDamageEvent event) {

            if (!event.getBlock().getType().equals(Material.IRON_BLOCK)) {
                return;
            }

            if ((!event.getPlayer().isSneaking())) {
                getBlocks(event.getBlock());
            }
        }
    }

    private void getBlocks(Block block) {
        if (block.getType() != Material.IRON_BLOCK) return;

        processBlock(block);
    }

    private final BlockFace[] faces = {
        BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST,
        BlockFace.WEST, BlockFace.UP, BlockFace.DOWN
    };

    private void processBlock(Block block) {
        for (BlockFace face : faces) {
            Block relative = block.getRelative(face);
            if (relative.getType() == Material.IRON_BLOCK) {
                temporarilyBreak(block);
                getBlocks(relative);
            }
        }

        if (block.getType() == Material.IRON_BLOCK) {
            temporarilyBreak(block);
        }
    }

    private void temporarilyBreak(Block block) {
        block.setType(Material.AIR);
        Bukkit.getScheduler().runTaskLater(AncientTweaks.getPluginInstance(), 
            () -> block.setType(Material.IRON_BLOCK), 20L);
    }
}
