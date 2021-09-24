package me.lezchap.yummo.commands;

import me.lezchap.yummo.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class HelpCommand {

    public static boolean showHelp(CommandSender sender, String[] args){
        if (sender.hasPermission("yummo.help")) {
            sender.sendMessage(ChatColor.YELLOW + "All Commands for the Yummo Plugin:");
            sender.sendMessage(ChatColor.YELLOW + "/yummo help - shows this menu");
            sender.sendMessage(ChatColor.YELLOW + "/yummo reload - reloads the config files");
            sender.sendMessage(ChatColor.YELLOW + "/yummo debug - debugging info");
            sender.sendMessage(ChatColor.YELLOW + "/yummo maxfood [#] - shows or (optionally) changes the MaxFood value for Helms");
            sender.sendMessage(ChatColor.YELLOW + "/yummo food get <name> - looks up the food value of that item");
            sender.sendMessage(ChatColor.YELLOW + "/yummo food set <name> # - Adds or changes a food item to the configs");
            sender.sendMessage(ChatColor.YELLOW + "/yummo food list - lists all food items available.");
            sender.sendMessage(ChatColor.YELLOW + "/yummo food remove <name> - attempts to remove food from the list");
            sender.sendMessage(ChatColor.YELLOW + "/yummo <user> <type> [color] - Gives USER a helm of the type.");
            sender.sendMessage(ChatColor.YELLOW + "   Types: Netherite, Diamond, Gold, Iron, Chain, and Leather.");
            sender.sendMessage(ChatColor.YELLOW + "   Leather Armor can be dyed with an optional RGB code, a color from");
            sender.sendMessage(ChatColor.YELLOW + "   the tab-list, or 'random'. If no valid color is given, none is used.");
        } else {
            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                    ChatColor.YELLOW + "yummo.help");
        }
        return true;
    }
}
