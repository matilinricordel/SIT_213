package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

/**
 * 
 */
public class EmetteurRZ extends Emetteur{
	/*
	 * Instanciation des parametres par defaut
	 * 
	 * @param
	 * signalEchantillonne - information de float qui correspond au signal echantillone
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * nbTechantillon - int qui correspond au nombre d'echantillon par bit
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
    private int nbTechantillon;

    /*
	 * Constructeur de l'emetteur RZ
	 * 
	 * @param
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * nbTechantillon - int qui correspond au nombre d'echantillon par bit
	 * 
	 */
    /**
     * @param vmax .
     * @param nbTechantillon .
     */
    public EmetteurRZ(float vmax, int nbTechantillon)
    {
        this.vmax = vmax;
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
     * Convertit l'information reeue en signal analogique RZ
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
        this.signalEchantillonne = new Information<Float>();
        // Pour chaque bit de l'information reeue
        for(Boolean bit : informationRecue)
        {
        	// Si le bit est "True"
            if(bit)
            {
            	int nbdebut = nbTechantillon/3;
            	int nbmilieu = nbTechantillon-2*nbdebut;
            	int nbfin = nbdebut;
            	// Les echantillons correspondant au premier tier du bit ont une amplitude nulle 
                for(int i=0; i<nbdebut; i++) this.signalEchantillonne.add(0f);
                // Les echantillons correspondant au deuxieme tier du bit ont une amplitude egale e vmax 
                for(int i=0; i<nbmilieu; i++) this.signalEchantillonne.add(vmax);
                // Les echantillons correspondant au dernier tier du bit ont une amplitude nulle 
                for(int i=0; i<nbdebut; i++) this.signalEchantillonne.add(0f);
            }
            // Si le bit est "False"
            else
            {
            	// Les echantillons du bit ont tous une amplitude nulle
                for(int i=0; i<nbTechantillon; i++) this.signalEchantillonne.add(0f);
            }
        }
        /* S'il n'y a pas autant d'echantillons dans le signal echantillone que dans l'information reeue, 
        alors on ajoute des echantillons nuls au signal echantillonne
        */
        while(signalEchantillonne.nbElements()<informationRecue.nbElements()*nbTechantillon)
        {
            signalEchantillonne.add(0f);
        }
        // On instancie l'information emise avec le signal echantillonne
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
        EmetteurRZ erz = new EmetteurRZ(5, 8);
        erz.recevoir(test);
    }
}