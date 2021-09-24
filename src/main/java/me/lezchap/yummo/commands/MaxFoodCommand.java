package me.lezchap.yummo.commands;

import me.lezchap.yummo.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MaxFoodCommand {

    public static boolean showMaxFood(CommandSender sender, String[] args) {
        if (sender.hasPermission("yummo.maxfood")) {
            if (args.length == 2) {
                float parse;
                try {
                    parse = Float.parseFloat(args[1]);
                    Config.MAX_FOOD = parse;
                    Config.save();
                } catch (Exception e) {
                    sender.sendMessage(Config.CHAT_FAIL_COLOR + "Invalid Number");
                }
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Current Max Food value for helms: " +
                        ChatColor.WHITE + Config.MAX_FOOD);
            }
            return true;
        } else {
            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                    ChatColor.YELLOW + "yummo.maxfood");
        }
        return  false;
    }
}
