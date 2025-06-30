package com.fiveeus.ancienttweaks.Config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigHelper {

    public static Map<Material, Integer> getMeltingLevels(FileConfiguration config, Logger logger) {

        Map<Material, Integer> result = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("melting-levels");
        if (section == null) {
            logger.log(Level.SEVERE, "Cannot find config section: melting-levels");
            throw new IllegalStateException("Cannot find config section: melting-levels");
        }

        for (String levelStr : section.getKeys(false)) {
            int level = Integer.parseInt(levelStr);
            List<String> materials = section.getStringList(levelStr);
            for (String matName : materials) {
                Material material = Material.matchMaterial(matName);
                if (material != null) {
                    result.put(material, level);
                    logger.log(Level.INFO, "Adding melting level: {0} -> {1}", new Object[]{material, level});
                } else {
                    logger.log(Level.WARNING, "Unknown material in config: {0}", matName);
                }
            }
        }

        return result;
    }

    public static Map<Integer, Integer> getMeltDelays(FileConfiguration config, Logger logger) {

        Map<Integer, Integer> result = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("melt-delays");
        if (section == null) {
            logger.log(Level.SEVERE, "Cannot find config section: melt-delays");
            throw new IllegalStateException("Cannot find config section: melt-delays");
        }

        for (String key : section.getKeys(false)) {
            try {
                int level = Integer.parseInt(key);
                int delay = section.getInt(key);
                result.put(level, delay);
                logger.log(Level.INFO, "Adding melt delay: {0} -> {1}", new Object[]{level, delay});
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid delay level: {0}", key);
            }
        }

        return result;
    }

    public static Set<Material> getProtectedMaterials(FileConfiguration config, Logger logger) {

        Set<Material> result = new HashSet<>();

        List<String> materialStrings = config.getStringList("protected-materials");
        if (materialStrings.isEmpty()) {
            logger.log(Level.SEVERE, "Cannot find config section: protected-materials");
            return result;
        }

        for (String key : materialStrings) {
            Material mat = Material.matchMaterial(key);
            if (mat == null) {
                logger.log(Level.WARNING, "Invalid material in protected-materials: {0}", key);
                continue;
            }
            result.add(mat);
            logger.log(Level.INFO, "Adding protected material: {0}", mat);
        }

        return result;
    }

    public static Map<Material, Material> getTransformationMap(FileConfiguration config, Logger logger) {
        
        Map<Material, Material> result = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("melt-transformations");
        if (section == null) {
            logger.log(Level.SEVERE, "Cannot find config section: melt-transformations");
            throw new IllegalStateException("Cannot find config section: melt-transformations");
        }

        Boolean wildcardUsed = false;

        for (String key : section.getKeys(false)) {

            Material keyMaterial;
            Material valueMaterial;
            try {

                valueMaterial = Material.valueOf(section.getString(key));

                if (key.equalsIgnoreCase("default") && !wildcardUsed) {
                    result.put(Material.AIR, valueMaterial);
                    result.put(valueMaterial, Material.AIR);
                    wildcardUsed = true; // no error thrown, will just ignore them and document it somewhere :)
                    logger.log(Level.INFO, "Using wildcard transformation for default: {0} -> {1}", new Object[]{Material.AIR, valueMaterial});
                    continue;
                }

                keyMaterial = Material.valueOf(key);

            } catch (IllegalArgumentException e) {
                logger.log(Level.WARNING, "Invalid entry {0} find config section: melt-transformations", key);
                continue;
            }

            result.put(keyMaterial, valueMaterial);
            logger.log(Level.INFO, "Adding transformation: {0} -> {1}", new Object[]{keyMaterial, valueMaterial});
        }

        return result;
    }
}
