package com.fiveeus.ancienttweaks.Features.Classic;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;

import com.fiveeus.ancienttweaks.Config.ConfigHelper;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class OldLava extends LiquidFeature {

    private Material lavaMaterial = Material.LAVA;

    private final Map<Material, Integer> meltingLevels;
    private final Map<Integer, Integer> meltDelays;

    private final Set<Material> protectedMaterials;

    public OldLava(FileConfiguration fileCfg, Logger logger) {
        super(fileCfg, logger);

        featureType = FeatureType.OLDLAVA;
        configEnabledStr = "old-lava";
        name = "Old Lava";

        meltingLevels = ConfigHelper.getMeltingLevels(fileCfg, logger);
        meltDelays = ConfigHelper.getMeltDelays(fileCfg, logger);
        protectedMaterials = ConfigHelper.getProtectedMaterials(fileCfg, logger);
    }

    @Override
    public void doFeature(Event e) {

        if (!isEnabled) return;

        if (e instanceof BlockFromToEvent event) {

            event.setCancelled(true);

            Block from = event.getBlock();

            if (from.getType() != lavaMaterial) {
                return;
            }

            for (Block rel : getRelatives(from)) {

                if (isMaterialProtected(rel.getType())) {
                    continue;
                }

                rel.setType(lavaMaterial);
            }  
        }
    }

    public void setLavaMaterial(Material mat) {
        lavaMaterial = mat;
    }

    public Integer getMeltDelay(Material mat) {
        Integer level = meltingLevels.get(mat);
        return level != null ? meltDelays.getOrDefault(level, 100) : null;
    }

    public Boolean isMaterialProtected(Material mat) {
        return protectedMaterials.contains(mat);
    }
}
