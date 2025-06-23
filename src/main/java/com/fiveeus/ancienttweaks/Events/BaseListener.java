package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.Listener;

import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;

public class BaseListener implements Listener {
    
    protected BaseFeature feature;
    protected FeatureType featureType;

    public BaseListener(BaseFeature feature) {
        this.feature = feature;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }

}
