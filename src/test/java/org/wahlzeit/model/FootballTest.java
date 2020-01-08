package org.wahlzeit.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class FootballTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	FootballManager footballManager;
	FootballType defaultType;
	FootballType footballTypeTackleCL;
	FootballType footballTypeGoalCL;
	FootballType footballTypeGoalWC;
	Football defaultFootball;
	Football robbenCLTackle;
	Football ronaldoCLTor;
	Football goetzeWCTor;
	
	@Before
	public void setUp() {
		footballManager = FootballManager.getInstance();
		defaultType = footballManager.getFootballType(FootballTypeScene.DEFAULT, Tournament.DEFAULT);
		footballTypeTackleCL = footballManager.getFootballType(FootballTypeScene.TACKLE, Tournament.CHAMPIONS_LEAGUE);
		footballTypeGoalCL = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		footballTypeGoalWC = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.WORLD_CUP);
		defaultFootball = footballManager.createFootball(FootballTypeScene.DEFAULT);
		robbenCLTackle = footballManager.createFootball(FootballTypeScene.TACKLE, Tournament.CHAMPIONS_LEAGUE);
		ronaldoCLTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		goetzeWCTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.WORLD_CUP);
	}
	
	//test whether correct exception thrown
	@Test(expected = IllegalArgumentException.class)
	public void testAddMultiplePlayerNamesException() {
		goetzeWCTor.addMultiplePlayerNames(null);		
	}
	
	//test whether player names are correctly added and retrieved
	@Test
	public void testGetAddPlayerNames() {
		//ARRANGE
		String goetze = "Mario Goetze";
		//ACT
		goetzeWCTor.addSinglePlayerName(goetze);
		//ASSERT
		List<String> resultList = goetzeWCTor.getPlayerNames();
		assertTrue(resultList.size() == 1);
		assertTrue(resultList.contains(goetze));
		
		//ARRANGE
		List<String> playerList = new ArrayList<>();
		String robben = "Arjen Robben";
		String ribery = "Franck Ribery";
		String hummels = "Mats Hummels";
		String subotic = "Neven Subotic";
		playerList.add(robben);
		playerList.add(ribery);
		playerList.add(hummels);
		playerList.add(subotic);
		robbenCLTackle.addMultiplePlayerNames(playerList);
		resultList = robbenCLTackle.getPlayerNames();
		
		//ASSERT
		assertEquals(4, resultList.size());
		assertTrue(resultList.contains(robben));
		String martinez = "Javi Martinez";
		assertFalse(resultList.contains(martinez));
	}
	
	@Test
	public void testRemovePlayers() {
		// remove single player
		goetzeWCTor.removeSinglePlayer("Mario Goetze");
		assertTrue(goetzeWCTor.getPlayerNames().isEmpty());
		
		// arrange
		List<String> playerList = new ArrayList<>();
		String robben = "Arjen Robben";
		String ribery = "Franck Ribery";
		String hummels = "Mats Hummels";
		String subotic = "Neven Subotic";
		playerList.add(robben);
		playerList.add(ribery);
		playerList.add(hummels);
		playerList.add(subotic);
		robbenCLTackle.addMultiplePlayerNames(playerList);
		
		//remove multiple players at once, test true return value
		List<String> playerList2 = new ArrayList<>();
		playerList2.add(robben);
		playerList2.add(ribery);
		boolean removedTrue = robbenCLTackle.removeMultiplePlayers(playerList2);
		// assert:
		assertTrue(removedTrue);
		assertTrue(robbenCLTackle.getPlayerNames().size() == 2);
		
		//remove multiple players at once, test false return value
		List<String> playerList3 = new ArrayList<>();
		playerList2.add(new String ("Spieler XYZ"));
		boolean removedFalse = robbenCLTackle.removeMultiplePlayers(playerList3);
		assertFalse(removedFalse);
		assertTrue(robbenCLTackle.getPlayerNames().size() == 2);
	}
	

}
