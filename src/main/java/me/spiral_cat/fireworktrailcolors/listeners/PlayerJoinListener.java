package me.spiral_cat.fireworktrailcolors.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener
{
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		FireworkTriggerListener.initializePlayerTime(event.getPlayer(), 1000);
	}
}
