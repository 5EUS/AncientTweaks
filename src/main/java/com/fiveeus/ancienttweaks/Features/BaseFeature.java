package com.fiveeus.ancienttweaks.Features;

import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;

import com.fiveeus.ancienttweaks.Events.BaseListener;

public abstract class BaseFeature {

    protected Boolean isEnabled;

    protected String name;
    protected FeatureType featureType;
    protected String configEnabledStr;
    protected BaseListener listener;

    public abstract void doFeature(Event e);

    public BaseFeature(FileConfiguration fileCfg, Logger logger) {
        setEnabled(fileCfg);
    }

    public final void setEnabled(FileConfiguration fileCfg) {

        ConfigurationSection section = fileCfg.getConfigurationSection("features");
        if (section == null) {
            throw new IllegalStateException("Could not find features config section!");
        }

        Set<String> keys = section.getKeys(false);
        isEnabled = false;
        for (String key : keys) {
            if (key.equals(configEnabledStr)) {
                isEnabled = Boolean.valueOf(key);
            }
        }
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    public String getName() {
        return name;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }
    
    public BaseListener getListener() {
        return listener;
    }
}
