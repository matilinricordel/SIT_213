package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

import java.util.Random;

import static org.junit.Assert.*;

public class EmetteurTest {

    Information<Boolean> message;
    Information<Boolean> messageMillion;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();

    @Before
    public void setUp() {
        message = new Information<Boolean>();
        for (Boolean bit : new Boolean[]{false, false, false, true, false, true, true, true, false, false}) {
            message.add(bit);
        }

        messageMillion = new Information<Boolean>();
        Random rd = new Random();
        for (int i = 0; i < 999999; i++)
            messageMillion.add(rd.nextBoolean());
    }

    private boolean verifyAMinAMaxOnly(Information<Float> message, Float aMin, Float aMax) {
        for (Float echantillon : message)
            if (!echantillon.equals(aMin) && !echantillon.equals(aMax))
                return false;
        return true;
    }

    private boolean verifyBetweenAMinAMax(Information<Float> message, Float aMin, Float aMax) {
        for (Float echantillon : message)
            if (echantillon < aMin && echantillon > aMax)
                return false;
        return true;
    }

    @Test
    public void codeRZDefault() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("RZ");
        emetteur.recevoir(message);
        int longuerAttendue = 30 * 10;
        int longeurEmise = emetteur.informationEmise.nbElements();

        assertEquals(longuerAttendue, longeurEmise);
        assertTrue(verifyAMinAMaxOnly(emetteur.informationEmise, 0f, 1f));
    }

    @Test
    public void codeNRZDefault() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZ");
        emetteur.recevoir(message);
        int longuerAttendue = 30 * 10;
        int longeurEmise = emetteur.informationEmise.nbElements();

        assertEquals(longuerAttendue, longeurEmise);
        assertTrue(verifyAMinAMaxOnly(emetteur.informationEmise, 0f, 1f));
    }

    @Test
    public void codeNRZTDefault() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZT");
        emetteur.recevoir(message);
        int longuerAttendue = 30 * 10;
        int longeurEmise = emetteur.informationEmise.nbElements();

        assertEquals(longuerAttendue, longeurEmise);
        assertTrue(verifyBetweenAMinAMax(emetteur.informationEmise, 0f, 1f));
    }

    @Test
    public void codeRZMillion() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("RZ");
        emetteur.recevoir(messageMillion);
        assertEquals(30 * 999999, emetteur.getInformationEmise().nbElements());
    }

    @Test
    public void codeNRZMillion() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZ");
        emetteur.recevoir(messageMillion);
        assertEquals(30 * 999999, emetteur.getInformationEmise().nbElements());
    }

    @Test
    public void codeNRZTMillion() throws InformationNonConformeException {
        Emetteur emetteur = new Emetteur("NRZT");
        emetteur.recevoir(messageMillion);
        assertEquals(30 * 999999, emetteur.getInformationEmise().nbElements());
    }

    @Test
    public void codeRZMultipleNE() throws InformationNonConformeException {
        for (int ne = 1; ne < 1000; ne++) {
            Emetteur emetteur = new Emetteur("RZ", ne, 0f, 1f);
            emetteur.recevoir(message);
            //if (ne * 10 != emetteur.getInformationEmise().nbElements())
            //    collector.addError(new Throwable("Problem with ne = " + ne));
            collector.checkThat("Problem with ne = " + ne,
                                emetteur.getInformationEmise().nbElements(),
                                is(ne * 10));
        }
    }
}