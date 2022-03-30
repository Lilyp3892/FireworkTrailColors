package me.spiral_cat.fireworktrailcolors.executors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import me.spiral_cat.fireworktrailcolors.io.FileHandler;

public class ToggleCommandExecutor implements CommandExecutor
{
	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args)
	{
		// This method was way too complex. Keep your methods small and, specifically whenever you have a condition chain like this, try extracting your
		// execution paths into additional methods.
		if (sender instanceof Player)
		{
			Player p = (Player) sender;
			
			if (args.length == 0)
			{
				return this.toggleParticles(p);
			}
			else if (args.length == 3)
			{
				return this.changeColor(p, args);
			}
			else if (args.length == 1 && args[0].equalsIgnoreCase("help"))
			{
				p.sendMessage("§6FTC help:");
				p.sendMessage("§6Enabling/Disabling particles: /ftc");
				p.sendMessage("§6Changing particle color: /ftc <red> <green> <blue>");
				p.sendMessage("§6Uses RGB Values. For more information or to report a bug:");
				p.sendMessage("§6https://github.com/Lilyp3892/FireworkTrailColors");
				return false;
			}
			else
			{
				p.sendMessage("§4FTC: Error! Incorrect number of arguments! Use \"/ftc help\" for more information");
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	private boolean toggleParticles(Player p)
	{
		if (FileHandler.getPlayerTrailColor(p.getUniqueId()).getOn())
		{
			FileHandler.write(p.getUniqueId(), -1, -1, -1, false);
			p.sendMessage("§a§lFTC: Off");
		}
		else
		{
			FileHandler.write(p.getUniqueId(), -1, -1, -1, true);
			p.sendMessage("§a§lFTC: On");
		}
		return true;
	}
	
	private boolean changeColor(Player p, String[] args)
	{
		try
		{
			int red   = Integer.parseInt(args[0]);
			int green = Integer.parseInt(args[1]);
			int blue  = Integer.parseInt(args[2]);
			if ((red >= 0 && red <= 255) && (green >= 0 && green <= 255) && (blue >= 0 && blue <= 255))
			{
				FileHandler.write(p.getUniqueId(), red, green, blue, true);
				p.sendMessage("§aFTC: Success! Color changed to: " + red + ", " + green + ", " + blue);
			}
			else
			{
				p.sendMessage("§4FTC: Error! All 3 arguments must be a valid numbers between 0 and 255! Use \"/ftc help\" for more information");
			}
			return true;
		}
		// Whenever you expect something to fail, be as specific with the resulting exception as possible. A general "Exception" should only be caught as a
		// last fail-safe.
		catch (NumberFormatException e)
		{
			p.sendMessage("§4FTC: Error! All 3 arguments must be a valid numbers between 0 and 255! Use \"/ftc help\" for more information");
			return false;
		}
	}
}
