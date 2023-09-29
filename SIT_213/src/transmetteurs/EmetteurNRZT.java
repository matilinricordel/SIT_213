package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

public class EmetteurNRZT extends Emetteur{
	/*
	 * Instanciation des param�tres par d�faut
	 * 
	 * @param
	 * signalEchantillonne - information de float qui correspond au signal �chantillon�
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * vmin - float qui correspond � la tension minimal du signal analogique
	 * 
	 */
    Information<Float> signalEchantillonne;
    private float vmax;
    private float vmin;

    /*
	 * Constructeur de l'�metteur NRZT
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * vmin - float qui correspond � la tension minimal du signal analogique
	 * 
	 */
    public EmetteurNRZT(float vmax, float vmin, int nbTechantillon)
    {
        this.vmax = vmax;
        this.vmin = vmin;
        this.nbTechantillon = nbTechantillon;
    }

    /* 
     * Initialise l'information re�ue, la convertit et l'�met
     * 
     * @param
     * information - information � recevoir qui contient des bool�ens
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        super.recevoir(information);
    }

    /* 
     * Convertit l'information re�ue en signal analogique NRZT
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    @Override
    public void conversion() {
        /* Il est important de raisonner en p�riode T et non pas en �chantillon
        chaque p�riode est caract�ris�e par un nombre d'�chantillon
        */
        signalEchantillonne = new Information<Float>();
        // Il faut 2 fois T/3 pour passer d'un �tat � un autre
        int nbEchantillonTransition = 2*nbTechantillon/3;
        // Cr�ation d'un tableau contenant toutes les valeurs d'�chantillonage de la pente
        float valeurInts [] = new float[nbEchantillonTransition];
        for(int i=0;i!=nbEchantillonTransition; i+=1) valeurInts[i]=(vmin+(i*(vmax-vmin))/nbEchantillonTransition);
        // Boucle sur les valeurs des �chantillons
        for(int j=0; j<informationRecue.nbElements(); j++)
        {
            if(j==0) // D�part de l'�tat initial
            {
                if(informationRecue.iemeElement(j)) { // R�cup�ration de du bit concern�
                    for(int i=nbEchantillonTransition/2; i!=nbEchantillonTransition; i++) signalEchantillonne.add(valeurInts[i]);
                    // Arriv� en haut de la pente, compl�tion du deuxi�me T/3 avec la valeur du max
                    for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmax);
                } else {
                    for(int i=nbEchantillonTransition/2;i!=nbEchantillonTransition; i+=1) signalEchantillonne.add(valeurInts[nbEchantillonTransition-i-1]);
                    for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmin);
                }
            }
            else{
                // V�rification si on a deux fois la même valeur de bit d'affil� true true ou false false
                if(informationRecue.iemeElement(j-1)==informationRecue.iemeElement(j)) {
                    if(informationRecue.iemeElement(j)) {
                        for(int i=0;i!=nbTechantillon; i+=1) signalEchantillonne.add(vmax);
                    } else {
                        for(int i=0;i!=nbTechantillon; i+=1) signalEchantillonne.add(vmin);
                    }
                } else{
                    if(informationRecue.iemeElement(j)) { // R�cup�ration de du bit concern�
                        // On lit le tableau de transition dans le sens initial car false => true pendant 2*T/3 (entre deux T respectives aux 2 bits)
                        for(int i=0; i!=nbEchantillonTransition; i++) signalEchantillonne.add(valeurInts[i]);
                        // On compl�te le deuxi�me T/3 du bit courant avec la valeur du maximum
                        for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmax);
                    } else {
                        // On lit le tableau de transition dans l'autre sens car true => false pendant 2*T/3 (entre deux T respectives aux 2 bits)
                        for(int i=0;i!=nbEchantillonTransition; i+=1) signalEchantillonne.add(valeurInts[nbEchantillonTransition-i-1]);
                        // On compl�te le deuxi�me T/3 du bit courant avec la valeur du minimum
                        for(int i=0;i!=nbTechantillon-nbEchantillonTransition; i+=1) signalEchantillonne.add(vmin);
                    }
                }
            }
        }
        // Si l'�chantillonnage n'est pas un multiple de 3 on compl�te avec les valeurs de v pr�c�dent
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

