package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

public class EmetteurNRZT extends Emetteur{
	/*
	 * Instanciation des paramètres par défaut
	 * 
	 * @param
	 * signalEchantillonne - information de float qui correspond au signal échantilloné
	 * vmax - float qui correspond à la tension maximal du signal analogique
	 * vmin - float qui correspond à la tension minimal du signal analogique
	 * 
	 */
    Information<Float> signalEchantillonne;
    private float vmax;
    private float vmin;

    /*
	 * Constructeur de l'émetteur NRZT
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'échantillon par bit
	 * vmax - float qui correspond à la tension maximal du signal analogique
	 * vmin - float qui correspond à la tension minimal du signal analogique
	 * 
	 */
    public EmetteurNRZT(float vmax, float vmin, int nbTechantillon)
    {
        this.vmax = vmax;
        this.vmin = vmin;
        this.nbTechantillon = nbTechantillon;
    }

    /* 
     * Initialise l'information reçue, la convertit et l'émet
     * 
     * @param
     * information - information à recevoir qui contient des booléens
     * 
     * @exception 
     * InformationNonConformeException - Vérifie que l'information est conforme
     * 
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        super.recevoir(information);
    }

    /* 
     * Convertit l'information reçue en signal analogique NRZT
     * 
     * @exception 
     * InformationNonConformeException - Vérifie que l'information est conforme
     * 
     */
    @Override
    public void conversion() {
        /* Il est important de raisonner en période T et non pas en échantillon
        chaque période est caractérisée par un nombre d'échantillon
        */
        signalEchantillonne = new Information<Float>();
        // Il faut 2 fois T/3 pour passer d'un état à un autre
        int nbEchantillonTransition = 2*nbTechantillon/3;
        // Création d'un tableau contenant toutes les valeurs d'échantillonage de la pente
        float valeurInts [] = new float[nbEchantillonTransition];
        for(int i=0;i!=nbEchantillonTransition; i+=1) valeurInts[i]=(vmin+(i*(vmax-vmin))/nbEchantillonTransition);
        // Boucle sur les valeurs des échantillons
        for(int j=0; j<informationRecue.nbElements(); j++)
        {
            if(j==0) // Départ de l'état initial
            {
                if(informationRecue.iemeElement(j)) { // Récupération de du bit concerné
                    for(int i=nbEchantillonTransition/2; i!=nbEchantillonTransition; i++) signalEchantillonne.add(valeurInts[i]);
                    // Arrivé en haut de la pente, complétion du deuxième T/3 avec la valeur du max
                    for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmax);
                } else {
                    for(int i=nbEchantillonTransition/2;i!=nbEchantillonTransition; i+=1) signalEchantillonne.add(valeurInts[nbEchantillonTransition-i-1]);
                    for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmin);
                }
            }
            else{
                // Vérification si on a deux fois la mÃªme valeur de bit d'affilé true true ou false false
                if(informationRecue.iemeElement(j-1)==informationRecue.iemeElement(j)) {
                    if(informationRecue.iemeElement(j)) {
                        for(int i=0;i!=nbTechantillon; i+=1) signalEchantillonne.add(vmax);
                    } else {
                        for(int i=0;i!=nbTechantillon; i+=1) signalEchantillonne.add(vmin);
                    }
                } else{
                    if(informationRecue.iemeElement(j)) { // Récupération de du bit concerné
                        // On lit le tableau de transition dans le sens initial car false => true pendant 2*T/3 (entre deux T respectives aux 2 bits)
                        for(int i=0; i!=nbEchantillonTransition; i++) signalEchantillonne.add(valeurInts[i]);
                        // On complète le deuxième T/3 du bit courant avec la valeur du maximum
                        for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmax);
                    } else {
                        // On lit le tableau de transition dans l'autre sens car true => false pendant 2*T/3 (entre deux T respectives aux 2 bits)
                        for(int i=0;i!=nbEchantillonTransition; i+=1) signalEchantillonne.add(valeurInts[nbEchantillonTransition-i-1]);
                        // On complète le deuxième T/3 du bit courant avec la valeur du minimum
                        for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmin);
                    }
                }
            }
        }
        // Si l'échantillonnage n'est pas un multiple de 3 on complète avec les valeurs de v précédent
        while(signalEchantillonne.nbElements()<informationRecue.nbElements()*nbTechantillon)
        {
            if(informationRecue.iemeElement(informationRecue.nbElements()-1)) signalEchantillonne.add(vmax);
            else signalEchantillonne.add(vmin);
        }
        informationEmise = signalEchantillonne;
    }

    public static void main(String[] args) throws InformationNonConformeException {
        Information<Boolean> test = new Information<Boolean>();
        test.add(true);
        test.add(false);
        test.add(true);
        EmetteurNRZT enrzt = new EmetteurNRZT(5, -5, 9);
        enrzt.recevoir(test);
    }
}

