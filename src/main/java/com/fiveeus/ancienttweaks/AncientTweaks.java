package com.fiveeus.ancienttweaks;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.fiveeus.ancienttweaks.Commands.CommandManager;
import com.fiveeus.ancienttweaks.Commands.FeatureCommand;
import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.Features;

public class AncientTweaks extends JavaPlugin {

    private static JavaPlugin instance;

    private Features featureList;
    private CommandManager commandManager;
    
    @Override
    public void onEnable() {

        instance = this;
        commandManager = new CommandManager();
        featureList = new Features(getConfig(), getLogger());
        
        saveDefaultConfig();

        registerListeners();
        registerCommands();
        
        getLogger().info("AncientTweaks enabled.");
    }

    @Override
    public void onDisable() {

        instance = null;

        getLogger().info("AncientTweaks disabled.");
    }

    public void registerCommands() {
        commandManager.register(getCommand("features"), new FeatureCommand(featureList));
    }

    public void registerListeners() {
        for (BaseFeature feature : featureList.getFeatures()) {
            getServer().getPluginManager().registerEvents(feature.getListener(), this);
        }
    }

    public static Plugin getPluginInstance() {
        return instance;
    }
}
