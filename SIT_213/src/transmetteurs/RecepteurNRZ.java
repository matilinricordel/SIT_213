package transmetteurs;

import information.Information;
import information.InformationNonConformeException;

import java.util.Iterator;

/**
 * 
 */
public class RecepteurNRZ extends Recepteur{
	/*
	 * Constructeur du récepteur NRZ ou NRZT
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * vmax - float qui correspond à la tension maximal du signal analogique
	 * vmin - float qui correspond à la tension minimal du signal analogique
	 * 
	 */
    /**
     * @param nbTechantillon .
     * @param vmax .
     * @param vmin .
     */
    public RecepteurNRZ(int nbTechantillon, float vmax, float vmin) {
        super(nbTechantillon, vmax, vmin);
    }
    
    /* 
     * Déconvertit l'information reçue en signal numérique NRZ ou NRZT
     * 
     * @exception 
     * InformationNonConformeException - Vérifie que l'information est conforme
     * 
     */
    /**
     *
     */
    public void deconversion() {
    	/* Déclaration et instanciation du seuil.
    	*  Le seuil permet de d�cider si un ensemble d'�chantillon repr�sente un "True" ou un "False"
    	*/
        float seuil =(vmin+vmax)/2;
        // Instanciation d'une information de bool�ens pour r�cup�rer le signal d�modul�
        signalDemodule = new Information <Boolean>();
        // Calcul du nombre de bit pr�sent dans l'information par bit
        //int nbBits = informationRecue.nbElements()/nbTechantillon;
        int nbBits = nbTechantillon;
        //System.out.println("nbbit = "+ nbBits);
        // Partitionnement de l'information re�ue
        Information<Information<Float>> partitions = partition(informationRecue, nbTechantillon);
        //System.out.println("partition = "+partitions);
        Iterator<Information<Float>> partition = partitions.iterator();
        // V�rifie qu'il y a des partitions 
        while (partition.hasNext()){
            // R�cup�ration de tous les �chantillons de chaque partition
            Iterator<Float> echantillon = partition.next().iterator();
            // D�finition et instanciation de la somme des amplitudes des �chantillons de la partition
            float sommeAmplitude = 0f;
            // V�rifie qu'il y a des �chantillons dans la partition courante 
            while(echantillon.hasNext())
            {
                sommeAmplitude+=echantillon.next();
            }
            //System.out.println("sommeAmpl = " + sommeAmplitude);
            /* Si la moyenne de tous les �chantillons dans une partition est > ou < au seuil de d�tection,
            *  on ajoute true or false
            */
            if(sommeAmplitude/nbBits>seuil) {
                signalDemodule.add(true);
            }else{
                signalDemodule.add(false);
            }
        }
    }

    /**
     * @param args .
     * @throws InformationNonConformeException .
     */
    public static void main(String[] args) throws InformationNonConformeException {
        Information<Float> test = new Information<Float>();
        test.add(5f);
        test.add(5f);
        test.add(-5f);
        test.add(-5f);
        test.add(-5f);
        test.add(-5f);
        test.add(5f);
        test.add(5f);
        RecepteurNRZ rnrzt = new RecepteurNRZ(2, 5, -5);
        rnrzt.recevoir(test);
    }
}


