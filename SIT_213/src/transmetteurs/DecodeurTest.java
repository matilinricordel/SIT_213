package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class DecodeurTest {

    @Test
    public void decodageSuiteDeZero() throws InformationNonConformeException {
        Information<Boolean> messageCode = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();
        for (int i = 0; i < 10; i++) {
            messageCode.add(false);
            messageCode.add(true);
            messageCode.add(false);
            sortieAttendue.add(false);
        }

        Decodeur decodeur = new Decodeur();
        decodeur.recevoir(messageCode);
        assertEquals("Le décodage est incorrect", sortieAttendue, decodeur.getInformationEmise());
    }

    @Test
    public void decodageSuiteDeUn() throws InformationNonConformeException {
        Information<Boolean> messageCode = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();
        for (int i = 0; i < 10; i++) {
            messageCode.add(true);
            messageCode.add(false);
            messageCode.add(true);
            sortieAttendue.add(true);
        }

        Decodeur decodeur = new Decodeur();
        decodeur.recevoir(messageCode);
        assertEquals("Le décodage est incorrect", sortieAttendue, decodeur.getInformationEmise());
    }

    @Test
    public void decodageMelangeZeroUn() throws InformationNonConformeException {
        Information<Boolean> messageCode = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();
        Random rd = new Random();
        for (int i = 0; i < 10; i++) {
            if (rd.nextBoolean()) {
                messageCode.add(true);
                messageCode.add(false);
                messageCode.add(true);
                sortieAttendue.add(true);
            } else {
                messageCode.add(false);
                messageCode.add(true);
                messageCode.add(false);
                sortieAttendue.add(false);
            }
        }

        Decodeur decodeur = new Decodeur();
        decodeur.recevoir(messageCode);
        assertEquals("La correction est erronée", sortieAttendue, decodeur.getInformationEmise());
    }

    @Test
    public void decodageCorrection() throws InformationNonConformeException {
        Information<Boolean> messageCode = new Information<Boolean>();
        Information <Boolean> sortieAttendue = new Information<Boolean>();

        // 000 -> 0
        messageCode.add(false);
        messageCode.add(false);
        messageCode.add(false);
        sortieAttendue.add(false);

        // 001 -> 1
        messageCode.add(false);
        messageCode.add(false);
        messageCode.add(true);
        sortieAttendue.add(true);

        // 011 -> 0
        messageCode.add(false);
        messageCode.add(true);
        messageCode.add(true);
        sortieAttendue.add(false);

        // 100 -> 1
        messageCode.add(true);
        messageCode.add(false);
        messageCode.add(false);
        sortieAttendue.add(true);

        // 110 -> 0
        messageCode.add(true);
        messageCode.add(true);
        messageCode.add(false);
        sortieAttendue.add(false);

        // 111 -> 1
        messageCode.add(true);
        messageCode.add(true);
        messageCode.add(true);
        sortieAttendue.add(true);

        Decodeur decodeur = new Decodeur();
        decodeur.recevoir(messageCode);
        assertEquals("Le codage est incorrect", sortieAttendue, decodeur.getInformationEmise());
    }

}