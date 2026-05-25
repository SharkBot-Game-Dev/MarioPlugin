package org.shark.marioPlugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.shark.marioPlugin.events.PlayerInteractEntity;
import org.shark.marioPlugin.events.PlayerSneakToggle;
import org.shark.marioPlugin.lib.SizeChange;

public final class MarioPlugin extends JavaPlugin {
    SizeChange sizeChange;

    @Override
    public void onEnable() {
        sizeChange = new SizeChange(this);

        Bukkit.getPluginManager().registerEvents(new PlayerInteractEntity(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerSneakToggle(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public SizeChange getSizeChange() {
        return sizeChange;
    }
}
