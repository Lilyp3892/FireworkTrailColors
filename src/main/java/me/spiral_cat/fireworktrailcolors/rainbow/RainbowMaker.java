package me.spiral_cat.fireworktrailcolors.rainbow;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// Packages should be lower case only.
public class RainbowMaker
{
	private static List<Color> rainbowColors;
	
	// Again, static classes should be protected by a private constructor to prevent the creation of instances
	private RainbowMaker()
	{
		
	}
	
	// Methods should be camelCase only.
	// Import classes rather than fully qualifying them (java.util.List -> List)
	// I don't know where you found this code but this is... not particularly efficient. Anyway, if it works, it's fine :D
	public static void initRainbow()
	{
		List<Color> colors = new ArrayList<>();
		
		for (int b = 0; b < 100; b++)
			colors.add(new Color(255, 0, b * 255 / 100)); // starts red goes to red-blue [1]
		for (int r = 100; r > 0; r--)
			colors.add(new Color(r * 255 / 100, 0, 255)); // starts red-blue goes to blue [2]
		for (int g = 0; g < 100; g++)
			colors.add(new Color(0, g * 255 / 100, 255)); // starts blue goes to green-blue [3]
		for (int b = 100; b > 0; b--)
			colors.add(new Color(0, 255, b * 255 / 100)); // starts green-blue goes to green [4]
		for (int r = 0; r < 100; r++)
			colors.add(new Color(r * 255 / 100, 255, 0)); // starts green goes to red-green [5]
		for (int g = 100; g > 0; g--)
			colors.add(new Color(255, g * 255 / 100, 0)); // starts red-green goes to red [6]
		colors.add(new Color(255, 0, 0));
		
		// List.copyOf creates an unmodifiable list with the same content as the source.
		// This way, you can statically access the list later on without having to worry about modifications.
		RainbowMaker.rainbowColors = List.copyOf(colors);
	}
	
	// No static access to attributes directly. Always make them private and give access via methods.
	public static List<Color> getRainbowColors()
	{
		return RainbowMaker.rainbowColors;
	}
}
