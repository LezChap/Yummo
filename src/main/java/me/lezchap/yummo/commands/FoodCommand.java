package me.lezchap.yummo.commands;

import me.lezchap.yummo.Config;
import me.lezchap.yummo.Utils;
import me.lezchap.yummo.ValuesConfig;
import me.lezchap.yummo.Yummo;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class FoodCommand {

    public static boolean showFoodCommand(CommandSender sender, String[] args) {
        if (args.length >= 3 ) {
            FileConfiguration valConfig = ValuesConfig.getConfigFile();
            String foodName = args[2].toLowerCase();
            switch (args[1].toLowerCase()) {
                case "list":
                    if (sender.hasPermission("yummo.food.list")) {
                        Set<String> list = valConfig.getKeys(false);
                        String listJoined = String.join(", ", list);
                        sender.sendMessage(ChatColor.YELLOW + "Found the following foods: " + ChatColor.WHITE + listJoined);
                        return true;
                    } else {
                        sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                ChatColor.YELLOW + "yummo.food.list");
                    }
                case "set":
                    if (sender.hasPermission("yummo.food.set")) {
                        if (args.length == 4) {
                            float parsedValue;
                            try { //make sure the last input is a number
                                parsedValue = Float.parseFloat(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(Config.CHAT_FAIL_COLOR + "Bad Command Format.  Correct format is '/yummo food set <name> #' " +
                                        "and no number was found.");
                                return true;
                            }
                            if (valConfig.getString(foodName) != null) {
                                //food already exists
                                double oldVal = valConfig.getDouble(foodName + ".value");
                                if (Utils.areEqual(oldVal, parsedValue)) {
                                    sender.sendMessage(Config.CHAT_FAIL_COLOR + "Same food value as in configs, no changes made to " + foodName);
                                } else {
                                    valConfig.set(foodName + ".value", parsedValue);
                                    ValuesConfig.saveConfigFile();
                                    sender.sendMessage(ChatColor.YELLOW + "Food Value for " + ChatColor.WHITE + foodName +
                                            ChatColor.YELLOW + " has been set to " + ChatColor.WHITE + parsedValue);
                                    Yummo.Logger().info(sender.getName() + "set food '" + foodName + "' to " + parsedValue);
                                }
                            } else {
                                valConfig.set(foodName + ".value", parsedValue);
                                ValuesConfig.saveConfigFile();
                                sender.sendMessage(ChatColor.YELLOW + "Adding food " + ChatColor.WHITE + foodName +
                                        ChatColor.YELLOW + " with a value of " + ChatColor.WHITE + parsedValue);
                                Yummo.Logger().info(sender.getName() + "added food '" + foodName + "' to " + parsedValue);

                            }
                        } else {
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Bad Command Format.  Correct format is '/yummo food set <name> #'");
                        }
                    } else {
                        sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                ChatColor.YELLOW + "yummo.food.set");
                    }
                    return true;


                case "get":
                    if (sender.hasPermission("yummo.food.get")) {
                        if (valConfig.getString(foodName) != null) {
                            sender.sendMessage(ChatColor.YELLOW + "The food " + ChatColor.WHITE + foodName +
                                    ChatColor.YELLOW + " was found and has the value of " + ChatColor.WHITE + valConfig.getDouble(foodName + ".value"));
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "The food " + ChatColor.WHITE + foodName + ChatColor.YELLOW + " was not found!");
                        }
                    } else {
                        sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                ChatColor.YELLOW + "yummo.food.get");
                    }
                    return true;

                case "remove":
                    if (sender.hasPermission("yummo.food.remove")) {
                        if (valConfig.getString(foodName) != null) {
                            valConfig.set(foodName, null);
                            ValuesConfig.saveConfigFile();
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "REMOVED THE FOOD " + ChatColor.WHITE + foodName.toUpperCase() +
                                    Config.CHAT_FAIL_COLOR + " FROM THE YUMMO HELM CONFIGS!");
                            Yummo.Logger().info(sender.getName() + "removed food '" + foodName + "' from the configs");

                        } else {
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Couldn't find any food by the name " + ChatColor.WHITE + foodName +
                                    Config.CHAT_FAIL_COLOR + " in the configs!");
                        }
                    } else {
                        sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                ChatColor.YELLOW + "yummo.food.remove");
                    }
                    return true;

                case "default":
                    return false;
            }
        }
        return false;
    }
}
