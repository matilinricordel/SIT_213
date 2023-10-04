package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import simulateur.Simulateur;
import transmetteurs.Transmetteur;

/**
 * 
 */
public class Codeur extends Transmetteur<Boolean, Boolean> {

    /**
     * Constructeur
     */
    public Codeur() {
        super();
    }

    /**
     * Reception de l'information emise
     *
     * @param information l'information  à recevoir
     * @throws InformationNonConformeException .
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information;
        
        emettre();
    }


    /**
     * Codage de l'information
     */
    public void codage() {
        for (boolean information : this.informationRecue) {
            if (information) {
                //Correspond à 1
                this.informationEmise.add(true);
                this.informationEmise.add(false);
                this.informationEmise.add(true);
            } else {
                //Correspond à 0
                this.informationEmise.add(false);
                this.informationEmise.add(true);
                this.informationEmise.add(false);
            }
        }
    }

    /**
     * Emission de la trame vers le bloc suivant
     *
     * @throws InformationNonConformeException .
     */
    public void emettre() throws InformationNonConformeException {
        this.informationEmise = new Information<>();
        codage();
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
        }
    }
}
