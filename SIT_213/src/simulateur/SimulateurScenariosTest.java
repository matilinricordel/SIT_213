package simulateur;

import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Test;
import transmetteurs.Emetteur;

import javax.crypto.spec.PSource;

import static org.junit.Assert.*;

public class SimulateurScenariosTest {

    @Before
    public void setUp() {
    }

    @Test
    public void enchainementSymbolesRZTest() throws ArgumentsException {
        String[] args = {"-mess", "0001011100", "-form", "RZ"};
        Simulateur simulateur = new Simulateur(args);
    }

    @Test
    public void enchainementSymbolesNRZTest() throws ArgumentsException {
        String[] args = {"-mess", "0001011100", "-form", "NRZ"};
        Simulateur simulateur = new Simulateur(args);
    }

    @Test
    public void enchainementSymbolesNRZTTest() throws ArgumentsException {
        String[] args = {"-mess", "0001011100", "-form", "NRZT"};
        Simulateur simulateur = new Simulateur(args);
    }

    @Test
    public void nombreEchantillonCroissantRZ() throws Exception {
        for (int ne = 1; ne < 1000; ne++) {
            String[] args = {"-mess", "100", "-nbEch", String.valueOf(ne), "-form", "RZ", "-seed", "1"};
            Simulateur simulateur = new Simulateur(args);
            simulateur.execute();
        }
    }

    @Test
    public void nombreEchantillonCroissantNRZ() throws Exception {
        for (int ne = 1; ne < 1000; ne++) {
            String[] args = {"-mess", "100", "-nbEch", String.valueOf(ne), "-form", "NRZ", "-seed", "1"};
            Simulateur simulateur = new Simulateur(args);
            simulateur.execute();
        }
    }

    @Test
    public void nombreEchantillonCroissantNRZT() throws Exception {
        for (int ne = 1; ne < 1000; ne++) {
            String[] args = {"-mess", "100", "-nbEch", String.valueOf(ne), "-form", "NRZT", "-seed", "1"};
            Simulateur simulateur = new Simulateur(args);
            simulateur.execute();
        }
    }

    @Test
    public void tebNonRegression() throws Exception {
        String[] args = {"-seed", "0", "-mess", "20", "-nbEch", "100", "-form", "NRZ", "-snrpb", "8"};
        Simulateur simulateur = new Simulateur(args);
        simulateur.execute();

        assertTrue("Le TEB doit renvoyer un nombre flotant", 0f < simulateur.calculTauxErreurBinaire());
    }

    @Test
    public void seedAppliqueeTEB() throws Exception {
        String[] args = {"-seed", "0", "-snrpb", "0"};
        Simulateur simulateur = new Simulateur(args);
        simulateur.execute();
        Float teb1 = simulateur.calculTauxErreurBinaire();

        simulateur.execute();
        Float teb2 = simulateur.calculTauxErreurBinaire();

        assertTrue("La génération aléatroire du bruit doit utiliser la graine donnée en argument", teb1.equals(teb2));
    }

    @Test
    public void codeRZMillion() throws Exception {
        String[] args = {"-mess", "999999", "-form", "RZ", "-seed", "1"};
        Simulateur simulateur = new Simulateur(args);
        simulateur.execute();
        //assertEquals(999999, simulateur);
    }

    @Test
    public void codeNRZMillion() throws Exception {
        String[] args = {"-mess", "999999", "-form", "NRZ", "-seed", "1"};
        Simulateur simulateur = new Simulateur(args);
        simulateur.execute();
        //assertEquals(30 * 999999, emetteur.getInformationEmise().nbElements());
    }

    @Test
    public void codeNRZTMillion() throws Exception {
        String[] args = {"-mess", "999999", "-form", "RZ", "-seed", "1"};
        Simulateur simulateur = new Simulateur(args);
        simulateur.execute();
        //assertEquals(30 * 999999, emetteur.getInformationEmise().nbElements());
    }

    @Test
    public void codeurEtGrandTrajetMultiple() throws Exception {
        String[] args = {"-mess", "10000", "-form", "NRZ", "-snrpb", "0", "-ti", "60", "0.5", "-codeur", "-seed", "1"};
        Simulateur simulateur = new Simulateur(args);
        simulateur.execute();
    }
}