package me.lezchap.yummo.commands;

import me.lezchap.yummo.Config;
import me.lezchap.yummo.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;
import java.util.List;

public class SpawnHelmCommand {

    public static boolean showHelmCommand(CommandSender sender, String[] args) {
        List<String> colors = Arrays.asList(
                "Aqua", "Black", "Blue", "Fuchsia", "Gray", "Green", "Lime", "Maroon", "Navy",
                "Olive", "Orange", "Purple", "Red", "Silver", "Teal", "White", "Yellow");

        if (args.length >= 2) {
            Player receiver = Bukkit.getPlayer(args[0]);
            if (receiver != null) {
                ItemStack helm;
                switch (args[1].toLowerCase()) {
                    case "leather":
                        helm = Utils.createItemStack("leather", Config.SAVORY_COLOR + "Savory I");
                        Color dye = null;
                        if (args.length == 3) {
                            if (Utils.containsIgnoreCase(colors, args[2])) {
                                dye = DyeColor.valueOf(args[2].toUpperCase()).getColor();
                            }
                            if (args[2].equalsIgnoreCase("random")) {
                                dye = Color.fromRGB((int)(Math.random() * 0x1000000));
                            }
                        }
                        if (args.length >= 5) {
                            dye = Utils.isValidColor(args[2], args[3], args[4]);
                        }
                        if (dye != null) {
                            LeatherArmorMeta meta = (LeatherArmorMeta) helm.getItemMeta();
                            meta.setColor(dye);
                            helm.setItemMeta(meta);
                        }
                        sendMessage(sender, receiver, "Savory Leather Cap", Config.SAVORY_COLOR, giveHelm(receiver, helm));
                        return true;
                    case "netherite":
                        helm = Utils.createItemStack("netherite", Config.SAVORY_COLOR + "Savory I");
                        sendMessage(sender, receiver, "Savory Netherite Helmet", Config.SAVORY_COLOR, giveHelm(receiver, helm));
                        return true;
                    case "diamond":
                        helm = Utils.createItemStack("diamond", Config.SAVORY_COLOR + "Savory I");
                        sendMessage(sender, receiver, "Savory Diamond Helmet", Config.SAVORY_COLOR, giveHelm(receiver, helm));
                        return true;
                    case "gold":
                        helm = Utils.createItemStack("gold", Config.SAVORY_COLOR + "Savory I");
                        sendMessage(sender, receiver, "Savory Gold Helmet", Config.SAVORY_COLOR, giveHelm(receiver, helm));
                        return true;
                    case "iron":
                        helm = Utils.createItemStack("iron", Config.SAVORY_COLOR + "Savory I");
                        sendMessage(sender, receiver, "Savory Iron Helmet", Config.SAVORY_COLOR, giveHelm(receiver, helm));
                        return true;
                    case "chain":
                        helm = Utils.createItemStack("chain", Config.SAVORY_COLOR + "Savory I");
                        sendMessage(sender, receiver, "Savory Chainmail Helmet", Config.SAVORY_COLOR, giveHelm(receiver, helm));
                        return true;
                    default:
                        sender.sendMessage(Config.CHAT_FAIL_COLOR+ "Invalid helm type!" + Color.ORANGE + " Available options: netherite, " +
                                "diamond, gold, iron, chain, and leather");
                        sender.sendMessage(Config.CHAT_FAIL_COLOR + "Usage: /yummo <player> <type> [color]");
                        return true;
                }
            } else {
                sender.sendMessage(Config.CHAT_FAIL_COLOR + "Could not find player '" + args[0] + "'");
                sender.sendMessage(Config.CHAT_FAIL_COLOR + "Usage: /yummo <player> <type> [color]");
            }
        }
        return false;
    }

    private static void sendMessage(CommandSender sender, Player receiver, String helmType, ChatColor helmColor, boolean success)
    {
        if (success)
        {
            receiver.sendMessage(Config.CHAT_SUCCESS_COLOR + "[Added a " + helmColor + helmType + Config.CHAT_SUCCESS_COLOR + " to your Inventory]");
            sender.sendMessage(Config.CHAT_SUCCESS_COLOR + "[" + helmColor + helmType + Config.CHAT_SUCCESS_COLOR + " Helm sent to " +
                    receiver.getName() + "]");
        } else
        {
            receiver.sendMessage(Config.CHAT_FAIL_COLOR + "[Your inventory is full! " + helmColor + helmType +
                    Config.CHAT_FAIL_COLOR + " has been dropped!]");
            sender.sendMessage(Config.CHAT_FAIL_COLOR + "[" + receiver.getName() + "'s inventory was full! " + helmColor + helmType +
                    Config.CHAT_FAIL_COLOR+ " was dropped]");
        }
    }

    private static boolean giveHelm(Player receiver, ItemStack helm)
    {
        if (Utils.isSpaceAvailable(receiver, helm))
        {
            receiver.getInventory().addItem(helm);
            receiver.updateInventory();
            return true;

        } else
        {
            receiver.getWorld().dropItemNaturally(receiver.getLocation(), helm);
            return false;
        }
    }
}
