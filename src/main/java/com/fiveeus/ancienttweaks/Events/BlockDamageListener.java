package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class BlockDamageListener extends BaseListener {

    public BlockDamageListener(BaseFeature feature) {
        super(feature);
    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent event) {
        feature.doFeature(event);
    }
}
