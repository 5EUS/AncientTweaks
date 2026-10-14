package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class InventoryOpenListener extends BaseListener {

    public InventoryOpenListener(BaseFeature feature) {
        super(feature);
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        feature.doFeature(event);
    }
}
