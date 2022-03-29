package me.spiral_cat.fireworktrailcolors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){ //only runs if player sends it, so it doesn't break if it's executed from something like a console
            Player p = (Player) sender; //grabs the sender and stores it in p

            if(args.length == 0) { //if you send the command with no arguments you are toggling the particles on/off
                if (FileHandler.read(p).get(p.getUniqueId()).getOn()) { //checks if the particles are on and....
                    FileHandler.write(p.getPlayer(), 256, 256, 256, false);//turns them off
                    p.sendMessage("§a§lFTC: Off");//sends a chat message to alert player that they have turned off the particles
                } else if (!FileHandler.read(p).get(p.getUniqueId()).getOn()) { //checks if the particles are off and....
                    FileHandler.write(p.getPlayer(), 256, 256, 256, true);//turns them on
                    p.sendMessage("§a§lFTC: On");//sends a chat message to alert player that they have turned on the particles
                } else { //if the players particles are somehow not on or off it...
                    FileHandler.write(p.getPlayer(), 256, 256, 256, true); //tries to fix it
                    p.sendMessage("§4FTC: Error! Attempted fix. Please try again!");//sends a chat message to alert player that there was an error, and it should be fixed
                }
            } else if(args.length == 3){ //checks if there are 3 arguments
                try{
                    int dured = Integer.parseInt(args[0]); //converts the first argument into variable dured
                    int dugreen = Integer.parseInt(args[1]); //converts the second argument into variable dugreen
                    int dublue = Integer.parseInt(args[2]); //converts the third argument into variable dublue
                    if((dured >= 0 && dured <= 255)&&(dugreen >= 0 && dugreen <= 255)&&(dublue >= 0 && dublue <= 255)){ //checks if RGB arguments given are valid (between 0 and 255)
                        FileHandler.write(p.getPlayer(), dured, dugreen, dublue, true); //changes the rgb values of the particles in the files
                        p.sendMessage("§aFTC: Success! Color changed to: " + dured + ", " + dugreen + ", " + dublue); //sends a chat message to the player to alert the player that their particle rgb values have been changed
                    }else { //if players 3 args are not numbers between 0 and 255
                        p.sendMessage("§4FTC: Error! All 3 arguments must be a valid numbers between 0 and 255! Use \"/ftc help\" for more information");//sends a chat message to the player to alert the player that there was an error because they didn't use values between 0 and 255
                    }
                }catch(Exception e){ //if the player doesn't use whole numbers (uses decimals or just anything that's not 0-9)
                    p.sendMessage("§4FTC: Error! All 3 arguments must be a valid numbers between 0 and 255! Use \"/ftc help\" for more information");//sends a chat message to the player to alert the player that there was an error because they didn't use real numbers
                }
            } else if(args.length == 2) { //checks if there are 2 arguments
                p.sendMessage("§4FTC: Error! not enough arguments! Use \"/ftc help\" for more information"); //sends a chat message to the player to alert the player that they did not use enough arguments
            } else if(args.length == 1) { //checks if there is 1 arguments
                if(args[0].equalsIgnoreCase("help")) { // if the one argument says help in any caps value
                    p.sendMessage("§6FTC help:"); //sends help dialog
                    p.sendMessage("§6Enabling/Disabling particles: /ftc"); //sends help dialog
                    p.sendMessage("§6Changing particle color: /ftc <red> <green> <blue>"); //sends help dialog
                    p.sendMessage("§6Uses RGB Values. For more information or to report a bug:");
                    p.sendMessage("§6https://github.com/Lilyp3892/FireworkTrailColors");
                } else { //if command had one argument, but it wasn't help in any caps value
                    p.sendMessage("§4FTC: Error! not enough arguments! Use \"/ftc help\" for more information"); //sends a chat message to the player to alert the player that they did not use enough arguments
                }
            } else { // if you use 4 or more arguments (or less than 0 not that that's possible)
                p.sendMessage("§4FTC: Error! Too many arguments! Use \"/ftc help\" for more information"); //sends a chat message to the player to alert the player that they used too many arguments
            }

            return true; //returns true if it's a player sending the command

        }else {

            return false; //returns false if it's not a player sending the command
        }
    }
}
