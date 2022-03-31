package me.spiral_cat.fireworktrailcolors.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;

import me.spiral_cat.fireworktrailcolors.FireworkTrailColors;
import me.spiral_cat.fireworktrailcolors.io.FileHandler;

import static me.spiral_cat.fireworktrailcolors.io.FileHandler.read;

public class FireworkTriggerListener implements Listener
{
	// Always use more generic classes from the class hierarchy for variable declaration.
	// Map > HashMap
	// List > ArrayList
	// Also, encapsulation is really important. Use private whenever possible instead of public and provide methods to change specific values instead.
	// Also, why did you use String -> Integer here instead of UUID -> Integer? It just clutters your code with a lot of .toString() calls.
	private static Map<UUID, Integer> timeLeft  = new HashMap<>();
	private static Map<UUID, Integer> taskIDMAP = new HashMap<>();
	
	@EventHandler
	public void onPlayerElytraBoost(PlayerElytraBoostEvent event)
	{
		if (timeLeft.get(event.getPlayer().getUniqueId()) == 1000 && FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getOn())
		{
			timeLeft.put(event.getPlayer().getUniqueId(), (event.getFirework().getFireworkMeta().getPower() * 20));
			taskIDMAP.put(event.getPlayer().getUniqueId(), startCountdown(event));
		}
	}
	
	public int startCountdown(PlayerElytraBoostEvent event)
	{
		return (Bukkit.getServer()
			.getScheduler()
			.scheduleSyncRepeatingTask(FireworkTrailColors.getInstance(), () ->
			{
				Player p = event.getPlayer();
				if (timeLeft.get(p.getUniqueId()) > 0)
				{
					if (read().getColorMap().get(event.getPlayer().getUniqueId()).getRed() == -2) {// checks if rainbow color
						double ColorValue = (timeLeft.get(event.getPlayer().getUniqueId()).doubleValue()/(event.getFirework().getFireworkMeta().getPower()*20)); //does some quick maths to get percent (in this case a number between 0 and 1) of the way through the time when the firework particles generate
						int RainbowRed = FireworkTrailColors.color.get((int) (ColorValue*600)).getRed(); //grabs the red rgb value from the list made in RainbowMaker and uses the ColorValue multiplied by the list length to get an accurate color
						int RainbowGreen = FireworkTrailColors.color.get((int) (ColorValue*600)).getGreen(); //grabs the green rgb value from the list made in RainbowMaker and uses the ColorValue multiplied by the list length to get an accurate color
						int RainbowBlue = FireworkTrailColors.color.get((int) (ColorValue*600)).getBlue(); //grabs the blue rgb value from the list made in RainbowMaker and uses the ColorValue multiplied by the list length to get an accurate color
						Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(RainbowRed, RainbowGreen, RainbowBlue), 1.0F); //gets the rainbow
						p.getWorld().spawnParticle(Particle.REDSTONE, event.getPlayer().getLocation(), 50, dustOptions); //spawns particles
					} else {
						int red = FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getRed();
						int green = FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getGreen();
						int blue = FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getBlue();
						Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(red, green, blue), 1.0F);
						p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 50, dustOptions);
					}
					timeLeft.put(p.getUniqueId(), timeLeft.get(p.getUniqueId()) - 1);
				}
				else
				{
					stop(event);
				}
			}, 0L, 1L));
	}
	
	public void stop(PlayerElytraBoostEvent event)
	{
		Bukkit.getServer().getScheduler().cancelTask(taskIDMAP.get(event.getPlayer().getUniqueId()));
		timeLeft.put(event.getPlayer().getUniqueId(), 1000);
	}
	
	public static void initializePlayerTime(Player p, int time)
	{
		timeLeft.put(p.getUniqueId(), time);
	}
}
