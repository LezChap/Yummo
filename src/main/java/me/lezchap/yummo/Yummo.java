package me.lezchap.yummo;

import me.lezchap.yummo.commands.TabCompleter;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Yummo extends JavaPlugin {

    private static Logger logger;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Config.setConfigFolder(this.getDataFolder().getAbsolutePath());
        Config.reloadConfigs();
        Config.FOOD_STORED_KEY = new NamespacedKey(this, "food-stored");

        ValuesConfig.setup();
        ValuesConfig.getConfigFile().options().copyDefaults(true);
        ValuesConfig.saveConfigFile();

        this.getCommand("yummo").setExecutor(new Commands());
        this.getCommand("yummo").setTabCompleter(new TabCompleter());

        this.getServer().getPluginManager().registerEvents(new Events(), this);

        this.logger = this.getLogger();
    }

    public static Logger Logger() {
        return logger;
    }

}
