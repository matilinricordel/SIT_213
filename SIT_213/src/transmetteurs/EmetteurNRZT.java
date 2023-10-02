package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

/**
 * 
 */
public class EmetteurNRZT extends Emetteur{
	/*
	 * Instanciation des parametres par defaut
	 * 
	 * @param
	 * signalEchantillonne - information de float qui correspond au signal echantillone
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * vmin - float qui correspond e la tension minimal du signal analogique
	 * 
	 */
    /**
     * 
     */
    Information<Float> signalEchantillonne;
    /**
     * 
     */
    private float vmax;
    /**
     * 
     */
    private float vmin;

    /*
	 * Constructeur de l'emetteur NRZT
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'echantillon par bit
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * vmin - float qui correspond e la tension minimal du signal analogique
	 * 
	 */
    /**
     * @param vmax .
     * @param vmin .
     * @param nbTechantillon .
     */
    public EmetteurNRZT(float vmax, float vmin, int nbTechantillon)
    {
        this.vmax = vmax;
        this.vmin = vmin;
        this.nbTechantillon = nbTechantillon;
    }

    /* 
     * Initialise l'information reeue, la convertit et l'emet
     * 
     * @param
     * information - information e recevoir qui contient des booleens
     * 
     * @exception 
     * InformationNonConformeException - Verifie que l'information est conforme
     * 
     */
    /**
     *
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        super.recevoir(information);
    }

    /* 
     * Convertit l'information reeue en signal analogique NRZT
     * 
     * @exception 
     * InformationNonConformeException - Verifie que l'information est conforme
     * 
     */
    /**
     *
     */
    @Override
    public void conversion() {
        /* Il est important de raisonner en periode T et non pas en echantillon
        chaque periode est caracterisee par un nombre d'echantillon
        */
        signalEchantillonne = new Information<Float>();
        // Il faut 2 fois T/3 pour passer d'un etat e un autre
        int nbEchantillonTransition = 2*nbTechantillon/3;
        // Creation d'un tableau contenant toutes les valeurs d'echantillonage de la pente
        float valeurInts [] = new float[nbEchantillonTransition];
        for(int i=0;i!=nbEchantillonTransition; i+=1) valeurInts[i]=(vmin+(i*(vmax-vmin))/nbEchantillonTransition);
        // Boucle sur les valeurs des echantillons
        for(int j=0; j<informationRecue.nbElements(); j++)
        {
            if(j==0) // Depart de l'etat initial
            {
                if(informationRecue.iemeElement(j)) { // Recuperation de du bit concerne
                    for(int i=nbEchantillonTransition/2; i!=nbEchantillonTransition; i++) signalEchantillonne.add(valeurInts[i]);
                    // Arrive en haut de la pente, completion du deuxieme T/3 avec la valeur du max
                    for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmax);
                } else {
                    for(int i=nbEchantillonTransition/2;i!=nbEchantillonTransition; i+=1) signalEchantillonne.add(valeurInts[nbEchantillonTransition-i-1]);
                    for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmin);
                }
            }
            else{
                // Verification si on a deux fois la même valeur de bit d'affile true true ou false false
                if(informationRecue.iemeElement(j-1)==informationRecue.iemeElement(j)) {
                    if(informationRecue.iemeElement(j)) {
                        for(int i=0;i!=nbTechantillon; i+=1) signalEchantillonne.add(vmax);
                    } else {
                        for(int i=0;i!=nbTechantillon; i+=1) signalEchantillonne.add(vmin);
                    }
                } else{
                    if(informationRecue.iemeElement(j)) { // Recuperation de du bit concerne
                        // On lit le tableau de transition dans le sens initial car false => true pendant 2*T/3 (entre deux T respectives aux 2 bits)
                        for(int i=0; i!=nbEchantillonTransition; i++) signalEchantillonne.add(valeurInts[i]);
                        // On complete le deuxieme T/3 du bit courant avec la valeur du maximum
                        for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmax);
                    } else {
                        // On lit le tableau de transition dans l'autre sens car true => false pendant 2*T/3 (entre deux T respectives aux 2 bits)
                        for(int i=0;i!=nbEchantillonTransition; i+=1) signalEchantillonne.add(valeurInts[nbEchantillonTransition-i-1]);
                        // On complete le deuxieme T/3 du bit courant avec la valeur du minimum
                        for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmin);
                    }
                }
            }
        }
        // Si l'echantillonnage n'est pas un multiple de 3 on complete avec les valeurs de v precedent
        while(signalEchantillonne.nbElements()<informationRecue.nbElements()*nbTechantillon)
        {
            if(informationRecue.iemeElement(informationRecue.nbElements()-1)) signalEchantillonne.add(vmax);
            else signalEchantillonne.add(vmin);
        }
        informationEmise = signalEchantillonne;
    }

    /**
     * @param args .
     * @throws InformationNonConformeException .
     */
    public static void main(String[] args) throws InformationNonConformeException {
        Information<Boolean> test = new Information<Boolean>();
        test.add(true);
        test.add(false);
        test.add(true);
        EmetteurNRZT enrzt = new EmetteurNRZT(5, -5, 9);
        enrzt.recevoir(test);
    }
}

