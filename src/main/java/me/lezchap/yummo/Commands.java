package me.lezchap.yummo;

import me.lezchap.yummo.commands.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("yummo")) {
            if (args.length > 0) {
                switch (args[0].toLowerCase()) {
                    case "help":
                            return HelpCommand.showHelp(sender, args);
                    case "food":
                        return FoodCommand.showFoodCommand(sender, args);
                    case "debug":
                        return DebugCommand.showDebug(sender, args);
                    case "maxfood":
                        return MaxFoodCommand.showMaxFood(sender, args);
                    case "reload":
                        if (sender.hasPermission("yummo.reload")) {
                            Config.reloadConfigs();
                            ValuesConfig.reloadConfigFile();
                            sender.sendMessage(ChatColor.YELLOW + "Yummo Configs Reloaded.");
                        } else {
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                    ChatColor.YELLOW + "yummo.reload");
                        }
                        return true;

                    default:  //for /yummo <NAME> <TYPE> [color] to spawn helmets
                        if (sender.hasPermission("yummo.spawnhelm")) {
                            return SpawnHelmCommand.showHelmCommand(sender, args);
                        } else {
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                    ChatColor.YELLOW + "yummo.spawnhelm");
                            return true;
                        }
                }
            }
        }
        return false;
    }
}
