package org.wahlzeit.services.mailing;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.wahlzeit.services.EmailAddressTest;

@RunWith(Suite.class)
@SuiteClasses({ EmailServiceTest.class, EmailAddressTest.class })
public class EmailServicesTestSuite {

}
