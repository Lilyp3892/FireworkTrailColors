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
import me.spiral_cat.fireworktrailcolors.rainbow.RainbowMaker;

public class FireworkTriggerListener implements Listener
{
	private static Map<UUID, Integer> timeLeft  = new HashMap<>();
	private static Map<UUID, Integer> taskIDMAP = new HashMap<>();
	
	@EventHandler
	public void onPlayerElytraBoost(PlayerElytraBoostEvent event)
	{
		if (timeLeft.get(event.getPlayer().getUniqueId()) == 1000
			&& FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getOn())
		{
			timeLeft.put(event.getPlayer().getUniqueId(), (event.getFirework().getFireworkMeta().getPower() * 20));
			taskIDMAP.put(event.getPlayer().getUniqueId(), startCountdown(event));
		}
	}
	
	public int startCountdown(PlayerElytraBoostEvent event)
	{
		return (Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(FireworkTrailColors.getInstance(), () ->
		{
			Player p   = event.getPlayer();
			UUID   pid = p.getUniqueId();
			if (timeLeft.get(pid) > 0)
			{
				// NEVER use static imports for methods, that is very hard to read.
				// Instead, fully qualify them like FileHandler.read().
				if (FileHandler.getPlayerTrailColor(pid).getRed() == -2)
				{
					int    time              = timeLeft.get(pid);
					double powerFactor       = event.getFirework().getFireworkMeta().getPower() * 20.0;
					double colorValue        = time / powerFactor;
					int    adaptedColorValue = (int) (colorValue * 600);
					
					// Try to not repeat sections of code like "(int) (colorValue * 600)".
					// Instead, use a variable to store it once and then reuse the variable.
					int rainbowRed   = RainbowMaker.getRainbowColors().get(adaptedColorValue).getRed();
					int rainbowGreen = RainbowMaker.getRainbowColors().get(adaptedColorValue).getGreen();
					int rainbowBlue  = RainbowMaker.getRainbowColors().get(adaptedColorValue).getBlue();
					
					Color targetColor = Color.fromRGB(rainbowRed, rainbowGreen, rainbowBlue);
					float targetSize  = 1.0F;
					
					// When dealing with long statements like this, try to extract the arguments and just pass in single variables
					// like targetColor and targetSize.
					Particle.DustOptions dustOptions = new Particle.DustOptions(targetColor, targetSize);
					// Always reuse variables like p instead of getting them from a super structure again (p > event.getPlayer()).
					p.getWorld().spawnParticle(Particle.REDSTONE, p.getLocation(), 50, dustOptions);
				}
				else
				{
					int                  red         = FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getRed();
					int                  green       = FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getGreen();
					int                  blue        = FileHandler.getPlayerTrailColor(event.getPlayer().getUniqueId()).getBlue();
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
