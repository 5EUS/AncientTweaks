package com.fiveeus.ancienttweaks.Features;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Events.BaseListener;

/**
 * BaseFeature is an abstract class that represents a feature in the AncientTweaks plugin.
 * It provides common functionality for all features, such as enabling/disabling,
 * logging, and handling events.
 */
public abstract class BaseFeature {

    /**
     * Indicates whether the feature is enabled or disabled.
     * This is set based on the configuration file and can be changed at runtime.
     */
    protected Boolean isEnabled;

    /**
     * The listener that handles events for this feature.
     * It is initialized in the constructor of the concrete feature class.
     */
    protected BaseListener listener;

    /**
     * Indicates whether the feature must be reloaded.
     * This is set to true if the feature's configuration changes and requires a reload.
     */
    protected Boolean mustReload = false;

    /**
     * The name of the feature, used for logging and identification.
     * Corresponds to the enum FeatureType name.
     */

    private final String name;
    /**
     * The string used in the configuration file to enable/disable this feature.
     * Also corresponds to the enum FeatureType name.
     */
    private final String configEnabledStr;

    /**
     * The type of the feature, represented by the FeatureType enum.
     * This is used to categorize and manage different features in the plugin.
     */
    private final FeatureType featureType;

    protected static final Logger logger = AncientTweaks.getPluginInstance().getLogger();

    public abstract void doFeature(Event e);

    public BaseFeature(FileConfiguration fileCfg, FeatureType featureType) {
        setEnabled(fileCfg);
        this.featureType = featureType;
        this.name = featureType.getName();
        this.configEnabledStr = featureType.getConfigEnabledStr();
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
