package transmetteurs;

import information.Information;

import java.util.Iterator;
import java.util.Random;

import static ressources.Signal.*;

public class TransmetteurBruit extends TransmetteurAnalogique{
    protected int nbTechantillon;
    protected double rsb;
    protected Random random;


    public TransmetteurBruit(int nbTechantillon, double rsbEndB)
    {
        this.nbTechantillon = nbTechantillon;
        this.rsb = Math.pow(10, (rsbEndB/10));
        this.random = new Random();
    }

    public TransmetteurBruit(int nbTechantillon, double rsbEndB, int seed)
    {
        this.nbTechantillon = nbTechantillon;
        this.rsb = Math.pow(10, (rsbEndB/10));
        this.random = new Random(seed);
    }

    @Override
    public void traitementduSignal() {
        // Puissance = variance
        double variance;
        //System.out.println("rsb = "+rsb);
        //rsb = Math.pow(10, rsb/10);
        //System.out.println(puissanceSignal(informationRecue));
        variance = ((puissanceSignal(informationRecue)))*(1/rsb);
        //System.out.println("variance  = " + variance);
        Information<Float> bruit = new Information<Float>();
        bruit = generationWhiteNoise(variance, informationRecue.nbElements(), random);
        //System.out.println("puissance sig = "+ puissanceSignal(informationRecue));
        //System.out.println("puissance noise = "+puissanceSignal(bruit));
        //System.out.println("rsb = "+ puissanceSignal(informationRecue)/puissanceSignal(bruit));
        try {
        	//System.out.println("ajout de bruit" + bruit);
            informationTraite=additionSignaux(bruit, informationRecue);
        } catch (LongueurDifferenteException e) {
            System.out.println("Pas le même nombre d'échantillons");
            e.printStackTrace();
        }
    }
}