package com.fiveeus.ancienttweaks.Events;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.fiveeus.ancienttweaks.Features.FeatureType;
import com.fiveeus.ancienttweaks.Features.Features;

public class ListenerHelper {
    
    public static void registerListeners(PluginManager mgr, JavaPlugin plugin, Features features) {
        
        mgr.registerEvents(new LavaListener(features.getFeature(FeatureType.OLDLAVA)), plugin);

    }

}
