package simulateur;

import org.junit.Test;

public class SimulateurArgumentSeedTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsSeed1() throws ArgumentsException {
        String[] args = {"-seed"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsSeed2() throws ArgumentsException {
        String[] args = {"-seed", "a"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsSeed3() throws ArgumentsException {
        String[] args = {"-seed", "0"};
        new Simulateur(args);
    }
}
