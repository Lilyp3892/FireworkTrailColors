package me.spiral_cat.fireworktrailcolors;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static me.spiral_cat.fireworktrailcolors.FileHandler.defaultWrite;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){ //Triggered on Player join
        Player p = event.getPlayer(); //sets reference to the player that triggered the event as p
        try{//try statement because Files.createDirectories demands it, but it shouldn't fail
            Files.createDirectories(Paths.get("plugins/FTC")); //Creates a folder named FTC in the /plugins folder if there isn't already one
        } catch(IOException e) {// could only fail if there wasn't a plugins folder
            e.printStackTrace();//we shouldn't get here
        }
        File tempFile = new File("plugins/FTC/" + p.getUniqueId() + ".json"); //references the joining players file and...
        boolean exists = tempFile.exists(); //this will be true if that file already exists
        if(!exists){ //checks if that file doesn't exist
            defaultWrite(p); //makes a default file for the player
        }
        FireworkTriggerEvent.timeLeft.put((event.getPlayer().getUniqueId()).toString(), 1000); //for FireworkTriggerEvent. 1000 is used if there is no active timer
    }
}
