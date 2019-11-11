package org.wahlzeit.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class FootballPhotoTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	private FootballPhoto footballPhoto;
	
	@Before
	public void setUpTestPhoto() {
		this.footballPhoto = new FootballPhoto(new PhotoId(666));
	}
	
	//test whether correct exception thrown
	@Test(expected = NullPointerException.class)
	public void testAddMultiplePlayerNamesException() {
		footballPhoto.addMultiplePlayerNames(null);		
	}
	
	//test whether player names are correctly set and retrieved
	@Test
	public void testGetPlayerNames() {
		//ARRANGE
		String goetze = "Mario Goetze";
		//ACT
		this.footballPhoto.addSinglePlayerName(goetze);
		//ASSERT
		List<String> resultList = footballPhoto.getPlayerNames();
		assertTrue(resultList.size() == 1);
		assertTrue(resultList.contains(goetze));
		
		//ARRANGE
		List<String> playerList = new ArrayList<>();
		String robben = "Arjen Robben";
		String ribery = "Franck Ribery";
		String lewandowski = "Robert Lewandowski";
		playerList.add(robben);
		playerList.add(ribery);
		playerList.add(lewandowski);
		this.footballPhoto.addMultiplePlayerNames(playerList);
		resultList = footballPhoto.getPlayerNames();
		
		//ASSERT
		assertEquals(4, resultList.size());
		assertTrue(resultList.contains(robben));
		String martinez = "Javi Martinez";
		assertFalse(resultList.contains(martinez));
	}
	
	
	

}
