package me.spiral_cat.fireworktrailcolors;

import me.spiral_cat.fireworktrailcolors.Rainbow.RainbowMaker;
import org.bukkit.plugin.java.JavaPlugin;

import me.spiral_cat.fireworktrailcolors.executors.ToggleCommandExecutor;
import me.spiral_cat.fireworktrailcolors.listeners.FireworkTriggerListener;
import me.spiral_cat.fireworktrailcolors.listeners.PlayerJoinListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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

	public static List<Color> color = new ArrayList<>(); //sets up list of rainbow

	@Override
	public void onEnable()
	{
		FireworkTrailColors.setInstance(this);

		color = RainbowMaker.InitRainbow(); //returns List of rainbow rgb codes
		getServer().getPluginManager().registerEvents(new FireworkTriggerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getServer().getPluginCommand("FireworkTrailColors").setExecutor(new ToggleCommandExecutor());
	}
	
	@Override
	public void onDisable()
	{}
}
