package org.wahlzeit.model;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.model.FootballType.FootballTypeScene;
import org.wahlzeit.model.FootballType.Tournament;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class FootballPhotoTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	PhotoId id;
	FootballPhoto footballPhoto;
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
		id = new PhotoId(666);
		this.footballPhoto = new FootballPhoto(id);
		footballManager = FootballManager.getInstance();
		defaultFootball = footballManager.createFootball(FootballTypeScene.DEFAULT);
		robbenCLTackle = footballManager.createFootball(FootballTypeScene.TACKLE, Tournament.CHAMPIONS_LEAGUE);
		ronaldoCLTor = footballManager.createFootball(FootballTypeScene.SCOARING_GOAL, Tournament.CHAMPIONS_LEAGUE);
	}
	
	// test whether correct instantiation when working with (no) argument constructors
	@Test
	public void testDefaultPhotoConstruction() {
		Football footballAssociated = footballPhoto.getFootball();
		assertEquals(id, footballPhoto.getId());
		FootballTypeScene expectedTypeScene = FootballTypeScene.DEFAULT;
		Tournament expectedTournament = Tournament.DEFAULT;
		assertEquals(expectedTypeScene, footballAssociated.getFootballType().getFootballTypeInScene());
		assertEquals(expectedTournament, footballAssociated.getFootballType().getTournamentInScene());
	}
	
	@Test
	public void testGettersandSetters() {
		FootballPhoto footballPhoto2 = new FootballPhoto(new PhotoId(1), robbenCLTackle, new Date(5000l));
		assertEquals(robbenCLTackle, footballPhoto2.getFootball());
		footballPhoto2.setFootball(ronaldoCLTor);
		footballPhoto2.setTimeTaken(new Date(6000));
		assertEquals(ronaldoCLTor, footballPhoto2.getFootball());
		assertEquals(new Date(6000), footballPhoto2.getTimeTaken());
	}
	
	
	

}
