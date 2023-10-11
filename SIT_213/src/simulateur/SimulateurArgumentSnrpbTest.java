package simulateur;

import org.junit.Ignore;
import org.junit.Test;

public class SimulateurArgumentSnrpbTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsSnrpb1() throws ArgumentsException {
        String[] args = {"-snrpb"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsSnrpb2() throws ArgumentsException {
        String[] args = {"-snrpb", "a"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsSnrpb3() throws ArgumentsException {
        String[] args = {"-snrpb", "0"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsSnrpb4() throws ArgumentsException {
        String[] args = {"-snrpb", "10"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsSnrpb5() throws ArgumentsException {
        String[] args = {"-snrpb", "-10"};
        new Simulateur(args);
    }
}
