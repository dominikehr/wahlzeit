package org.wahlzeit.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccessRightsTest.class, CartesianCoordinateTest.class, FlagReasonTest.class, FootballPhotoFactoryTest.class,
				FootballPhotoManagerTest.class, FootballPhotoTest.class, GenderTest.class, GuestTest.class,
				PhotoFilterTest.class, SphericCoordinateTest.class, TagsTest.class, UserStatusTest.class, ValueTest.class })
public class modelSuite {

}
