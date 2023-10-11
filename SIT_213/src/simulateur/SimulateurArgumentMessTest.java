package simulateur;

import org.junit.Test;

public class SimulateurArgumentMessTest {

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsMess1() throws ArgumentsException {
        String[] args = {"-mess"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsMess2() throws ArgumentsException {
        String[] args = {"-mess", "0"};
        new Simulateur(args);
    }

    @Test(expected = ArgumentsException.class)
    public void analyseArgumentsMess3() throws ArgumentsException {
        String[] args = {"-mess", "12afcd"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess4() throws ArgumentsException {
        String[] args = {"-mess", "1"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess5() throws ArgumentsException {
        String[] args = {"-mess", "101110"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess6() throws ArgumentsException {
        String[] args = {"-mess", "1111111"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess7() throws ArgumentsException {
        String[] args = {"-mess", "0000000"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess8() throws ArgumentsException {
        String[] args = {"-mess", "0001011100"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess9() throws ArgumentsException {
        String[] args = {"-mess", "010101010101"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess10() throws ArgumentsException {
        // message binaire de 256 symboles
        String[] args = {"-mess", "0110101010111000101101001010101011001010111000111010101010101010101101001010101100101010111010010110100111010010101000101011101010010101100101010101010101001010101001011001010101000111010101010100101010100010101010101000101010110101010100101010101010100111"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess11() throws ArgumentsException {
        String[] args = {"-mess", "42"};
        new Simulateur(args);
    }

    @Test
    public void analyseArgumentsMess12() throws ArgumentsException {
        String[] args = {"-mess", "999999"};
        new Simulateur(args);
    }

}