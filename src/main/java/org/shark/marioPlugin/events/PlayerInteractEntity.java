package org.shark.marioPlugin.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.shark.marioPlugin.MarioPlugin;

public class PlayerInteractEntity implements Listener {

    private final MarioPlugin plugin;

    public PlayerInteractEntity(MarioPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        var player = event.getPlayer();
        ItemStack mainHand = player.getInventory().getItemInMainHand();
        ItemStack offHand = player.getInventory().getItemInOffHand();

        EquipmentSlot hand = event.getHand();
        ItemStack item = (hand == EquipmentSlot.HAND)
                ? player.getInventory().getItemInMainHand()
                : player.getInventory().getItemInOffHand();

        if (mainHand.getType() == Material.RED_MUSHROOM || offHand.getType() == Material.RED_MUSHROOM) {

            if (event.getRightClicked() instanceof LivingEntity livingEntity) {

                if (livingEntity.hasMetadata("is_changing_size")) {
                    return;
                }

                if (player.getGameMode() != GameMode.CREATIVE) {
                    item.setAmount(item.getAmount() - 1);
                }

                livingEntity.setMetadata("is_changing_size", new org.bukkit.metadata.FixedMetadataValue(plugin, true));
                this.plugin.getSizeChange().startSizeAnimation(livingEntity);
            }
        }
    }


}