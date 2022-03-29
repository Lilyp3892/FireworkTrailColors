package me.spiral_cat.fireworktrailcolors;

import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import java.util.HashMap;

public class FireworkTriggerEvent implements Listener {
    FireworkTrailColors plugin; //specifies plugin
    public FireworkTriggerEvent(FireworkTrailColors plugin) {
        this.plugin = plugin;
    } //specifies plugin
    public static HashMap<String, Integer> timeLeft = new HashMap<>(); //sets var to track time left for particle creation per UUID
    public static HashMap<String, Integer> taskIDMAP = new HashMap<>();  //sets var to track the taskID of the running task per on UUID

    @EventHandler
    public void onPlayerElytraBoost(PlayerElytraBoostEvent event){
        if(timeLeft.get(event.getPlayer().getUniqueId().toString()) == 1000 && FileHandler.read(event.getPlayer()).get(event.getPlayer().getUniqueId()).getOn()) { //1000 is used at if there is no active timer and is immediately changed right....
            timeLeft.put(event.getPlayer().getUniqueId().toString(), (event.getFirework().getFireworkMeta().getPower()*20)); //here. Also, It's impossible to obtain a rocket with a flight duration of 0 in survival
            taskIDMAP.put(event.getPlayer().getUniqueId().toString(), startCountdown(event)); // gets taskID to use for stop() and at the same time runs startCountdown()
        }
    }

    public int startCountdown(PlayerElytraBoostEvent event){ //starts the firework timer and returns the taskID of the runnable
        return(Bukkit.getServer().getScheduler()
                .scheduleSyncRepeatingTask(this.plugin, () -> {
                    if (timeLeft.get(event.getPlayer().getUniqueId().toString()) > 0) { // checks if the timer is not at 0 or below 0 (IDK how it would get below 0, so this is just in case)
                        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(FileHandler.read(event.getPlayer()).get(event.getPlayer().getUniqueId()).getRed(), FileHandler.read(event.getPlayer()).get(event.getPlayer().getUniqueId()).getGreen(), FileHandler.read(event.getPlayer()).get(event.getPlayer().getUniqueId()).getBlue()), 1.0F); //gets the players rgb values from their file
                        event.getPlayer().spawnParticle(Particle.REDSTONE, event.getPlayer().getLocation(), 50, dustOptions); //spawns particles
                        timeLeft.put(event.getPlayer().getUniqueId().toString(), timeLeft.get(event.getPlayer().getUniqueId().toString()) - 1); //time = time - 1; but the var name is not actually a var it's a hashmap
                    } else {
                        stop(event); // sends stop() once the timer hits 0
                    }
                }, 0L, 1L)); //runs every one tic until it is canceled

    }
    public void stop(PlayerElytraBoostEvent event){ //stops and resets the firework timer
        Bukkit.getServer().getScheduler().cancelTask(taskIDMAP.get(event.getPlayer().getUniqueId().toString())); //kills the runnable using the task ID
        timeLeft.put(event.getPlayer().getUniqueId().toString(), 1000); //1000 is used if there is no active timer
    }
}
