package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class LiquidListener extends BaseListener {

    public LiquidListener(BaseFeature feature) {
        super(feature);
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        feature.doFeature(event);
    }
}
