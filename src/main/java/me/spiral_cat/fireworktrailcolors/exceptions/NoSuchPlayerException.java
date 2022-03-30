package me.spiral_cat.fireworktrailcolors.exceptions;

import java.util.UUID;

public class NoSuchPlayerException extends Exception
{
	private static final long serialVersionUID = -3197665386682193012L;
	
	private final UUID playerID;
	
	public NoSuchPlayerException(UUID playerID)
	{
		super(playerID.toString());
		this.playerID = playerID;
	}
	
	public UUID getPlayerID()
	{
		return playerID;
	}
}
