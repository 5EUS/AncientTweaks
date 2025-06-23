package com.fiveeus.ancienttweaks.Features.Classic;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockFromToEvent;

import com.fiveeus.ancienttweaks.Events.LiquidListener;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class OldWater extends LiquidFeature {

    private Material waterMaterial = Material.WATER;

    public OldWater(FileConfiguration fileCfg, Logger logger) {
        super(fileCfg, logger);

        listener = new LiquidListener(this);

        featureType = FeatureType.OLDWATER;
        configEnabledStr = "old-water";
        name = "Old Water";
    }

    @Override
    public void doFeature(Event e) {

        if (!isEnabled) return;

        if (e instanceof BlockFromToEvent event) {

            event.setCancelled(true);

            Block from = event.getBlock();

            if (from.getType() != waterMaterial) {
                return;
            }

            for (Block rel : getRelatives(from)) {

                if (!rel.getType().equals(Material.AIR)) {
                    continue;
                }

                rel.setType(waterMaterial);
            }  
        }
    }

    public void setWaterMaterial(Material mat) {
        waterMaterial = mat;
    }
}
