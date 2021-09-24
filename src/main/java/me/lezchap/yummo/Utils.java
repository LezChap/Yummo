package me.lezchap.yummo;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static ItemStack createItemStack(String type, String... lores) {
        ItemStack stack;
        switch (type) {
            case "netherite":
                stack = new ItemStack(Material.NETHERITE_HELMET, 1);
                break;
            case "diamond":
                stack = new ItemStack(Material.DIAMOND_HELMET, 1);
                break;
            case "gold":
                stack = new ItemStack(Material.GOLDEN_HELMET, 1);
                break;
            case "iron":
                stack = new ItemStack(Material.IRON_HELMET, 1);
                break;
            case "chain":
                stack = new ItemStack(Material.CHAINMAIL_HELMET, 1);
                break;
            default:
                stack = new ItemStack(Material.LEATHER_HELMET, 1);
                break;
        }

        ItemMeta im = stack.getItemMeta();
        im.setDisplayName("");
        ArrayList<String> lore = new ArrayList<>();
        int n = lores.length;
        int n2 = 0;
        while (n2 < n) {
            String str = lores[n2];
            lore.add(str);
            ++n2;
        }
        im.setLore(lore);
        YummoHelm yh = new YummoHelm();
        yh.updateLore(stack, im);
        stack.setItemMeta(im);
        return stack;
    }

    public static boolean isSpaceAvailable(Player player, ItemStack item) {
        //Exclude armor slots - ids 100, 101, 102, 103 - Normal Inventory is slots 0-35
        for (int i = 0; i <= 35; i++) {
            ItemStack slotItem = player.getInventory().getItem(i);
            if (slotItem == null || (slotItem.getType() == item.getType()
                    && item.getAmount() + slotItem.getAmount() <= slotItem.getMaxStackSize())) {
                return true;
            }
        }
        return false;
    }

    public static Color isValidColor(String red, String green, String blue) {
        int ri, gi, bi;
        try { //make sure the last input is a number
            ri = Integer.parseInt(red);
            gi = Integer.parseInt(green);
            bi = Integer.parseInt(blue);
        } catch (NumberFormatException e) {
            return null;
        }
        return Color.fromRGB(ri, gi, bi);
    }

    public static boolean containsIgnoreCase(List<String> list, String str){
        for(String i : list){
            if(i.equalsIgnoreCase(str))
                return true;
        }
        return false;
    }
}