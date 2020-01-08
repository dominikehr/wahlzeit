package org.wahlzeit.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;
import org.wahlzeit.services.ObjectManager;

/*
 * Manager singleton charged with instantiating FootballType and Football instances 
 */
public class FootballManager extends ObjectManager {
	// Singleton instance of this football manager
	private static FootballManager instance = new FootballManager();
	// Map that stores all existing Football objects, meaning instances of football scenes
	private static final Map<Integer, Football> footballScenes = new HashMap<>();
	/*
	 *  Map that stores all existing Football Type objects. In this case:
	 *  FootballTypeScene "TACKLE" , Tournament "WORLD_CUP" is one type
	 *  FootballTypeScene "TACKLE" and Tournament "CHAMPIONS_LEAGUE" is another type
	 */
	private static final Map<Integer, FootballType> footballTypes = new HashMap<>();
	// private constructor in order to avoid instantiation
	private FootballManager() {
		
	}
	/*
	 * @methodtype: get
	 */
	public static final FootballManager getInstance() {
		return instance;
	}
	
	/*
	 * @methodtype: factory
	 * creates a Football object 
	 */
	public Football createFootball(FootballTypeScene footballTypeScene ) {
		FootballType.assertValidFootballTypeParameters(footballTypeScene, Tournament.DEFAULT);
		FootballType footballType = getFootballType(footballTypeScene, Tournament.DEFAULT);
		Football football = footballType.createInstance();
		footballScenes.put(football.getFootballId(), football);
		return football;
	}
	
	/*
	 * @methodtype: factory
	 * overloaded factory method that also takes into account Tournaments passed
	 */
	public Football createFootball(FootballTypeScene footballTypeScene, Tournament tournament ) {
		FootballType.assertValidFootballTypeParameters(footballTypeScene, tournament);
		FootballType footballType = getFootballType(footballTypeScene, tournament);
		Football football = footballType.createInstance();
		footballScenes.put(football.getFootballId(), football);
		return football;
	}
	
	/*
	 * @methodtype get
	 * getter method that either returns existing footballType stored in footballTypes map
	 * or instantiates a new one
	 */
	public synchronized FootballType getFootballType(FootballTypeScene footballTypeScene, Tournament tournament) {
		// compute key based on the two attributes passed
		int key = Objects.hash(footballTypeScene, tournament);
		//look up:
		if(footballTypes.containsKey(key)) {
			return footballTypes.get(key);
		} else {
			//no type stored in map yet, instantiate new one
			FootballType footballType = new FootballType(footballTypeScene, tournament);
			// and put into map
			footballTypes.put(key, footballType);
			return footballType;
		}
	}
	
	
	/*
	 * @methodtype: get method
	 * returns a Collection view of all types stored
	 */
	public Set<FootballType> getAllTypes(){
		Collection<FootballType> map = footballTypes.values();
		return new HashSet<>(map);
	}

		

}
