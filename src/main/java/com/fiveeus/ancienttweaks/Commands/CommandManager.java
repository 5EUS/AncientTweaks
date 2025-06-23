package com.fiveeus.ancienttweaks.Commands;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

public class CommandManager implements CommandExecutor, TabCompleter {

    private final Map<String, CommandHandler> commands = new HashMap<>();

    public void register(PluginCommand command, CommandHandler handler) {
        commands.put(command.getName().toLowerCase(), handler);
        command.setExecutor(this);
        command.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        CommandHandler handler = commands.get(command.getName().toLowerCase());
        return handler != null && handler.onCommand(sender, command, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        CommandHandler handler = commands.get(command.getName().toLowerCase());
        return handler != null ? handler.onTabComplete(sender, command, alias, args) : Collections.emptyList();
    }

}
