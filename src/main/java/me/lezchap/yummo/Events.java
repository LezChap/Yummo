package me.lezchap.yummo;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener {
    //Events(){}

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        ItemStack helm = e.getItem();

        if (helm == null) {
            return;
        }

        if ((e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
                && (YummoHelm.isHelm(helm))) {
            YummoHelm h = new YummoHelm();
            if (h.hasSpace(helm)) {
                h.addFood(e);
            } else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Config.CHAT_SUCCESS_COLOR + "[Yummo Helm is Sated]"));
                //player.sendMessage("Your Helm is already full of food!");
            }
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent e) {
        HumanEntity entity = e.getEntity();
        ItemStack helm = entity.getInventory().getHelmet();
        if (helm == null) { return; }
        if (entity instanceof Player && YummoHelm.isHelm(helm)) {
            if (entity.getFoodLevel() < 20) {
                YummoHelm h = new YummoHelm();
                if (h.hasFood(helm)) {
                    h.useFood(e);
                    e.setCancelled(true);
                }
            }
        }
    }

}
