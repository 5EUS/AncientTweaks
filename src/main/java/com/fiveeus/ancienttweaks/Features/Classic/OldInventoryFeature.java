package com.fiveeus.ancienttweaks.Features.Classic;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.fiveeus.ancienttweaks.Events.InventoryOpenListener;
import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class OldInventoryFeature extends BaseFeature {

    public OldInventoryFeature(FileConfiguration fileCfg, FeatureType featureType) {
        super(fileCfg, featureType);

        listener = new InventoryOpenListener(this);

        logEnabled();
    }

    @Override
    public void doFeature(Event e) {
        if (!isEnabled()) return;

        if (e instanceof InventoryOpenEvent event) {
            
        }
    } 
}
