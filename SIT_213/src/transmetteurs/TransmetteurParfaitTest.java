package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import transmetteurs.TransmetteurParfait;

public class TransmetteurParfaitTest {

    private TransmetteurParfait transmetteur;
    @Before
    public void setUp() throws Exception {
        transmetteur = new TransmetteurParfait();
    }

    @Test
    public void transnmettreRien() throws InformationNonConformeException {
        Information<Boolean> messageVide = new Information<Boolean>();
        transmetteur.recevoir(messageVide);

        assertEquals(messageVide, transmetteur.getInformationEmise());
    }

    @Test
    public void transnmettreMessageBooleens() throws InformationNonConformeException {
        Information<Boolean> message = new Information<Boolean>();
        message.add(true);
        message.add(false);
        transmetteur.recevoir(message);

        assertEquals(transmetteur.getInformationEmise(), message);
    }
}