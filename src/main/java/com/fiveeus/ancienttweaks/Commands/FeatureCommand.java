package com.fiveeus.ancienttweaks.Commands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if (args[0].equalsIgnoreCase("list")) {
            sendList(player);
            return true;
        } else if (args[0].equalsIgnoreCase("enable")) {
            setFeature(player, args, true);
            return true;
        } else if (args[0].equalsIgnoreCase("disable")) {
            setFeature(player, args, false);
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

    private void setFeature(Player player, String[] args, Boolean enabled) {
        if (args.length != 2) {
            player.sendMessage(invalidSyntax);
            sendUsage(player);
            return;
        }

        FeatureType type;
        try {
            type = FeatureType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid feature type!");
            return;
        }

        BaseFeature feature = featuresList.getFeature(type);
        if (feature == null) {
            player.sendMessage(ChatColor.RED + "Could not find feature " + ChatColor.WHITE + args[1]);
            return;
        }

        feature.setEnabled(enabled);

        String enabledStr = ChatColor.RED + "Disabled ";
        if (enabled) {
            enabledStr = ChatColor.GREEN + "Enabled ";
        }
        player.sendMessage(enabledStr + ChatColor.AQUA + feature.getName());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) {
            return Arrays.asList("list", "enable", "disable").stream()
                .filter(option -> option.startsWith(args[0].toLowerCase()))
                .toList();
        }

        if (args.length == 2) {

            if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("disable")) {
                return featuresList.getFeatures().stream()
                        .map(BaseFeature::getFeatureType)
                        .map(FeatureType::toString)
                        .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .toList();
            }

        }

        return Collections.emptyList();
    }

}
