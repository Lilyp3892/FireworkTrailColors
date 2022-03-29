package me.spiral_cat.fireworktrailcolors;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.bukkit.entity.Player;

public class FileHandler {

    public static Map<UUID, TrailColor> read(Player p2) //makes Map of UUID and trail color
    {
        try //shouldn't fail because of PlayerJoinListener but just in case
        {
            String fileContent = Files.readString(Paths.get("plugins/FTC/"+p2.getUniqueId()+".json")); //grabs your file based on UUID
            StateRecord stateRecord = new ObjectMapper().readValue(fileContent, StateRecord.class); //Makes file content readable to the program
            return (stateRecord.getColorMap()); //returns readable to use file content
        }
        catch (IOException e)//we shouldn't get here
        {
            e.printStackTrace();//we shouldn't get here
        }
        return null; //only would happen if the try failed which again it shouldn't
    }

    public static void write(Player p, int redd, int greenn, int bluee, boolean on) { //used for write
        if(redd == 256){ //so over in ToggleCommandExecutor If someone does /ftc it toggles weather the var is on or off, but it has to send some sort of int values for RGB, so it instead sends 256 which tells this line of code to grab the old RGB values and replace the new ones that it just got (256, 256, 256) with the ones contained in the file already.
            redd = FileHandler.read(p).get(p.getUniqueId()).getRed(); //changes new value of red to file value of red
            greenn = FileHandler.read(p).get(p.getUniqueId()).getGreen(); //changes new value of green to file value of green
            bluee = FileHandler.read(p).get(p.getUniqueId()).getBlue(); //changes new value of blue to file value of blue
        } else { // if this happens then you are just changing the colors and therefor we can't assume if its on or off, so we want to grab the old on value from the file, so we don't overwrite it
            on = FileHandler.read(p).get(p.getUniqueId()).getOn(); //changed new value of on/off to file value of on/off
        }

        Map<UUID, TrailColor> colorMap = new HashMap<>(); //makes Map of UUID and trail color

        TrailColor tc1 = new TrailColor(redd, greenn, bluee, on); //defines what the trail color (and on) is. Pretty much when I say TrailColor I'm talking about trail color and weather its on or off

        colorMap.put(p.getUniqueId(), tc1); //sets UUID to tc1 or TrailColor

        StateRecord stateRecord = new StateRecord(); //sets up to make file content readable to use
        stateRecord.setColorMap(colorMap); //makes file content readable

        try { //had to for Files.createDirectories
            String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(stateRecord);//makes StateRecord into json format
            Files.createDirectories(Paths.get("plugins/FTC"));//creates a folder called "FTC" in plugins if there is not one there
            Files.writeString(Paths.get("plugins/FTC/" + p.getUniqueId() + ".json"), jsonString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING); //creates/overwrites JSON file made of StateRecord and names it <PlayerUUID>.json
        } catch (IOException e) { //We shouldn't get here
            e.printStackTrace(); //or here
        }
    }
    public static void defaultWrite(Player p) { //all basically same as above. This is called in PlayerJoinListener to make a default json file for a player if no file is present.

        Map<UUID, TrailColor> colorMap = new HashMap<>();

        TrailColor tc1 = new TrailColor(255, 255, 255, false); //uses these default values

        colorMap.put(p.getUniqueId(), tc1);

        StateRecord stateRecord = new StateRecord();
        stateRecord.setColorMap(colorMap);

        try { //shouldn't fail because of PlayerJoinListener making a directory but just in case
            String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(stateRecord);
            Files.createDirectories(Paths.get("plugins/FTC"));
            Files.writeString(Paths.get("plugins/FTC/" + p.getUniqueId() + ".json"), jsonString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
