package me.lezchap.yummo;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ValuesConfig {
    private static File file;
    private static FileConfiguration config;

    public static void setup() {
        // Tries to find file
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Yummo").getDataFolder(), "foodvalues.yml");

        // Checks if it exists and if not tries to create one
        if(!file.exists()) {
            try {
                // Successfully created file
                file.createNewFile();
            } catch (IOException e) {
                // Something went wrong while creating the file
                Bukkit.getLogger().severe("Something went wrong while trying to create foodvalues.yml");
            }
        }

        // Assign the file to the config variable
        config = YamlConfiguration.loadConfiguration(file);
        setDefaults();
    }

    public static void saveConfigFile() {
        try {
            // Successfully saved file
            config.save(file);
            reloadConfigFile();
        } catch (IOException e) {
            // Something went wrong while creating the file
            Bukkit.getLogger().severe("Something went wrong while trying to save values.yml");
        }
    }

    public static void reloadConfigFile() {
        setup();
    }

    private static void setDefaults() {
        addConfigItem("apple", 6.4f);
        addConfigItem("baked_potato", 11);
        addConfigItem("beetroot", 2.2f);
        addConfigItem("beetroot_soup", 13.2f);
        addConfigItem("bread", 11);
        addConfigItem("cake", 16.8f);
        addConfigItem("carrot", 6.6f);
        addConfigItem("chorus_fruit", 6.4f);
        addConfigItem("cooked_beef", 20.8f);
        addConfigItem("cooked_chicken", 13.2f);
        addConfigItem("cooked_cod", 11);
        addConfigItem("cooked_mutton", 15.6f);
        addConfigItem("cooked_porkchop", 20.8f);
        addConfigItem("cooked_rabbit", 11);
        addConfigItem("cooked_salmon", 15.6f);
        addConfigItem("cookie", 2.4f);
        addConfigItem("dried_kelp", 1.6f);
        addConfigItem("enchanted_golden_apple", 13.6f);
        addConfigItem("glow_berries", 2.4f);
        addConfigItem("golden_apple", 13.6f);
        addConfigItem("golden_carrot", 20.4f);
        addConfigItem("honey_bottle", 7.2f);
        addConfigItem("melon_slice", 3.2f);
        addConfigItem("mushroom_stew", 13.2f);
        addConfigItem("poisonous_potato", 3.2f);
        addConfigItem("potato", 1.6f);
        addConfigItem("pufferfish", 1.2f);
        addConfigItem("pumpkin_pie", 12.8f);
        addConfigItem("rabbit_stew", 22);
        addConfigItem("beef", 4.8f);
        addConfigItem("chicken", 3.2f);
        addConfigItem("cod", 2.4f);
        addConfigItem("mutton", 3.2f);
        addConfigItem("porkchop", 4.8f);
        addConfigItem("rabbit", 4.8f);
        addConfigItem("salmon", 2.4f);
        addConfigItem("rotten_flesh", 4.8f);
        addConfigItem("spider_eye", 5.2f);
        addConfigItem("suspicious_stew", 13.2f);
        addConfigItem("sweet_berries", 2.4f);
        addConfigItem("tropical_fish", 1.2f);
    }

    private static void addConfigItem(String name, float value) {
        config.addDefault(name + ".value", value);
    }

    public static FileConfiguration getConfigFile() {
        return config;
    }
}
