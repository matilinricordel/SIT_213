package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class RecepteurTest {

    Information<Boolean> message;
    Information<Boolean> messageMillion;
    Information<Float> messageRZ;
    Information<Float> messageNRZ;
    Information<Float> messageNRZT;
    Float aMin = 0f;
    Float aMax = 1f;

    @Before
    public void setUp() {
        message = new Information<Boolean>();
        for (Boolean bit : new Boolean[]{false, false, false, true, false, true, true, true, false, false})
            message.add(bit);

        messageMillion = new Information<Boolean>();
        Random rd = new Random();
        for (int i = 0; i < 999999; i++)
            messageMillion.add(rd.nextBoolean());
    }

    @Test
    public void decodeRZDefault() throws InformationNonConformeException {
        message = new Information<Boolean>();
        messageRZ = new Information<Float>();
        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            Boolean bit = rd.nextBoolean();
            message.add(bit);
            for (int j = 0; j < 30; j++) {
                if (bit) messageRZ.add(aMax);
                else messageRZ.add(aMin);
            }
        }

        Recepteur recepteur = new Recepteur("RZ");
        recepteur.recevoir(messageRZ);

        assertEquals(100, recepteur.getInformationEmise().nbElements());
    }

    @Test
    public void decodeNRZDefault() throws InformationNonConformeException {
        message = new Information<Boolean>();
        messageNRZ = new Information<Float>();
        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            Boolean bit = rd.nextBoolean();
            message.add(bit);
            for (int j = 0; j < 30; j++) {
                if (bit) messageNRZ.add(aMax);
                else messageNRZ.add(aMin);
            }
        }

        Recepteur recepteur = new Recepteur("NRZ");
        recepteur.recevoir(messageNRZ);

        assertEquals(100, recepteur.getInformationEmise().nbElements());
    }

    @Test
    public void decodeNRZTDefault() throws InformationNonConformeException {
        message = new Information<Boolean>();
        messageNRZT = new Information<Float>();
        Random rd = new Random();
        for (int i = 0; i < 100; i++) {
            Boolean bit = rd.nextBoolean();
            message.add(bit);
            for (int j = 0; j < 30; j++) {
                if (bit) messageNRZT.add(aMax);
                else messageNRZT.add(aMin);
            }
        }

        Recepteur recepteur = new Recepteur("NRZT");
        recepteur.recevoir(messageNRZT);

        assertEquals(100, recepteur.getInformationEmise().nbElements());
    }
}