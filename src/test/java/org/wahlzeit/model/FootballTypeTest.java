package org.wahlzeit.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class FootballTypeTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	FootballManager footballManager;
	FootballType defaultType;
	FootballType footballTypeGoal;
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
		footballTypeGoal = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.DEFAULT);
		footballTypeGoalCL = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		footballTypeGoalWC = footballManager.getFootballType(FootballTypeScene.SCOARING_GOAL, Tournament.WORLD_CUP);
		defaultFootball = footballManager.createFootball(FootballTypeScene.DEFAULT);
		robbenCLTackle = footballManager.createFootball(FootballTypeScene.TACKLE, Tournament.CHAMPIONS_LEAGUE);
		ronaldoCLTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
		goetzeWCTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.WORLD_CUP);
	}
	
	// test super and subtypes declarations
	@Test
	public void testIsSubType() {
		footballTypeGoal.addSubType(footballTypeGoalCL);
		footballTypeGoal.addSubType(footballTypeGoalWC);
		
		assertTrue(footballTypeGoalCL.isSubType());
		assertTrue(footballTypeGoalWC.isSubType());
		assertTrue(footballTypeGoalCL.hasEqualSuperType(footballTypeGoalWC));
		assertEquals(footballTypeGoal,footballTypeGoalCL.getSuperType());
	}
	
	// test has Instance method
	@Test
	public void testHasInstance() {
		assertTrue(footballTypeGoalCL.hasInstance(ronaldoCLTor));
		assertFalse(footballTypeGoalCL.hasInstance(goetzeWCTor));
	}
	
	

}
