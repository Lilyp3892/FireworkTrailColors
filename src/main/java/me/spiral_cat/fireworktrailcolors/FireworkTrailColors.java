package me.spiral_cat.fireworktrailcolors;

import org.bukkit.plugin.java.JavaPlugin;

public final class FireworkTrailColors extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new FireworkTriggerEvent(this), this); //enables this class
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this); //enables this class
        getServer().getPluginCommand("FireworkTrailColors").setExecutor(new ToggleCommandExecutor()); //enables this command and this class
    }

    @Override
    public void onDisable() {
    }
}
