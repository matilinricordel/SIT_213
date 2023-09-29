package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

public class EmetteurRZ extends Emetteur{
	/*
	 * Instanciation des param�tres par d�faut
	 * 
	 * @param
	 * signalEchantillonne - information de float qui correspond au signal �chantillon�
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * 
	 */
    Information<Float> signalEchantillonne;
    private float vmax;
    private int nbTechantillon;

    /*
	 * Constructeur de l'�metteur RZ
	 * 
	 * @param
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * 
	 */
    public EmetteurRZ(float vmax, int nbTechantillon)
    {
        this.vmax = vmax;
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
     * Convertit l'information re�ue en signal analogique RZ
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
        this.signalEchantillonne = new Information<Float>();
        // Pour chaque bit de l'information re�ue
        for(Boolean bit : informationRecue)
        {
        	// Si le bit est "True"
            if(bit)
            {
            	int nbdebut = nbTechantillon/3;
            	int nbmilieu = nbTechantillon-2*nbdebut;
            	int nbfin = nbdebut;
            	// Les �chantillons correspondant au premier tier du bit ont une amplitude nulle 
                for(int i=0; i<nbdebut; i++) this.signalEchantillonne.add(0f);
                // Les �chantillons correspondant au deuxi�me tier du bit ont une amplitude �gale � vmax 
                for(int i=0; i<nbmilieu; i++) this.signalEchantillonne.add(vmax);
                // Les �chantillons correspondant au dernier tier du bit ont une amplitude nulle 
                for(int i=0; i<nbdebut; i++) this.signalEchantillonne.add(0f);
            }
            // Si le bit est "False"
            else
            {
            	// Les �chantillons du bit ont tous une amplitude nulle
                for(int i=0; i<nbTechantillon; i++) this.signalEchantillonne.add(0f);
            }
        }
        /* S'il n'y a pas autant d'�chantillons dans le signal �chantillon� que dans l'information re�ue, 
        alors on ajoute des �chantillons nuls au signal �chantillonn�
        */
        while(signalEchantillonne.nbElements()<informationRecue.nbElements()*nbTechantillon)
        {
            signalEchantillonne.add(0f);
        }
        // On instancie l'information �mise avec le signal �chantillonn�
        informationEmise = signalEchantillonne;
    }

    public static void main(String[] args) throws InformationNonConformeException {
        Information<Boolean> test = new Information<Boolean>();
        test.add(true);
        test.add(false);
        test.add(true);
        EmetteurRZ erz = new EmetteurRZ(5, 8);
        erz.recevoir(test);
    }
}