package me.lezchap.yummo.commands;

import me.lezchap.yummo.Utils;
import me.lezchap.yummo.ValuesConfig;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Stream;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> empty = new ArrayList<>();
        List<String> playerNames = new ArrayList<>();
        Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
        Bukkit.getServer().getOnlinePlayers().toArray(players);
        for (int i = 0; i < players.length; i++) {
            playerNames.add(players[i].getName());
        }



        if (args.length > 3 ) {
            return empty;
        }

        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("food") &&
                    (args[1].equalsIgnoreCase("get") ||
                        args[1].equalsIgnoreCase("remove") ||
                        args[1].equalsIgnoreCase("set"))) {
                FileConfiguration valConfig = ValuesConfig.getConfigFile();
                List<String> list = new ArrayList<>(valConfig.getKeys(false));

                return list;
            }
            if (((Utils.containsIgnoreCase(playerNames, args[0])) &&
                    sender.hasPermission("yummo.spawnhelm")) &&
                    args[1].equalsIgnoreCase("leather")) {
                DyeColor[] dyes = DyeColor.values();
                List<String> dyeColors = new ArrayList<>();
                for (int i = 0; i <dyes.length; i++) {
                    dyeColors.add(dyes[i].name().toLowerCase());
                }
                dyeColors.add("random");
                return dyeColors;

            }
            return empty;
        }


        if (args.length == 2) {
            if ((Utils.containsIgnoreCase(playerNames, args[0])) &&
                    sender.hasPermission("yummo.spawnhelm")) {
                List<String> types = new ArrayList<>();
                types.add("leather");
                types.add("chain");
                types.add("iron");
                types.add("gold");
                types.add("diamond");
                types.add("netherite");
                return types;
            }

            if (args[0].equalsIgnoreCase("food")) {
                List<String> types = new ArrayList<>();
                if (sender.hasPermission("yummo.food.list")) {
                    types.add("list");
                }
                if (sender.hasPermission("yummo.food.get")) {
                    types.add("get");
                }
                if (sender.hasPermission("yummo.food.set")) {
                    types.add("set");
                }
                if (sender.hasPermission("yummo.food.remove")) {
                    types.add("remove");
                }
                return types;
            }
            return empty;
        }


        if (args.length == 1) {
            List<String> firstArg = new ArrayList<>();
            if (sender.hasPermission("yummo.spawnhelm")) {
                ListIterator<String> iterator = playerNames.listIterator();
                while (iterator.hasNext()) {
                    firstArg.add(iterator.next().toLowerCase());
                }
            }
            if (sender.hasPermission("yummo.help")) {
                firstArg.add("help");
            }
            if (sender.hasPermission("yummo.food.list") ||
                    sender.hasPermission("yummo.food.set") ||
                    sender.hasPermission("yummo.food.remove") ||
                    sender.hasPermission("yummo.food.get")) {
                firstArg.add("food");
            }
            if (sender.hasPermission("yummo.reload")) {
                firstArg.add("reload");
            }
            if (sender.hasPermission("yummo.maxfood")) {
                firstArg.add("maxfood");
            }
            if (sender.hasPermission("yummo.debug")) {
                firstArg.add("debug");
            }

            return firstArg;
        }
        return null;
    }
}
