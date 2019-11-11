package org.wahlzeit.model;

import static org.junit.Assert.*;


import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.services.OfyService;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;
public class FootballPhotoManagerTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	// test whether FootballPhotoManager does in fact return instance of right type
	@Test
	public void testGetInstance() {
		PhotoManager manager = FootballPhotoManager.getInstance();
		assertEquals(manager.getClass(), FootballPhotoManager.class);
		assertTrue(manager instanceof FootballPhotoManager);
	}
}
