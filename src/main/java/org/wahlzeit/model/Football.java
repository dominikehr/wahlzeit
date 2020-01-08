package org.wahlzeit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.wahlzeit.utils.StringUtil;

public class Football {
	protected FootballType footballType = null;
	
	// List of player names that may be present in the football scene
	private List<String> playerNames = new ArrayList<>();

	/* 
	 * ticker variable that keeps track of instantiated football objects for unique ids
	 * Made atomic for thread-safety
	 */
	private AtomicInteger ticker = new AtomicInteger(0);
	// Key of football scene, based on ticker variable
	private int footballId;
	
	/*
	 * @methodtype: constructor
	 */
	public Football(FootballType footballType){
		if(footballType == null) throw new IllegalArgumentException("FootballType must not be null!");
		this.footballType = footballType;
		//assign unique id and increment ticker atomically for next one
		this.footballId = ticker.getAndIncrement();
	}
	
	/*
	 * @methodtype: get
	 */
	public int getFootballId() {
		return footballId;
	}
	
	/*
	 * @methodtype: get
	 */
	public FootballType getFootballType() {
		return footballType;
	}

	/*
	 * @methodtype command
	 * sets a a players name inside list attribute
	 */
	public void addSinglePlayerName(String name) {
		StringUtil.assertIsValidString(name);
		this.playerNames.add(name);
	}
	
	/*
	 * @methodtype command
	 * allows insertion of multiple player names at once
	 */
	public void addMultiplePlayerNames(List<String> nameList) {
		//check for uninitialized list
		if(nameList == null) {
			throw new IllegalArgumentException("Name list must not be null");
		}
		for(String player : nameList) {
			this.playerNames.add(player);
		}
	}
	
	/*
	 * @methodtype command
	 * remove individualplayer from player list if present
	 */
	public boolean removeSinglePlayer(String name) {
		if(name != null && name != "") {
			if(playerNames.contains(name)) {
				playerNames.remove(name);
				return true;
			}
		}
		// player not found
		return false;
	}
	
	/*
	 * @methodtype command
	 * batch remove multiple players from player list if present
	 */
	public boolean removeMultiplePlayers(List<String> nameList) {
		if(nameList == null) {
			throw new IllegalArgumentException("Name list must not be null");
		}
		
		boolean changed = false;
		for(String player : nameList) {
			if(playerNames.contains(player)) {
				changed = true;
				playerNames.remove(player);
			}
		}
		//return whether we have successfully removed elements, thus changed the list structure
		return changed;
	}
	
	/*
	 * @methodtype get
	 * returns a List representation of all players currently identified in the FootballPhoto
	 */
	public List<String> getPlayerNames(){
		return this.playerNames;
	}

}
