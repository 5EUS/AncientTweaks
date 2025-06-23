package com.fiveeus.ancienttweaks.Features;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;

import com.fiveeus.ancienttweaks.Features.Classic.FallingBlockFeature;
import com.fiveeus.ancienttweaks.Features.Classic.IronDoorFeature;
import com.fiveeus.ancienttweaks.Features.Classic.OldLava;
import com.fiveeus.ancienttweaks.Features.Classic.OldWater;
import com.fiveeus.ancienttweaks.Features.Classic.TNTActivationFeature;

public class Features {

    private final Set<BaseFeature> features = new HashSet<>();

    public Features(FileConfiguration fileCfg, Logger logger) {
        loadFeatures(fileCfg, logger);
    }

    public Boolean isFeatureEnabled(FeatureType featureType) {
        return features.stream().anyMatch(
            feature -> feature.getFeatureType() == featureType 
            && feature.isEnabled());
    }

    public final void loadFeatures(FileConfiguration fileCfg, Logger logger) {
        features.clear();
        features.add(new OldLava(fileCfg, logger));
        features.add(new OldWater(fileCfg, logger));
        features.add(new FallingBlockFeature(fileCfg, logger));
        features.add(new TNTActivationFeature(fileCfg, logger));
        features.add(new IronDoorFeature(fileCfg, logger));
    }

    public BaseFeature getFeature(FeatureType featureType) {
        return features.stream()
            .filter(feature -> feature.getFeatureType() == featureType)
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("Feature not found: " + featureType));
    } 

    public Set<BaseFeature> getFeatures() {
        return features;
    }
    
}
