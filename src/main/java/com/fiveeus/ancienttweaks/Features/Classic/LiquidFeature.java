package com.fiveeus.ancienttweaks.Features.Classic;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Features.BaseFeature;

public abstract class LiquidFeature extends BaseFeature {

    private final int debugBlockDelay = 20;

    public LiquidFeature(FileConfiguration fileCfg) {
        super(fileCfg);
    }

    protected final BlockFace[] blockFaces = new BlockFace[]{
        BlockFace.DOWN,
        BlockFace.EAST,
        BlockFace.NORTH,
        BlockFace.SOUTH,
        BlockFace.WEST
    };

    protected final Block[] getRelatives(Block b) {
        Block[] result = new Block[blockFaces.length];
        for (int i = 0; i < blockFaces.length; i++) {
            result[i] = b.getRelative(blockFaces[i]);
        }
        return result;
    }

    protected void showDebugBlock(Block b) {
        Block above = b.getRelative(BlockFace.UP);
        Material before = above.getType();
        above.setType(Material.GREEN_WOOL);
        AncientTweaks.getPluginInstance().getServer().getScheduler().runTaskLater(AncientTweaks.getPluginInstance(), () -> {
            above.setType(before);
        }, debugBlockDelay);
    }
}
