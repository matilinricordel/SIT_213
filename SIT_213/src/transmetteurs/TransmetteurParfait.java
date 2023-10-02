package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * @param <R> .
 * @param <E> .
 */
public class TransmetteurParfait<R, E> extends Transmetteur<R, E>{
    /**
     *
     */
    @Override
    public void recevoir(Information information) throws InformationNonConformeException {
        informationRecue = information;
        this.emettre();
    }

    /**
     *
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for(DestinationInterface <E> destination : destinationsConnectees)
        {
            destination.recevoir((Information<E>) informationRecue);
        }
    }
}
