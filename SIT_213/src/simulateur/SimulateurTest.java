package simulateur;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        SimulateurArgumentsTest.class,
        SimulateurScenariosTest.class,
})
public class SimulateurTest {}