package me.lezchap.yummo.commands;

import me.lezchap.yummo.Config;
import me.lezchap.yummo.YummoHelm;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DebugCommand {

    public static boolean showDebug(CommandSender sender, String[] args) {
        if (sender.hasPermission("yummo.debug") && sender instanceof Player) {
            Player p = (((Player) sender).getPlayer());
            ItemStack helm =  p.getInventory().getHelmet();
            String helmSlot = "Worn";
            if (helm == null || !YummoHelm.isHelm(helm)) {
                helm = p.getInventory().getItemInMainHand();
                helmSlot = "in Main Hand";
            }

            if (helm == null || !YummoHelm.isHelm(helm)) {
                helm = p.getInventory().getItemInOffHand();
                helmSlot = "in Off Hand";
            }
            if (helm == null || !YummoHelm.isHelm(helm)) {
                sender.sendMessage(Config.CHAT_FAIL_COLOR + "No valid helm found worn or in hand.");
                return true;
            }
            float storedFood = YummoHelm.getFood(helm);
            sender.sendMessage(ChatColor.YELLOW + "Helmet found " + ChatColor.WHITE + helmSlot + ChatColor.YELLOW +
                    " contains " + ChatColor.WHITE + storedFood + ChatColor.YELLOW + " of " +
                    ChatColor.WHITE + Config.MAX_FOOD + " stored food value. " + Math.round((storedFood/Config.MAX_FOOD)*100) +
                    "% full.");
            return true;
        } else {
            if (sender instanceof Player) {
                sender.sendMessage(Config.CHAT_FAIL_COLOR + "Missing command permission: " + ChatColor.YELLOW + "yummo.debug");
            } else {
                sender.sendMessage(Config.CHAT_FAIL_COLOR + "Must be a player to run this command.");
            }
            return true;
        }
    }
}
