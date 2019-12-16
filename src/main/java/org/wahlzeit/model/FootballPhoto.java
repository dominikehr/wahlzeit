package org.wahlzeit.model;

import java.util.ArrayList;
import java.util.List;


public class FootballPhoto extends Photo {

	//list of player names that may be present in the FootballPhoto
	private List<String> playerNames = new ArrayList<>();
	
	public FootballPhoto() {
		super();
	}
	
	public FootballPhoto(PhotoId id) {
		super(id);
		if(id == null) {
			throw new IllegalArgumentException("PhotoID must not be null");
		}
	}

	/*
	 * @methodtype command
	 * sets a a players name inside list attribute
	 */
	public void addSinglePlayerName(String name) {
		this.playerNames.add(name);
	}
	
	/*
	 * @methodtype command
	 * allows insertion of multiple player names at once
	 */
	public void addMultiplePlayerNames(List<String> nameList) {
		//check for uninitialized list
		if(nameList == null) {
			throw new NullPointerException("Name list must not be null");
		}
		for(String player : nameList) {
			this.playerNames.add(player);
		}
	}
	
	/*
	 * @methodtype get
	 * returns a List representation of all players currently identified in the FootballPhoto
	 */
	public List<String> getPlayerNames(){
		return this.playerNames;
	}

}
