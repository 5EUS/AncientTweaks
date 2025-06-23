package com.fiveeus.ancienttweaks.Features.Classic;

import java.util.logging.Logger;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;

import com.fiveeus.ancienttweaks.Features.BaseFeature;

public abstract class LiquidFeature extends BaseFeature {
    
    public LiquidFeature(FileConfiguration fileCfg, Logger logger) {
        super(fileCfg, logger);
    }

    protected final BlockFace[] blockFaces = new BlockFace[] {
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
}
