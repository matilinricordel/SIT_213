package simulateur;

import org.junit.Test;

public class SimulateurArgumentTiTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI1() throws ArgumentsException {
        String[] args = {"-ti"};
        new Simulateur(args);
    }

    @Test(expected = Exception.class)
    public void analyseArgumentsTI2() throws ArgumentsException {
        String[] args = {"-ti", "a"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI3() throws ArgumentsException {
        String[] args = {"-ti", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI4() throws ArgumentsException {
        String[] args = {"-ti", "a", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI5() throws ArgumentsException {
        String[] args = {"-ti", "0", "a"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI6() throws ArgumentsException {
        String[] args = {"-ti", "a", "a"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI7() throws ArgumentsException {
        String[] args = {"-ti", "0", "0"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI8() throws ArgumentsException {
        String[] args = {"-ti", "42", "2"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI9() throws ArgumentsException {
        String[] args = {"-ti", "-2", "-42"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI10() throws ArgumentsException {
        String[] args = {"-ti", "1", "1", "1", "1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI11() throws ArgumentsException {
        String[] args = {"-ti", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI12() throws ArgumentsException {
        String[] args = {"-ti", "1", "1", "2", "1", "3", "1", "4", "1", "5", "1"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsTI13() throws ArgumentsException {
        String[] args = {"-ti", "1", "1", "2", "1", "3", "1", "4", "1", "5", "1", "6", "1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI14() throws ArgumentsException {
        String[] args = {"-ti", "1", "1", "-s"};
        new Simulateur(args);

        args = new String[]{"-ti", "1", "1", "-codeur"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsTI15() throws ArgumentsException {
        String[] args = {"-ti", "1", "1", "2", "1", "3", "1", "4", "1", "5", "1", "6", "1", "-s"};
        new Simulateur(args);
    }
}
