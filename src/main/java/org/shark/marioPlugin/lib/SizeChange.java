package org.shark.marioPlugin.lib;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SizeChange {
    Plugin plugin;

    public SizeChange(Plugin plugin) {
        this.plugin = plugin;
    }

    public void startSizeAnimation(LivingEntity entity) {
        AttributeInstance scaleAttribute = entity.getAttribute(Attribute.SCALE);

        if (scaleAttribute == null) {
            entity.removeMetadata("is_changing_size", plugin);
            return;
        }

        new BukkitRunnable() {
            double currentScale = 1.0;
            final double targetScale = 3.0;
            final double step = 0.2;

            @Override
            public void run() {
                if (currentScale < targetScale) {
                    currentScale += step;
                    scaleAttribute.setBaseValue(currentScale);
                } else {
                    this.cancel();

                    new BukkitRunnable() {
                        @Override
                        public void run() {

                            new BukkitRunnable() {
                                double shrinkScale = targetScale;

                                @Override
                                public void run() {
                                    if (shrinkScale > 1.0) {
                                        shrinkScale -= step;
                                        scaleAttribute.setBaseValue(Math.max(shrinkScale, 1.0));
                                    } else {
                                        this.cancel();
                                        entity.removeMetadata("is_changing_size", plugin);
                                    }
                                }
                            }.runTaskTimer(plugin, 0L, 2L);

                        }
                    }.runTaskLater(plugin, 200L);
                }
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }
}
