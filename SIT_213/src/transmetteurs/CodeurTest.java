package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CodeurTest {

    @Test
    public void codageSuiteDeZero() throws InformationNonConformeException {
        Information <Boolean> message = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();
        for (int i = 0; i < 10; i++) {
            message.add(false);
            sortieAttendue.add(false);
            sortieAttendue.add(true);
            sortieAttendue.add(false);
        }

        Codeur codeur = new Codeur();
        codeur.recevoir(message);
        assertEquals("Le codage est incorrect", sortieAttendue, codeur.getInformationEmise());
    }

    @Test
    public void codageSuiteDeUn() throws InformationNonConformeException {
        Information <Boolean> message = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();
        for (int i = 0; i < 10; i++) {
            message.add(true);
            sortieAttendue.add(true);
            sortieAttendue.add(false);
            sortieAttendue.add(true);
        }

        Codeur codeur = new Codeur();
        codeur.recevoir(message);
        assertEquals("Le codage est incorrect", sortieAttendue, codeur.getInformationEmise());
    }

    @Test
    public void codageMelageZeroUn() throws InformationNonConformeException {
        Information <Boolean> message = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();
        Random rd = new Random();
        for (int i = 0; i < 10; i++) {
            if (rd.nextBoolean()) {
                message.add(true);
                sortieAttendue.add(true);
                sortieAttendue.add(false);
                sortieAttendue.add(true);
            } else {
                message.add(false);
                sortieAttendue.add(false);
                sortieAttendue.add(true);
                sortieAttendue.add(false);
            }
        }

        Codeur codeur = new Codeur();
        codeur.recevoir(message);
        assertEquals("Le codage est incorrect", sortieAttendue, codeur.getInformationEmise());
    }

}