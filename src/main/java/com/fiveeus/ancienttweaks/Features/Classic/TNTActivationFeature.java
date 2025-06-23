package com.fiveeus.ancienttweaks.Features.Classic;

import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;

import com.fiveeus.ancienttweaks.Events.BlockBreakListener;
import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class TNTActivationFeature extends BaseFeature {

    public TNTActivationFeature(FileConfiguration fileCfg, Logger logger) {
        super(fileCfg, logger);

        listener = new BlockBreakListener(this);

        featureType = FeatureType.TNT_ACTIVATION;
        configEnabledStr = "old-tnt-activation";
        name = "Old TNT Activation";
    }

    @Override
    public void doFeature(Event e) {
        if (!isEnabled()) return;

        if (e instanceof BlockBreakEvent event) {
            if (event.getBlock().getType().equals(Material.TNT)) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
                event.getBlock().getWorld().spawnEntity(event.getBlock().getLocation(), EntityType.PRIMED_TNT);
            }
        }
    } 
}
