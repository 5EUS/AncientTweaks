package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class LavaListener extends BaseListener {

    public LavaListener(BaseFeature feature) {
        super(feature);
        featureType = FeatureType.OLDLAVA;
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        feature.doFeature(event);
    }
}
