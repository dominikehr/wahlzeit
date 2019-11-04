
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.wahlzeit.handlers.*;
import org.wahlzeit.model.*;
import org.wahlzeit.model.persistence.*;
import org.wahlzeit.services.*;
import org.wahlzeit.services.mailing.*;
import org.wahlzeit.utils.*;

@RunWith(Suite.class)
@SuiteClasses({ handlersSuite.class, modelSuite.class, modelPersistenceSuite.class, servicesSuite.class,
		EmailServicesTestSuite.class, utilsSuite.class })
		
public class AllTests {

}
