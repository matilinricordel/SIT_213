package simulateur;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        SimulateurArgumentDefaultTest.class,
        SimulateurArgumentMessTest.class,
        SimulateurArgumentSeedTest.class,
        SimulateurArgumentFormTest.class,
        SimulateurArgumentNbEchTest.class,
        SimulateurArgumentAmplTest.class,
        SimulateurArgumentSnrpbTest.class,
})
public class SimulateurArgumentsTest {}