package simulateur;

import org.junit.Ignore;
import org.junit.Test;

public class SimulateurArgumentNbEchTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsNbEch1() throws ArgumentsException {
        String[] args = {"-nbEch"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsNbEch2() throws ArgumentsException {
        String[] args = {"-nbEch", "a"};
        new Simulateur(args);
    }

    // Values equals to zero should be refused
    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsNbEch3() throws ArgumentsException {
        String[] args = {"-nbEch", "0"};
        new Simulateur(args);
    }

    // Values below to zero should be refused
    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsNbEch4() throws ArgumentsException {
        String[] args = {"-nbEch", "-1"};
        new Simulateur(args);
    }

    // Floats should be refused
    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsNbEch5() throws ArgumentsException {
        String[] args = {"-nbEch", "0.1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsNbEch6() throws ArgumentsException {
        String[] args = {"-nbEch", "1"};
        new Simulateur(args);
    }
}
