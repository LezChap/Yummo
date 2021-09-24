package me.lezchap.yummo.commands;

import me.lezchap.yummo.Config;
import me.lezchap.yummo.ValuesConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Set;

public class FoodCommand {

    public static boolean showFoodCommand(CommandSender sender, String[] args) {
        if (args.length == 2 ) {
            FileConfiguration valConfig = ValuesConfig.getConfigFile();
            if (args[1].equalsIgnoreCase("list")) {
                if (sender.hasPermission("yummo.food.list")) {
                    Set<String> list = valConfig.getKeys(false);
                    String listJoined = String.join(", ", list);
                    sender.sendMessage(ChatColor.YELLOW + "Found the following foods: " + ChatColor.WHITE + listJoined);
                    return true;
                } else {
                    sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                            ChatColor.YELLOW + "yummo.food.list");
                }
            }
        }

        if (args.length >= 3 ) {
            FileConfiguration valConfig = ValuesConfig.getConfigFile();
            String foodname = args[2].toLowerCase();
            switch (args[1].toLowerCase()) {
                case "set":
                    if (sender.hasPermission("yummo.food.set")) {
                        if (!args[3].isBlank()) {
                            Float parsedValue;
                            try { //make sure the last input is a number
                                parsedValue = Float.parseFloat(args[3]);
                            } catch (NumberFormatException e) {
                                sender.sendMessage(Config.CHAT_FAIL_COLOR + "Bad Command Format.  Correct format is '/yummo food set <name> #' " +
                                        "and no number was found.");
                                return true;
                            }
                            if (valConfig.getString(foodname) != null) {
                                //food already exists
                                double oldVal = valConfig.getDouble(foodname + ".value");
                                if (oldVal == parsedValue) {
                                    sender.sendMessage(Config.CHAT_FAIL_COLOR + "Same food value as in configs, no changes made to " + foodname);
                                } else {
                                    valConfig.set(foodname + ".value", parsedValue);
                                    ValuesConfig.saveConfigFile();
                                    sender.sendMessage(ChatColor.YELLOW + "Food Value for " + ChatColor.WHITE + foodname +
                                            ChatColor.YELLOW + " has been set to " + ChatColor.WHITE + parsedValue);
                                }
                            } else {
                                valConfig.set(foodname + ".value", parsedValue);
                                ValuesConfig.saveConfigFile();
                                sender.sendMessage(ChatColor.YELLOW + "Adding food " + ChatColor.WHITE + foodname +
                                        ChatColor.YELLOW + " with a value of " + ChatColor.WHITE + parsedValue);
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
                        if (valConfig.getString(foodname) != null) {
                            sender.sendMessage(ChatColor.YELLOW + "The food " + ChatColor.WHITE + foodname +
                                    ChatColor.YELLOW + " was found and has the value of " + ChatColor.WHITE + valConfig.getDouble(foodname + ".value"));
                        } else {
                            sender.sendMessage(ChatColor.YELLOW + "The food " + ChatColor.WHITE + foodname + ChatColor.YELLOW + " was not found!");
                        }
                    } else {
                        sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " +
                                ChatColor.YELLOW + "yummo.food.get");
                    }
                    return true;

                case "remove":
                    if (sender.hasPermission("yummo.food.remove")) {
                        if (valConfig.getString(foodname) != null) {
                            valConfig.set(foodname, null);
                            ValuesConfig.saveConfigFile();
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "REMOVED THE FOOD " + ChatColor.WHITE + foodname.toUpperCase() +
                                    Config.CHAT_FAIL_COLOR + " FROM THE CONFIGS!");
                        } else {
                            sender.sendMessage(Config.CHAT_FAIL_COLOR + "Couldn't find any food by the name " + ChatColor.WHITE + foodname +
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
