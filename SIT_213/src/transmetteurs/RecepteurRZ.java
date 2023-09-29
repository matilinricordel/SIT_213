package transmetteurs;


import information.Information;
import information.InformationNonConformeException;

import java.util.Iterator;

public class RecepteurRZ extends Recepteur{

    public RecepteurRZ(int nbTechantillon, float vmax) {
        super(nbTechantillon, vmax);
    }
    
    public RecepteurRZ(int nbTechantillon, float vmax, float vmin) {
        super(nbTechantillon, vmax);
        this.vmin = vmin;
    }
    
 // Déconversion RZ
    public void deconversion() {
    	/*System.out.println("nbech =" +nbTechantillon);
    	System.out.println("nbech/3 =" +nbTechantillon/3);
    	System.out.println("2nbech/3 =" + (2*nbTechantillon/3 - nbTechantillon/3));
    	System.out.println("3nbech/3 =" +3*nbTechantillon/3);
    	*/
        float seuil =(vmin+vmax)/2;
        signalDemodule = new Information<Boolean>();
        
        // Partitionne le signal reçu par bit
        Information<Information<Float>> partitions = partition(informationRecue, nbTechantillon);
        Iterator<Information<Float>> partition = partitions.iterator();
        while (partition.hasNext()){
            // Récupération de tous les échantillons de chaque partition
            Iterator<Float> echantillon = partition.next().iterator();
            
            // Calcule la somme des valeurs des �chantillons sur la p�riode [T/3; 2T/3]
            float somme = 0f;
            //System.out.println("nbech/3 = "+nbTechantillon/3);
            //System.out.println("2nbech/3 = "+2*(nbTechantillon/3));
            for(int i = (nbTechantillon/3); i < nbTechantillon - (nbTechantillon/3); i++) {
	            while(echantillon.hasNext()) {
	            	somme += echantillon.next();
	            }
            }
           //System.out.println(echantillon.toString());
            // Si la moyenne de tous les échantillons dans une partition est > ou < au seuil de détection on ajoute true or false
            float moyenne = somme/(nbTechantillon/3);
            if(moyenne>seuil) {
                signalDemodule.add(true);
            }else{
                signalDemodule.add(false);
            }
        }
    }

    public static void main(String[] args) throws InformationNonConformeException {
        Information<Float> test = new Information<Float>();
        test.add(0f);
        test.add(5f);
        test.add(0f);
        test.add(0f);
        test.add(0f);
        test.add(0f);
        test.add(0f);
        test.add(0f);
        test.add(0f);
        RecepteurRZ rrz = new RecepteurRZ(1, 5);
        rrz.recevoir(test);
    }
}