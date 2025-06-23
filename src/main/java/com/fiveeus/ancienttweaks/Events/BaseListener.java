package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.Listener;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class BaseListener implements Listener {
    
    protected BaseFeature feature;

    public BaseListener(BaseFeature feature) {
        this.feature = feature;
    }

}
