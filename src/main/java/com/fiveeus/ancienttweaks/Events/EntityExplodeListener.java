package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class EntityExplodeListener extends BaseListener {

    public EntityExplodeListener(BaseFeature feature) {
        super(feature);
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        feature.doFeature(event);
    }
}
