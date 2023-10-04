package ressources;

import information.Information;
import transmetteurs.LongueurDifferenteException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

/**
 * 
 */
public class Signal {

    /**
     * Fonction qui retourne un bruit blanc gaussien de variance et de taille données
     * @param variance : variance du bruit blanc gaussien (Puissance = variance)
     * @param taille : nombre d'échantillon total
     * @return bruit blang gaussien
     * @param random .
     */
    public static Information<Float> generationWhiteNoise(double variance, int taille, Random random){
        // Liste de valeurs du bruit blanc gaussien bbg
        Information<Float> bbg = new Information<Float>();
        for(int i = 0; i < taille; i++)
        {
            // Ajustement des valeurs de la normale centrée réduite avec écart type
        	//System.out.println("variance = "+variance);
        	//System.out.println("variance = "+variance);
            bbg.add((float) (random.nextGaussian()*Math.sqrt(variance)));
            //System.out.println(random.nextGaussian()*variance);
        }

        try {
            writeNoiseToCsv(bbg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(bbg);
        //System.out.println("puissance bbg = "+puissanceSignal(bbg));
        
        
        return bbg;
    }

    /**
     * @param bbg .
     * @throws IOException .
     */
    public static void writeNoiseToCsv(Information<Float> bbg) throws IOException {
        /*
    	FileWriter writer = new FileWriter("output/bruit_blanc.csv");
        Iterator<Float> valeursgauss = bbg.iterator();
        while(valeursgauss.hasNext())
        {
            writer.write(String.format("%.1f", valeursgauss.next()));
            writer.write(";\n");
        }
        writer.close();
        */
        
    }

    /**
     * Fonction qui calcule la puissance d'un signal à partir des échantilllons
     * @param signal : liste  des échantillons d'un signal sous la forme de Information Float
     * @return puissance
     */
    public static double puissanceSignal(Information<Float> signal){
        int somme = 0;
        //System.out.println(signal);
        for(float echantillon : signal)
        {
            somme += Math.pow(echantillon, 2);
        }
        //System.out.println("somme = "+somme);
        //System.out.println("nbelement = "+signal.nbElements());
        //System.out.println((double)somme/signal.nbElements());
        //return (double)somme/signal.nbElements();
        //System.out.println(signal.nbElements());
        //System.out.println("puissance = "+(double)somme/signal.nbElements());
        return (double)somme/signal.nbElements();
    }

    /**
     * Addition de deux signaux
     * @param signal1 : signal d'entré 1
     * @param signal2 : signal d'entré 1
     * @return addition des deux signal
     * @throws LongueurDifferenteException .
     */
    public static Information<Float> additionSignaux(Information<Float> signal1, Information<Float> signal2) throws LongueurDifferenteException{
        Information<Float> signalResultant = new Information<Float>();
        if(signal1.nbElements()!=signal2.nbElements()) throw new LongueurDifferenteException();
        // Itterator pour parcourir les échantillons
        Iterator<Float> iteratorSignal1 = signal1.iterator();
        Iterator<Float> iteratorSignal2 = signal2.iterator();
        while(iteratorSignal1.hasNext() && iteratorSignal2.hasNext()) {
            signalResultant.add((iteratorSignal1.next()+iteratorSignal2.next()));
        }
        return signalResultant;
    }



}
