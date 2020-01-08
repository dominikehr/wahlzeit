package org.wahlzeit.model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class FootballManagerTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	FootballManager footballManager;
	FootballType defaultType;
	FootballType footballTypeGoal;
	FootballType footballTypeTackleCL;
	FootballType footballTypeGoalCL;
	FootballType footballTypeGoalCL2;
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
		footballTypeGoal = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.DEFAULT);
		footballTypeGoalCL = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		footballTypeGoalCL2 = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		footballTypeGoalWC = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.WORLD_CUP);
		defaultFootball = footballManager.createFootball(FootballTypeScene.DEFAULT);
		robbenCLTackle = footballManager.createFootball(FootballTypeScene.TACKLE, Tournament.CHAMPIONS_LEAGUE);
		ronaldoCLTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		goetzeWCTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.WORLD_CUP);
	}
	
	// test getFootballType method in terms of map storage
	@Test
	public void testGetFootballType() {
		// two equal types (equal FootballSceneType, equal Tournament) should have the same key
		// thus only stored once in map --> same key/reference
		Set<FootballType> typesSet = footballManager.getAllTypes();
		assertTrue(footballTypeGoalCL == footballTypeGoalCL2);
		assertTrue(typesSet.contains(footballTypeGoalCL));
	}
	
	// another test for getFootballType method in terms of map storage
	@Test
	public void testGetFootballType2() {
		Set<FootballType> typesSet = footballManager.getAllTypes();
		assertFalse(footballTypeGoalCL == footballTypeGoalWC);
		assertTrue(typesSet.contains(footballTypeGoalWC));
	}
}
