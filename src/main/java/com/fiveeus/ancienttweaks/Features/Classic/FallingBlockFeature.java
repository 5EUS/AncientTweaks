package com.fiveeus.ancienttweaks.Features.Classic;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import com.fiveeus.ancienttweaks.Events.FallingBlockListener;
import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class FallingBlockFeature extends BaseFeature {

    public FallingBlockFeature(FileConfiguration fileCfg) {
        super(fileCfg);

        listener = new FallingBlockListener(this);

        featureType = FeatureType.FALLINGBLOCK;
        configEnabledStr = "falling-block";
        name = "Old Falling Blocks";
        logEnabled();
    }

    @Override
    public void doFeature(Event e) {
        if (!isEnabled()) return;

        if (e instanceof EntityChangeBlockEvent event) {
            Material type = event.getBlock().getType();
            if (type.equals(Material.SAND) || type.equals(Material.GRAVEL)) {
                event.setCancelled(true);
                event.getBlock().getState().update(false, false);
            }
        }
    }
}
