package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class TransmetteurScenariosTest {

    int ne = 30;
    Information <Boolean> message;

    @Before
    public void setUp() throws Exception {
        message = new Information<Boolean>();
        Random rd = new Random();
        for (int i = 0; i < 10; i++)
            message.add(rd.nextBoolean());
    }

    @Test
    public void transmissionRZParfaiteDefaut() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("RZ");
        Recepteur recepteur = new Recepteur("RZ");
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmissionRZParfaiteAmplitudePosPos() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("RZ", ne, 1f, 2f);
        Recepteur recepteur = new Recepteur("RZ", ne, 1f, 2f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmissionRZParfaiteAmplitudeNegNeg() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("RZ", ne, -2f, -1f);
        Recepteur recepteur = new Recepteur("RZ", ne, -2f, -1f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmissionRZParfaiteAmplitudeNegPos() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("RZ", ne, -1f, 1f);
        Recepteur recepteur = new Recepteur("RZ", ne, -1f, 1f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZParfaiteDefaut() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZ");
        Recepteur recepteur = new Recepteur("NRZ");
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZParfaiteAmplitudePosPos() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZ", ne, 1f, 2f);
        Recepteur recepteur = new Recepteur("NRZ", ne, 1f, 2f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZParfaiteAmplitudeNegNeg() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZ", ne, -2f, -1f);
        Recepteur recepteur = new Recepteur("NRZ", ne, -2f, -1f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZParfaiteAmplitudeNegPos() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZ", ne, -1f, 1f);
        Recepteur recepteur = new Recepteur("NRZ", ne, -1f, 1f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZTParfaiteDefaut() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZT");
        Recepteur recepteur = new Recepteur("NRZT");
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZTParfaiteAmplitudePosPos() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZT", ne, 1f, 2f);
        Recepteur recepteur = new Recepteur("NRZT", ne, 1f, 2f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZTParfaiteAmplitudeNegNeg() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZT", ne, -2f, -1f);
        Recepteur recepteur = new Recepteur("NRZT", ne, -2f, -1f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }

    @Test
    public void transmisisonNRZTParfaiteAmplitudeNegPos() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZT", ne, -1f, 1f);
        Recepteur recepteur = new Recepteur("NRZT", ne, -1f, 1f);
        emetteur.connecter(recepteur);
        emetteur.recevoir(message);

        assertEquals("Les messages émis et reçus ne sont pas identiques", message, recepteur.getInformationEmise());
    }
}