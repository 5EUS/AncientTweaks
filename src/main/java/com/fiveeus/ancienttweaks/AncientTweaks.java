package com.fiveeus.ancienttweaks;

import org.bukkit.plugin.java.JavaPlugin;

public class AncientTweaks extends JavaPlugin {
    
    @Override
    public void onEnable() {
        getLogger().info("AncientTweaks enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("AncientTweaks disabled.");
    }

}
