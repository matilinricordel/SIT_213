package transmetteurs;

import information.Information;
import information.InformationNonConformeException;
import transmetteurs.Transmetteur;

import java.util.Iterator;

import destinations.DestinationInterface;

public class DecodeurNG extends Transmetteur<Boolean,Boolean> {

    /**
     * reception des informations
     * 
     * @param information l'information  à recevoir
     * @throws InformationNonConformeException
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information; //on recoit des trames de booleens de taille N*3
        this.analyze();
        this.emettre(); //on emet l'info juste après qu'on l'ai recue
    }

    /**
     * émission des informations
     *
     * @throws InformationNonConformeException
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue.nbElements() < 6) { //Si notre trame est trop petite, on ne l'emet pas
            throw new InformationNonConformeException("Message de taille inférieure Ã  6 bits");
        }
        for (DestinationInterface<Boolean> destinationConnectee : this.destinationsConnectees) {
            //Si notre sortie est au bon endroit,
            destinationConnectee.recevoir(informationEmise);
            //la destination connectée peut commencer à recevoir ce qu'on envoi : infoEmise
        }
    }

    /**
     * décodage des informations
     */
    private void analyze() {
    	//System.out.println(informationEmise);
        this.informationEmise = new Information<>();
        Iterator<Boolean> iterator = informationRecue.iterator();
        boolean b1, b2, b3;

        while (iterator.hasNext()) {
            b1 = iterator.next();
            b2 = iterator.next();
            b3 = iterator.next();

            if ((!b1 && !b2 && !b3) || (!b1 && b2 && !b3) || (!b1 && b2 && b3) || (b1 && b2 && !b3)) {
                this.informationEmise.add(false);
            } else {
                this.informationEmise.add(true);
            }
        }
    }

}
