package me.lezchap.yummo;

import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class YummoHelm {

    public static boolean isHelm(ItemStack item)
    {
        return item != null
                && item.hasItemMeta()
                && item.getItemMeta().getLore().contains(Config.SAVORY_COLOR + "Savory I");
    }

    boolean hasSpace(ItemStack helm) {
        ItemMeta meta = helm.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        Float storedFood = data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT);
        if (storedFood == null)
            storedFood = 0f;
        return storedFood < Config.MAX_FOOD;
    }

    boolean hasFood(ItemStack helm) {
        ItemMeta meta = helm.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        Float storedFood = data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT);
        if (storedFood == null)
            storedFood = 0f;
        return storedFood > 0;
    }

    public static float getFood(ItemStack helm) {
        ItemMeta meta = helm.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        Float storedFood = data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT);
        if (storedFood == null)
            storedFood = 0f;
        return storedFood;
    }

    void addFood(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack helm = e.getItem();
        PlayerInventory inv = player.getInventory();

        FileConfiguration valConfig = ValuesConfig.getConfigFile();

        for(int i = 0 ; i < inv.getSize() ; i++) {
            ItemStack slot = inv.getItem(i);
            if (slot != null && slot.getType().isEdible()) {
                String foodName = slot.getType().name().toLowerCase();
                if (valConfig.getString(foodName) != null) {
                    // Don't remove item if player is in creative or spectator
                    if (player.getGameMode() != GameMode.CREATIVE || player.getGameMode() != GameMode.SPECTATOR)
                        slot.setAmount(slot.getAmount() - 1);
                    // Add food value to helm
                    ItemMeta meta = helm.getItemMeta();
                    PersistentDataContainer data = meta.getPersistentDataContainer();
                    if (data.has(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT)) {
                        double val = Math.min(data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT) + valConfig.getDouble(foodName +
                                ".value"), Config.MAX_FOOD);
                        data.set(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT, (float)val);
                    } else {
                        data.set(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT, (float)valConfig.getDouble(foodName + ".value"));
                    }
                    //player.sendMessage("Added: "+ valConfig.getDouble(foodName + ".value") + " Food points to helm. Helm total: " + data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT));
                    updateLore(helm, meta);
                    e.setCancelled(true);
                    return;
                }
            }
        }
    }

    void useFood(FoodLevelChangeEvent e) {
        Player player = (Player)e.getEntity();
        ItemStack helm = player.getInventory().getHelmet();
        ItemMeta meta = helm.getItemMeta();
        PersistentDataContainer data = meta.getPersistentDataContainer();

        int food = player.getFoodLevel();
        float sat = player.getSaturation();
        float need = (20 - food) + (20 - sat);
        float helmStored = data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT);

        if (need <= helmStored) {
            player.setFoodLevel(20);
            player.setSaturation(20);
            data.set(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT, (helmStored-need));
        } else {
            if (food <= helmStored) {
                player.setFoodLevel(20);
                player.setSaturation(sat + (helmStored - (20-food)));
            } else {
                player.setFoodLevel(food + (int)helmStored);
            }
            data.set(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT, 0f);
        }
        updateLore(helm, meta);
    }

    void updateLore(ItemStack helm, ItemMeta meta){
        PersistentDataContainer data = meta.getPersistentDataContainer();
        List<String> lore = meta.getLore();

        boolean foodValueExists = false;
        Float storedFood = data.get(Config.FOOD_STORED_KEY, PersistentDataType.FLOAT);
        if (storedFood == null)
            storedFood = 0f;
        for (int i = 0; i < lore.size(); i++) {
            String lore_line = lore.get(i);
            if (storedFood > -1 && (lore_line.contains(":::::") || lore_line.contains("|||||"))) {
                lore.set(i, createGaugeBar(storedFood));
                foodValueExists = true;
            }
        }
        if (!foodValueExists && storedFood > -1) lore.add(createGaugeBar(storedFood));

        meta.setLore(lore);
        helm.setItemMeta(meta);
    }

    String createGaugeBar(float storedFood) {
        float percFull = (storedFood/ Config.MAX_FOOD) * 100;  //Percent of helmet fill
        float barPerc = 100 / 18;  //Each bar counts as this many %
        int solidBars = Math.min(Math.round(percFull / barPerc), 18);  //Math.min in case server decreases MAX FOOD Config when existing helms are over cap

        String gauge = Config.FOOD_STORED_LORE;
        for (int i = 0; i < solidBars; i++) {
            gauge = gauge + "|";  //add full bars
        }
        gauge = gauge + Config.EMPTY_GAUGE_COLOR;
        for (int i = solidBars; i < 18; i++) {
            gauge = gauge + ":"; //add empty bars
        }
        gauge = gauge + Config.GAUGE_END_COLOR + "]";
        return gauge;
    }

}
