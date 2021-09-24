package me.lezchap.yummo;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class Config {
    private static YamlConfiguration mainConfig;
    private static final String MAIN_CONFIG = "config.yml";
    private static String configFolder;

    static NamespacedKey FOOD_STORED_KEY;

    public static ChatColor SAVORY_COLOR = ChatColor.DARK_GREEN;
    public static ChatColor FULL_GAUGE_COLOR = ChatColor.DARK_GREEN;
    public static ChatColor EMPTY_GAUGE_COLOR = ChatColor.BLACK;
    public static ChatColor GAUGE_END_COLOR = ChatColor.GOLD;

    static String FOOD_STORED_LORE;
    public static float MAX_FOOD;

    public static ChatColor CHAT_SUCCESS_COLOR = ChatColor.GREEN;
    public static ChatColor CHAT_FAIL_COLOR = ChatColor.RED;

    static void setConfigFolder(String configFolder) {
        Config.configFolder = configFolder;
    }

    static void reloadConfigs() {
        mainConfig = load(configFolder + "/" + MAIN_CONFIG);

        SAVORY_COLOR = ChatColor.getByChar(mainConfig.getString("savory_color").charAt(1));
        FULL_GAUGE_COLOR = ChatColor.getByChar(mainConfig.getString("full_gauge_color").charAt(1));
        EMPTY_GAUGE_COLOR = ChatColor.getByChar(mainConfig.getString("empty_gauge_color").charAt(1));
        GAUGE_END_COLOR = ChatColor.getByChar(mainConfig.getString("gauge_end_color").charAt(1));

        FOOD_STORED_LORE = GAUGE_END_COLOR + "["  + FULL_GAUGE_COLOR;

        MAX_FOOD = (float)mainConfig.getDouble("max_food");

        CHAT_SUCCESS_COLOR = ChatColor.getByChar(mainConfig.getString("success_color").charAt(1));
        CHAT_FAIL_COLOR = ChatColor.getByChar(mainConfig.getString("fail_color").charAt(1));
    }

    private static YamlConfiguration load(String FileLocation) {
        File f = new File(FileLocation);
        YamlConfiguration cfg;
        cfg = setDefaults();
        if (!f.exists()) {
            try {
                f.getParentFile().mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (f.createNewFile()) {
                    Bukkit.getServer().getLogger().log(Level.INFO, "[Yummo] New Config Created at: " + FileLocation);
                    cfg.save(new File(FileLocation));
                } else {
                    Bukkit.getServer().getLogger().log(Level.SEVERE, "[Yummo] Failed to create Config file at: " + FileLocation);
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else {
            try {
                cfg.load(f);
            } catch (IOException | InvalidConfigurationException e) {
                e.printStackTrace();
            }
        }
        return cfg;
    }

    public static void save() {
        HashMap< String, Object > toSave = new HashMap<>();

        toSave.put("savory_color", SAVORY_COLOR.toString());
        toSave.put("full_gauge_color", FULL_GAUGE_COLOR.toString());
        toSave.put("empty_gauge_color", EMPTY_GAUGE_COLOR.toString());
        toSave.put("gauge_end_color", GAUGE_END_COLOR.toString());

        toSave.put("success_color", CHAT_SUCCESS_COLOR.toString());
        toSave.put("fail_color", CHAT_FAIL_COLOR.toString());

        toSave.put("max_food", MAX_FOOD );

        for (Map.Entry < String, Object > entry:toSave.entrySet())
        {
            mainConfig.set(entry.getKey(), entry.getValue());
        }

        try {
            mainConfig.save(configFolder + "/" + MAIN_CONFIG);
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static YamlConfiguration setDefaults() {
        YamlConfiguration config = new YamlConfiguration();
        HashMap< String, Object > defaults = new HashMap<>();

        defaults.put("savory_color", ChatColor.DARK_GREEN.toString());
        defaults.put("full_gauge_color", ChatColor.DARK_GREEN.toString());
        defaults.put("empty_gauge_color", ChatColor.BLACK.toString());
        defaults.put("gauge_end_color", ChatColor.GOLD.toString());

        defaults.put("success_color", ChatColor.GREEN.toString());
        defaults.put("fail_color", ChatColor.RED.toString());

        defaults.put("max_food", 1331.2d );

        for (Map.Entry < String, Object > entry:defaults.entrySet())
        {
            config.set(entry.getKey(), entry.getValue());
        }

        return config;
    }

    public YamlConfiguration getConfig(){
        return mainConfig;
    }
}
