package simulateur;

import org.junit.Test;

public class SimulateurArgumentDefaultTest {
    @Test
    public void analyseArgumentsDefault() throws ArgumentsException {
        String[] args = {};
        new Simulateur(args);
    }
}
