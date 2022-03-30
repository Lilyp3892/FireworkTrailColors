package me.spiral_cat.fireworktrailcolors.records;

import java.util.UUID;

public class PlayerRecord
{
	private UUID playerID;

	public PlayerRecord()
	{
		
	}
	
	public PlayerRecord(UUID playerID)
	{
		this.playerID = playerID;
	}
	
	public UUID getPlayerID()
	{
		return playerID;
	}
	
	public void setPlayerID(UUID playerID)
	{
		this.playerID = playerID;
	}
}
