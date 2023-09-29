package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

public class EmetteurNRZ extends Emetteur{
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
	 * Constructeur de l'�metteur NRZ
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * vmin - float qui correspond � la tension minimal du signal analogique
	 * 
	 */
    public EmetteurNRZ(float vmax, float vmin, int nbTechantillon)
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
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException{
        super.recevoir(information);
    }

    /* 
     * Convertit l'information re�ue en signal analogique NRZ
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    @Override
    public void conversion() {
        this.signalEchantillonne = new Information<Float>();
        for(Boolean bit : informationRecue)
        {
            for(int i=0; i<nbTechantillon; i++)
            {
                if(bit) signalEchantillonne.add(vmax);
                else signalEchantillonne.add(vmin);
            }
        }
        informationEmise = signalEchantillonne;
    }

    public static void main(String[] args) throws InformationNonConformeException {
        Information<Boolean> test = new Information<Boolean>();
        test.add(true);
        test.add(false);
        test.add(false);

        EmetteurNRZ enrz = new EmetteurNRZ(5, -5, 2);
        enrz.recevoir(test);

        Information<Float> test2 = new Information<Float>();
        test2.add(5f);
        test2.add(5f);
        test2.add(-5f);
        test2.add(-5f);
        test2.add(-5f);
        test2.add(-5f);

        if(enrz.informationEmise.equals(test2))
        {
            System.out.println("Bravo");
        }
    }
}