package me.spiral_cat.fireworktrailcolors.records;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StateRecord
{
	private Map<UUID, TrailColorRecord> colorMap;
	
	// It's good practice to always initialize collections so that they are never null.
	public StateRecord()
	{
		this.colorMap = new HashMap<>();
	}
	
	public Map<UUID, TrailColorRecord> getColorMap()
	{
		return colorMap;
	}
	
	public void setColorMap(Map<UUID, TrailColorRecord> colorMap)
	{
		this.colorMap = colorMap;
	}
}
