package com.fiveeus.ancienttweaks.Features;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Features.Classic.FallingBlockFeature;
import com.fiveeus.ancienttweaks.Features.Classic.IronDoorFeature;
import com.fiveeus.ancienttweaks.Features.Classic.OldLava;
import com.fiveeus.ancienttweaks.Features.Classic.OldWater;
import com.fiveeus.ancienttweaks.Features.Classic.TNTActivationFeature;

public enum FeatureType {
    
    NONE(null),
    OLDLAVA(OldLava.class),
    OLDWATER(OldWater.class),
    FALLINGBLOCK(FallingBlockFeature.class),
    TNT_ACTIVATION(TNTActivationFeature.class),
    IRON_DOOR(IronDoorFeature.class);

    private static final Logger logger = AncientTweaks.getPluginInstance().getLogger();
    private final Class<? extends BaseFeature> featureClass;

    FeatureType(Class<? extends BaseFeature> featureClass) {
        this.featureClass = featureClass;
    }

    public Optional<BaseFeature> instantiate(FileConfiguration config) {

        if (featureClass == null) return java.util.Optional.empty();

        try {
            Constructor<? extends BaseFeature> ctor = featureClass.getConstructor(FileConfiguration.class);
            return Optional.of(ctor.newInstance(config));

        } catch (IllegalAccessException | IllegalArgumentException 
                | InstantiationException | NoSuchMethodException 
                | SecurityException | InvocationTargetException e) {
            logger.log(Level.WARNING, "Could not instantiate feature: {0} - {1}", new Object[]{name(), e.getMessage()});
            return Optional.empty();
        }

    }

    public Class<? extends BaseFeature> getFeatureClass() {
        return featureClass;
    }
}

