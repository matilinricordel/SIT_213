package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import simulateur.Simulateur;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 
 */
public class TrajetsMultiples extends Transmetteur<Float, Float> {

    /**
     * Valeurs de décalage et d'atténuation du trajet multiple.
     */
    private Map<Integer, Float> trajetMult;

    /**
     * 
     */
    public TrajetsMultiples() {
        this.trajetMult = new HashMap<>();
    }

    /**
     * Permet d'ajouter un TM
     *
     * @param dt Décalage temporel
     * @param ar Amplitude relative
     */
    public void addTrajetMultiple(int dt, float ar) {
        trajetMult.compute(dt, (key, val) -> val == null ? ar : val + ar > 1 ? 1 : val + ar);
    }

    /**
     * Permet de savoir combien de trajets sont en mémoire
     *
     * @return nombre de TM
     */
    public int nbTrajets() {
        return trajetMult.size();
    }

    /**
     * permet de mettre des trajets multiples dans la transmission
     */
    private void ajouterTM() {
        this.informationEmise = this.informationRecue;
        for (Map.Entry<Integer, Float> entry : this.trajetMult.entrySet()) {
            //combinaison des TM en même temps que leur génération
            //generationTrajet(atténuation, décalage)
            this.informationEmise = combinaison(this.informationEmise, generationTrajet(entry.getValue(), entry.getKey()));
            /*
            if(Simulateur.showDebug()){
                System.out.println("Génération d'un TM pour atténuation : "+entry.getValue()+" décalage : "+entry.getValue());
            }
            */
            this.informationEmise = combinaison(this.informationEmise, generationTrajet(entry.getValue(),entry.getKey()));
        }
    }

    /**
     * Permet de générer un trajet multiple sous forme d'information
     *
     * @param attenuation Attenuation du TM
     * @param decalage    Décalage du TM
     * @return Information TM
     */
    private Information<Float> generationTrajet(float attenuation, int decalage) {
        Information<Float> trajet;
        Iterator<Float> iterator = this.informationRecue.iterator();
        trajet = new Information<>();
        for (int i = 0; i < this.informationRecue.nbElements(); i++) {
        	//System.out.println(attenuation);
            if (i > decalage) {
                trajet.add(attenuation/2 * iterator.next());
            } else {
                trajet.add(0f);
            }
        }
        return trajet;
    }

    /**
     * Fonction permettant de faire l'addition de deux listes
     *
     * @param liste1 .
     * @param liste2 .
     * @return combinaiseon des listes
     */
    private Information<Float> combinaison(Information<Float> liste1, Information<Float> liste2) {
        Iterator<Float> it1 = liste1.iterator();
        Iterator<Float> it2 = liste2.iterator();
        Information<Float> comb = new Information<>();
        while (it1.hasNext() && it2.hasNext()) {
            comb.add(it1.next() + it2.next());
        }
        return comb;
    }


    /**
     * Reception de l'information
     *
     * @param information l'information  à recevoir
     * @throws InformationNonConformeException .
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        this.informationRecue = information;
        this.informationEmise = new Information<>();

        /*
        if (Simulateur.showDebug()) {
            System.out.println("Trajets multiples:");
            System.out.println(trajetMult);
        }
        */

        ajouterTM();
        this.emettre();
    }

    /**
     * Emmission des informations avec TM
     *
     * @throws InformationNonConformeException .
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        if (this.informationRecue.nbElements() < 6) {
            throw new InformationNonConformeException("Message de taille inférieure à 6 bits");
        }
        for (DestinationInterface<Float> destinationConnectee : this.destinationsConnectees) {
        	//System.out.println("emission vers "+ destinationConnectee);
            destinationConnectee.recevoir(informationEmise);
            //System.out.println("ok");
        }
    }
}
