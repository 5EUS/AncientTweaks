package com.fiveeus.ancienttweaks.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public class BlockBreakListener extends BaseListener {

    public BlockBreakListener(BaseFeature feature) {
        super(feature);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        feature.doFeature(event);
    }
}
