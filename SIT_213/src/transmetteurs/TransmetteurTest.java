package transmetteurs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import transmetteurs.EmetteurTest;
import transmetteurs.RecepteurTest;

@RunWith(Suite.class)
@SuiteClasses({
        CodeurTest.class,
        DecodeurTest.class,
        EmetteurTest.class,
        RecepteurTest.class,
        TransmetteurBruiteTest.class,
        TransmetteurBruiteTest.class,
        TransmetteurScenariosTest.class,
})
public class TransmetteurTest {}