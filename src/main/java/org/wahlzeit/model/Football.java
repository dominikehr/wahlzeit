package org.wahlzeit.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.wahlzeit.utils.StringUtil;

/**
 * 
 * Instantiation process of a Football:
 * 
 * The instantiation process entails several consecutive steps that begin inside FootballManager.
 * FootballManager provides two overloaded factory methods for Football instantiation. These take
 * in either just the the type of the football scene depicted or a tournament type as well.
 * Using these two attributes a look-up inside a map is performed which returns either an already
 * existing FootballType or a new one. On this FootballType, the instance method createInstance is
 * invoked which in turn invokes the Football constructor.
 * There, actual instantiation takes place and a unique ID is assigned to the football instance.
 * 
 * This detailed description translates to the following key facts when determining its position as a sixtuple inside 
 * the solution design space:
 * 
 * 1. Delegation of Object Creation: separate-object
 * 2. Selection of Concrete Class: By-mapping 
 * 3. Configuration of Class Mapping: In-code
 * 4. Instantiation of Concrete Class: In-code
 * 5. Initialization of New Object: Default ("In-second-step" option may be applicable as provided setter methods allow for this)
 * 6. Building of Object Structure: Default
 */


/**
 * 
 * Client-Service Collaboration
 * This collaboration binds the role "Service" to this Football class
 * and the role "Client" to the class {@link FootballPhoto #}
 *
 */

/**
 * 
 * Base Object-Type Object Collaboration
 * This collaboration binds the role "Base Object" to this Football class
 * and the role "Type Object" to the class {@link FootballType #}
 *
 */

/**
 * 
 * Manager-Element Collaboration
 * This collaboration binds the role "Element" to this Football class
 * and the role "Manager" to the class {@link FootballManager #}
 *
 */

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
