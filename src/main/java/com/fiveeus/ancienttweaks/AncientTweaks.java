package com.fiveeus.ancienttweaks;

import org.bukkit.plugin.java.JavaPlugin;

import com.fiveeus.ancienttweaks.Commands.CommandManager;
import com.fiveeus.ancienttweaks.Commands.FeatureCommand;
import com.fiveeus.ancienttweaks.Events.ListenerHelper;
import com.fiveeus.ancienttweaks.Features.Features;

public class AncientTweaks extends JavaPlugin {

    private Features featureList;
    private CommandManager commandManager;
    
    @Override
    public void onEnable() {

        commandManager = new CommandManager();
        featureList = new Features(getConfig(), getLogger());
        
        saveDefaultConfig();

        ListenerHelper.registerListeners(getServer().getPluginManager(), this, featureList);
        registerCommands();
        
        getLogger().info("AncientTweaks enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("AncientTweaks disabled.");
    }

    public void registerCommands() {
        commandManager.register(getCommand("features"), new FeatureCommand(featureList));
    }
}
