package simulateur;

import org.junit.Ignore;
import org.junit.Test;

public class SimulateurArgumentAmplTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl1() throws ArgumentsException {
        String[] args = {"-ampl"};
        new Simulateur(args);
    }

    @Test(expected = Exception.class)
    public void analyseArgumentsAmpl2() throws ArgumentsException {
        String[] args = {"-ampl", "a"};
        new Simulateur(args);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void analyseArgumentsAmpl3() throws ArgumentsException {
        String[] args = {"-ampl", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl4() throws ArgumentsException {
        String[] args = {"-ampl", "a", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl5() throws ArgumentsException {
        String[] args = {"-ampl", "0", "a"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl6() throws ArgumentsException {
        String[] args = {"-ampl", "a", "a"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl7() throws ArgumentsException {
        String[] args = {"-ampl", "0", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl8() throws ArgumentsException {
        String[] args = {"-ampl", "42", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsAmpl9() throws ArgumentsException {
        String[] args = {"-ampl", "0", "-42"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsAmpl10() throws ArgumentsException {
        String[] args = {"-ampl", "0", "1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsAmpl11() throws ArgumentsException {
        String[] args = {"-ampl", "-1", "0"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsAmpl12() throws ArgumentsException {
        String[] args = {"-ampl", "-2", "-1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsAmpl13() throws ArgumentsException {
        String[] args = {"-ampl", "1", "2"};
        new Simulateur(args);
    }
}
