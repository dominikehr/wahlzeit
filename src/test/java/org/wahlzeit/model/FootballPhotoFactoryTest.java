package org.wahlzeit.model;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.wahlzeit.testEnvironmentProvider.LocalDatastoreServiceTestConfigProvider;
import org.wahlzeit.testEnvironmentProvider.RegisteredOfyEnvironmentProvider;

public class FootballPhotoFactoryTest {

	@ClassRule
    public static RuleChain ruleChain = RuleChain.
            outerRule(new LocalDatastoreServiceTestConfigProvider()).
            around(new RegisteredOfyEnvironmentProvider());
	
	/*
	 * Resetting the private FootballPhotoFactory instance to null after
	 * every test case using reflections. This is needed to avoid side effects
	 */
	@Before
	public void resetFootballPhotoFactory() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field instance = FootballPhotoFactory.class.getDeclaredField("instance");
		instance.setAccessible(true);
		instance.set(instance, null);
	}
	
	// test whether initialize() results in non-null factory instance
	@Test
	public void testInitializeT() {
		FootballPhotoFactory.initialize();
		assertTrue(FootballPhotoFactory.getInstance() != null);
	}
	
	// test whether setInstance correctly throws an exception given double assignment
	@Test(expected = IllegalStateException.class)
	public void testSetInstanceThrowsException() {
		// first assignment since getInstance calls setInstance
		FootballPhotoFactory fpf = FootballPhotoFactory.getInstance();
		// second assignment this throws IllegalStateException
		FootballPhotoFactory.setInstance(fpf);
	}
	
	// test whether no exception is thrown in case of regular unique assignment
	@Test(expected = Test.None.class)
	public void testSetInstance() {
		//ARRANGE
		FootballPhotoFactory fpf = new FootballPhotoFactory();
		//ACT
		FootballPhotoFactory.setInstance(fpf);
	}
	
	// test whether photo is indeed created as instance of FootballPhoto
	@Test
	public void testCreatePhoto() {
		//ARRANGE
		FootballPhotoFactory.initialize();
		//ACT
		Photo foto = FootballPhotoFactory.getInstance().createPhoto();
		//ASSERT
		assertTrue(foto instanceof FootballPhoto);
		assertEquals(FootballPhoto.class, foto.getClass());
	}
	
	// test whether photo is indeed created as instance of FootballPhoto
	// using overloaded method taking id
	@Test
	public void testCreatePhotoId() {
		//ARRANGE
		FootballPhotoFactory.initialize();
		PhotoId id = new PhotoId(666);
		//ACT
		Photo foto = FootballPhotoFactory.getInstance().createPhoto(id);
		//ASSERT
		assertTrue(foto instanceof FootballPhoto);
		assertEquals(FootballPhoto.class, foto.getClass());
		//test whether getter for Id is working correctly
		assertEquals(foto.getId(), id);
	}
}
