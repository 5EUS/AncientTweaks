package com.fiveeus.ancienttweaks.Commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public abstract class PlayerCommand implements CommandHandler {

    protected String noPermission = ChatColor.RED + "No permission!"; // TODO config options
    protected String notPlayer = ChatColor.RED + "This command can only be run by a player.";
    protected String invalidSyntax = ChatColor.RED + "Invalid command syntax!";
    
    protected Boolean checkUsage(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(notPlayer);
            return false;
        }

        return true;
    }

    protected Boolean checkPermission(Player player, String permission) {
        if (!player.hasPermission(permission)) {
            player.sendMessage(noPermission);
            return false;
        }
        return true;
    }
}
