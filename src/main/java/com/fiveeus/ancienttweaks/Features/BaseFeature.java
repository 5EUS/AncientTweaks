package com.fiveeus.ancienttweaks.Features;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Events.BaseListener;

public abstract class BaseFeature {

    protected Boolean isEnabled;

    protected String name;
    protected FeatureType featureType;
    protected String configEnabledStr;
    protected BaseListener listener;
    protected Boolean mustReload = false;

    protected static final Logger logger = AncientTweaks.getPluginInstance().getLogger();

    public abstract void doFeature(Event e);

    public BaseFeature(FileConfiguration fileCfg) {
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
                isEnabled = section.getBoolean(key);
            }
        }
    }

    protected final void logEnabled()
    {
        String enabled = isEnabled ? "enabled" : "disabled";
        logger.log(Level.INFO, "Feature {0} has successfully loaded and {1}", new String[]{ name, enabled });
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
        reload(true);
    }

    public String getName() {
        return name;
    }

    public Boolean isEnabled() {
        return isEnabled;
    }

    public Boolean mustReload() {
        return mustReload;
    }

    public FeatureType getFeatureType() {
        return featureType;
    }
    
    public BaseListener getListener() {
        return listener;
    }

    public void reload(Boolean startNow) {}
}
