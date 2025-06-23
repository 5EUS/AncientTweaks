package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class FallingBlockListener extends BaseListener {

    public FallingBlockListener(BaseFeature feature) {
        super(feature);
    }

    @EventHandler
    public void onFall(EntityChangeBlockEvent event) {
        feature.doFeature(event);
    }
}
