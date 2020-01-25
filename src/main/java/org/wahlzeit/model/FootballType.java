package org.wahlzeit.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.wahlzeit.services.DataObject;

/**
 * 
 * Base Object-Type Object Collaboration
 * This collaboration binds the role "Type Object" to this FootballType class
 * and the role "Base Object" to the class {@link Football #}
 *
 */

public class FootballType extends DataObject {
	
	/*
	 * Enumeration that contains all possible types of football scenes
	 * It is unlikely that all too many pictures could fall outside these categories
	 * However, default value would accomodate such cases
	 */
	FootballTypeScene footballTypeInScene;
	public enum FootballTypeScene {
		SCOARING_GOAL,
		TACKLE,
		SET_PIECE,
		REFEREE_DECISION,
		CHEERING,
		TEAM_PICTURE,
		DEFAULT
	}
	
	Tournament tournamentInScene;
	// Enum of possible tournaments the football scence could originate from
	public enum Tournament {
		CHAMPIONS_LEAGUE,
		EUROPA_LEAUGE,
		NATIONAL_LEAGUE,
		NATIONAL_CUP,
		EUROPEAN_CHAMPIONSHIP,
		WORLD_CUP,
		CLUB_WORLD_CUP,
		DEFAULT
	}
		
	protected FootballType superType = null;
	protected Set<FootballType> subTypes = new HashSet<>();
	
	/*
	 * @methodtype: constructor
	 * various constructor methods taking in (no) arguments
	 */
	public FootballType() {
		this(FootballTypeScene.DEFAULT, Tournament.DEFAULT);
	}
	
	public FootballType(FootballTypeScene footballTypeScene) {
		this(footballTypeScene, Tournament.DEFAULT);
	}
	
	public FootballType(FootballTypeScene footballTypeScene, Tournament tournament) {
		assertValidFootballTypeParameters(footballTypeScene, tournament);
		this.footballTypeInScene = footballTypeScene;
		this.tournamentInScene = tournament;
	}
	/*
	 * @methodtype: get method
	 */
	public FootballTypeScene getFootballTypeInScene() {
		return footballTypeInScene;
	}

	/*
	 * @methodtype: set
	 */
	public void setFootballTypeInScene(FootballTypeScene footballTypeInScene) {
		this.footballTypeInScene = footballTypeInScene;
	}

	/*
	 * @methodtype: get method
	 */
	public Tournament getTournamentInScene() {
		return tournamentInScene;
	}

	/*
	 * @methodtype: set
	 */
	public void setTournamentInScene(Tournament tournamentInScene) {
		this.tournamentInScene = tournamentInScene;
	}
	
	/*
	 * @methodtype: factory
	 * creates an instance of Football using this-reference FootballType
	 */
	public Football createInstance() {
		return new Football(this);
	}
	
	/*
	 * @methodtype: get method
	 * Returns the supertype of this FootballType.
	 */
	public FootballType getSuperType() {
		return superType;
	}
		
	/*
	 * @methodtype: set
	 * sets superType of a given FootballType
	 */
	public void setSuperType(FootballType superType) {
		this.superType = superType;
	}
	
	/*
	 * @methodtype: get method
	 * returns iterator in order to go through all subtypes of a given FootballType
	 */
	public Iterator<FootballType> getSubTypesIterator(){
		return subTypes.iterator();
	}
		
	/*
	 * @methodtype: command method
	 * adds given footballType as a subType. This method creates a relation between
	 * a given FootballType (henceforth a subType) and another FootballType (henceforth a superType),
	 * which is the FootballType we invoke this method on
	 */
	public void addSubType(FootballType footballType) {
		if(footballType == null) throw new IllegalArgumentException("FootballType subtype passed must not be null!");
		footballType.setSuperType(this);
		subTypes.add(footballType);
	}

	/*
	 * @methodtype: query
	 * Determines whether the FootballType we invoke this on is the subType
	 * of another FootballType, in other words, has a non-null superType
	 */
	public boolean isSubType() {
		return superType != null;
	}
	
	/*
	 * @methodtype: query
	 * Determines whether the FootballType "this" is invoked on shares the same
	 * superType with the footballType passed as an argument.
	 * In my domain, I determine this to be the case when both FootballTypes
	 * share the same FootballTypeScene argument. Therefore, a FootballType
	 * of FootballTypeScene "TACKLE" and Tournament "WORLD_CUP", has the same 
	 * superType "TACKLE" as another FootballType of FootballTypeScene "TACKLE"
	 * and Tournament "CHAMPIONS_LEAGUE"
	 */
	public boolean hasEqualSuperType(FootballType footballType) {
		return this.footballTypeInScene.equals(footballType.footballTypeInScene);
	}
	
	/*
	 * @methodtype: query
	 */
	public boolean hasInstance(Football football) {
		if(football == null) throw new IllegalArgumentException("Asked about null object!");
		// first compare to this reference type
		if(football.getFootballType() == this) {
			return true;
		}
		// if not found then recursively query all subTypes for potential result
		for(FootballType subType : this.subTypes) {
			// recursively invoke method on subType
			if(subType.hasInstance(football)) return true;
		}
		// Neither this type nor its subTypes hold the given Football object
		return false;
	}
	
	/*
	 * @methodtype: assertion
	 * asserts that no invalid (= null) type parameters are passed
	 */
	public static void assertValidFootballTypeParameters(FootballTypeScene footballTypeScene, Tournament tournament) {
		if(footballTypeScene == null) throw new IllegalArgumentException("FootballTypeScene passed must not be null");
		if(tournament == null) throw new IllegalArgumentException("Tournament passed must not be null");
	}
	
}
