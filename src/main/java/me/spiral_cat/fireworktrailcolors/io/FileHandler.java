package me.spiral_cat.fireworktrailcolors.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;

import me.spiral_cat.fireworktrailcolors.records.StateRecord;
import me.spiral_cat.fireworktrailcolors.records.TrailColorRecord;

public class FileHandler
{
	private static final String BASE_DIRECTORY_PATH = "plugins/FTC/";
	
	// You should create a private constructor, since this class is not supposed to be instanced.
	private FileHandler()
	{
		
	}
	
	public static StateRecord read()
	{
		// There should only ever be one state file. If you want one file per player, you must change the structure of the file to not contain an entire
		// Map<UUID, TrailColor> but just one of each.
		try
		{
			// This is a bit more readable than putting everything in one line.
			String filePathString = String.format("%s.json", FileHandler.BASE_DIRECTORY_PATH);
			Path   filePath       = Paths.get(filePathString);
			String fileContent    = Files.readString(filePath);
			return new ObjectMapper().readValue(fileContent, StateRecord.class);
		}
		catch (IOException e)
		{
			// This will occur when there is no state file yet.
			return new StateRecord();
		}
	}
	
	// Helper method to deal with players that don't exist in the colorMap yet.
	public static TrailColorRecord getPlayerTrailColor(UUID playerID)
	{
		StateRecord                 stateRecord = read();
		Map<UUID, TrailColorRecord> colorMap    = stateRecord.getColorMap();
		if (colorMap.containsKey(playerID))
		{
			return colorMap.get(playerID);
		}
		else
		{
			return TrailColorRecord.getDefault();
		}
	}
	
	public static void write(UUID playerID, int red, int green, int blue, boolean on)
	{
		// You have to read all existing values from the file first and then work on the resulting data set, otherwise you'd just overwrite them with every
		// save operation.
		StateRecord                 state    = FileHandler.read();
		Map<UUID, TrailColorRecord> colorMap = state.getColorMap();
		
		// Dummy values like are usually negative as to not confuse anyone.
		if (red == -1 || green == -1 || blue == -1)
		{
			red   = getPlayerTrailColor(playerID).getRed();
			green = getPlayerTrailColor(playerID).getGreen();
			blue  = getPlayerTrailColor(playerID).getBlue();
		}
		else
		{
			on = getPlayerTrailColor(playerID).getOn();
		}
		
		TrailColorRecord tc = new TrailColorRecord(red, green, blue, on);
		colorMap.put(playerID, tc);
		state.setColorMap(colorMap);
		
		// IOExceptions can always happen. A locked file system, a missing file, etc. That's why it's mandatory to catch them.
		try
		{
			Files.createDirectories(Paths.get(FileHandler.BASE_DIRECTORY_PATH));
			
			String filePathString = String.format("%s.json", FileHandler.BASE_DIRECTORY_PATH);
			Path   filePath       = Paths.get(filePathString);
			
			String jsonString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(state);
			
			Files.writeString(filePath, jsonString, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}
		catch (IOException e)
		{
			// Usually, exceptions have to be handled graciously. Here, however, it's okay to just print the stack trace since it's an unrecoverable state.
			e.printStackTrace();
		}
	}
}
