package com.fiveeus.ancienttweaks.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.fiveeus.ancienttweaks.AncientTweaks;
import com.fiveeus.ancienttweaks.Features.BaseFeature;
import com.fiveeus.ancienttweaks.Features.FeatureType;
import com.fiveeus.ancienttweaks.Features.Features;

import net.md_5.bungee.api.ChatColor;

public class FeatureCommand extends PlayerCommand {

    private final Features featuresList;

    public FeatureCommand(Features features) {
        featuresList = features;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!checkUsage(sender)) {
            return true;
        }

        Player player = (Player) sender;

        if (!checkPermission(player, "af.features.base")) {
            return true;
        }

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        // TODO subpermissions
        if (args[0].equalsIgnoreCase("list")) {
            sendList(player);
            return true;
        } else if (args[0].equalsIgnoreCase("enable")) {
            setFeature(player, args, true);
            return true;
        } else if (args[0].equalsIgnoreCase("disable")) {
            setFeature(player, args, false);
            return true;
        }
        else if (args[0].equalsIgnoreCase("reload")) {
            reloadFeature(player, args);
            return true;
        } else {
            player.sendMessage(invalidSyntax);
            sendUsage(player);
        }

        return true;
    }

    private void sendUsage(Player player) {

        player.sendMessage("Features Command Help");
        player.sendMessage("/features list - lists loaded features");
        player.sendMessage("/feature enable <feature> - enables a given feature");
        player.sendMessage("/feature disable <feature> - disables a given feature");
        player.sendMessage("/feature reload <feature> - reloads a given feature");

    }

    private void sendList(Player player) {
        player.sendMessage("Loaded features:");
        for (BaseFeature feature : featuresList.getFeatures()) {
            String enabled = ChatColor.RED + "disabled";
            if (feature.isEnabled()) {
                enabled = ChatColor.GREEN + "enabled";
            }
            player.sendMessage(feature.getName() + " (" + feature.getFeatureType() + ") - " + enabled);
        }
    }

    private BaseFeature[] getFeaturesFromInput(Player player, String[] args) {
        if (args.length < 2) {
            player.sendMessage(invalidSyntax);
            sendUsage(player);
            return null;
        }

        BaseFeature[] features = new BaseFeature[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            FeatureType type;
            try {
                type = FeatureType.valueOf(args[i].toUpperCase());
            } catch (IllegalArgumentException e) {
                player.sendMessage(ChatColor.RED + "Invalid feature type!");
                return null;
            }

            BaseFeature feature = featuresList.getFeature(type);
            if (feature == null) {
                player.sendMessage(ChatColor.RED + "Could not find feature " + ChatColor.WHITE + args[i]);
                return null;
            }
            features[i - 1] = feature;
        }

        return features;
    }

    private void setFeature(Player player, String[] args, Boolean enabled) {
        BaseFeature[] features = getFeaturesFromInput(player, args);
        if (features == null || features.length == 0) {
            return;
        }

        String enabledStr = ChatColor.RED + "Disabled ";
        if (enabled) {
            enabledStr = ChatColor.GREEN + "Enabled ";
        }
        else {
            Bukkit.getScheduler().cancelTasks(AncientTweaks.getPluginInstance());
            featuresList.restartTasks(true);
        }

        for (BaseFeature feature : features) {
            feature.setEnabled(enabled);
            player.sendMessage(enabledStr + ChatColor.AQUA + feature.getName());
        }
    }

    private void reloadFeature(Player player, String[] args) {
        BaseFeature[] features = getFeaturesFromInput(player, args);
        if (features == null || features.length == 0) {
            return;
        }

        for (BaseFeature feature : features) {
            if (!feature.mustReload()) {
                player.sendMessage(ChatColor.RED + "Feature " + ChatColor.AQUA + feature.getName() 
                    + ChatColor.RED + " does not support reload.");
                return;
            }

            if (!feature.isEnabled()) {
                player.sendMessage(ChatColor.RED + "Feature " + ChatColor.AQUA + feature.getName() 
                    + ChatColor.RED + " is not enabled. Please enable it first.");
                return;
            }

            feature.reload(true);
            player.sendMessage(ChatColor.GREEN + "Reloaded " + ChatColor.AQUA + feature.getName());
        }   
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            return Arrays.asList("list", "enable", "disable", "reload").stream()
                .filter(option -> option.startsWith(args[0].toLowerCase()))
                .toList();
        }

        if (args.length >= 2 && (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable") 
                || args[0].equalsIgnoreCase("reload"))) {
            List<String> result = new ArrayList<>();
            for (int i = 1; i < args.length; i++) {
                int index = i;
                result.addAll(featuresList.getFeatures().stream()
                    .map(BaseFeature::getFeatureType)
                    .map(FeatureType::toString)
                    .filter(name -> name.toLowerCase().startsWith(args[index].toLowerCase()))
                    .toList());
            }
            return result;
        }

        return Collections.emptyList();
    }

}
