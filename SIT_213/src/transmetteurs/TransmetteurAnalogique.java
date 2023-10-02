package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * Classe Transmetteur pour signaux réels
 */
public abstract class TransmetteurAnalogique extends Transmetteur<Float, Float>{
    /**
     * Information recevant un traitement
     */
    protected Information<Float>  informationTraite;
    @Override
    /**
     * Recevoir une information
     * @param information  l'information à recevoir
     */
    public void recevoir(Information information) throws InformationNonConformeException {
        informationRecue=information;
        traitementduSignal();
        emettre();
    }

    /**
     *
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for(DestinationInterface<Float> destination : destinationsConnectees)
        {
            destination.recevoir(informationTraite);
        }
        informationTraite = informationEmise;
    }

    /**
     * 
     */
    public abstract void traitementduSignal();
}
