package org.shark.marioPlugin.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.shark.marioPlugin.MarioPlugin;

import java.util.Optional;

public class PlayerSneakToggle implements Listener {

    private final MarioPlugin plugin;

    public PlayerSneakToggle(MarioPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSneak(PlayerToggleSneakEvent event) {
        var player = event.getPlayer();
        var item = this.getHeldItem(player);
        if (item == null) return;

        if (item.getType() != Material.RED_MUSHROOM) {
            return;
        }

        if (player.hasMetadata("is_changing_size")) {
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            item.setAmount(item.getAmount() - 1);
        }

        player.setMetadata("is_changing_size", new org.bukkit.metadata.FixedMetadataValue(plugin, true));
        this.plugin.getSizeChange().startSizeAnimation(player);
    }

    public ItemStack getHeldItem(Player player) {
        PlayerInventory inventory = player.getInventory();

        ItemStack mainHand = inventory.getItemInMainHand();
        if (mainHand.getType() != Material.AIR) {
            return mainHand;
        }

        ItemStack offHand = inventory.getItemInOffHand();
        if (offHand.getType() != Material.AIR) {
            return offHand;
        }

        return null;
    }
}
