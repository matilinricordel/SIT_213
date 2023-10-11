package simulateur;

import org.junit.Test;

public class SimulateurArgumentFormTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsForm1() throws ArgumentsException {
        String[] args = {"-form"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsForm2() throws ArgumentsException {
        String[] args = {"-form", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsForm3() throws ArgumentsException {
        String[] args = {"-form", "NRZX"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsForm4() throws ArgumentsException {
        String[] args = {"-form", "RZ"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsForm5() throws ArgumentsException {
        String[] args = {"-form", "NRZ"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsForm6() throws ArgumentsException {
        String[] args = {"-form", "NRZT"};
        new Simulateur(args);
    }
}
