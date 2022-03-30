package me.spiral_cat.fireworktrailcolors;

import org.bukkit.plugin.java.JavaPlugin;

import me.spiral_cat.fireworktrailcolors.executors.ToggleCommandExecutor;
import me.spiral_cat.fireworktrailcolors.listeners.FireworkTriggerListener;
import me.spiral_cat.fireworktrailcolors.listeners.PlayerJoinListener;

public final class FireworkTrailColors extends JavaPlugin
{
	private static FireworkTrailColors instance;
	
	public static FireworkTrailColors getInstance()
	{
		return FireworkTrailColors.instance;
	}
	
	private static void setInstance(FireworkTrailColors instance)
	{
		FireworkTrailColors.instance = instance;
	}
	
	@Override
	public void onEnable()
	{
		FireworkTrailColors.setInstance(this);
		
		getServer().getPluginManager().registerEvents(new FireworkTriggerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginCommand("FireworkTrailColors").setExecutor(new ToggleCommandExecutor());
	}
	
	@Override
	public void onDisable()
	{}
}
