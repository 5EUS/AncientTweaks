package com.fiveeus.ancienttweaks.Features;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.file.FileConfiguration;

public class Features {

    private final Set<BaseFeature> features = new HashSet<>();

    public Features(FileConfiguration fileCfg) {
        loadFeatures(fileCfg);
    }

    public Boolean isFeatureEnabled(FeatureType featureType) {
        return features.stream().anyMatch(
                feature -> feature.getFeatureType() == featureType
                && feature.isEnabled());
    }

    public final void loadFeatures(FileConfiguration fileCfg) {
        features.clear();

        for (FeatureType type : FeatureType.values()) {
            type.instantiate(fileCfg).ifPresent(features::add);
        }
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

    public void restartTasks(Boolean startNow) {
        features.stream()
                .filter(feature -> feature.mustReload)
                .forEach(reloadFeature -> {
                    reloadFeature.reload(startNow);
                });
    }

}
