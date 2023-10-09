package transmetteurs;

import information.Information;

import java.util.*;
import java.util.Iterator;
import java.util.Random;

import static ressources.Signal.*;

/**
 * 
 */
public class TransmetteurBruit extends TransmetteurAnalogique{
    /**
     * 
     */
    protected int nbTechantillon;
    /**
     * 
     */
    protected double rsb;
    /**
     * 
     */
    protected Random random;
    
    
    protected float snrReel;
    
    protected double variance;

    protected float puissanceBruitMoyen;
    
    protected float puissanceMoyenneSignal;
    
    protected LinkedList<Float> bruitEmis = new LinkedList<Float>();
    /**
     * @param nbTechantillon .
     * @param rsbEndB .
     */
    public TransmetteurBruit(int nbTechantillon, double rsbEndB)
    {
        this.nbTechantillon = nbTechantillon;
        this.rsb = rsbEndB; //Math.pow(10, (rsbEndB/10));
        this.random = new Random();
    }

    /**
     * @param nbTechantillon .
     * @param rsbEndB .
     * @param seed .
     */
    public TransmetteurBruit(int nbTechantillon, double rsbEndB, int seed)
    {
        this.nbTechantillon = nbTechantillon;
        //this.rsb = Math.pow(10, (rsbEndB/10));
        this.rsb = rsbEndB;
        this.random = new Random(seed);
        
    }
    
    private void calculerPuissanceDeBruitMoyen() {
    	float somme = 0;
    	for (float i : this.bruitEmis)
    		somme+= Math.pow(i, 2);
    	this.puissanceBruitMoyen = (float) somme / (float) this.bruitEmis.size();
    }
    private void calculerPuissanceMoyenneSignal() {
        float somme = 0;
        for (float i : this.informationRecue)
            somme += Math.pow(i, 2);
        this.puissanceMoyenneSignal = (float) somme / this.informationRecue.nbElements();
    }
    public void calculerSNRreel() {
    	
    	this.snrReel = 10 * (float) Math.log10(
    			(this.puissanceMoyenneSignal * nbTechantillon) / (2*this.puissanceBruitMoyen));
    			
    	
    }
    private void calculerVariance() {
        calculerPuissanceMoyenneSignal();
        this.variance = (this.puissanceMoyenneSignal * nbTechantillon) / (2 * (float) Math.pow(10, rsb / 10));
    }
    

    /**
     *
     */
    @Override
    public void traitementduSignal() {
        // Puissance = variance
        
        //System.out.println("rsb = "+rsb);
        //rsb = Math.pow(10, rsb/10);
        //System.out.println(puissanceSignal(informationRecue));
        // EX // variance = ((puissanceSignal(informationRecue)))*(1/rsb);
        //variance = ((puissanceSignal(informationRecue)))/Math.pow(10, rsb/10);
        calculerVariance();
        calculerPuissanceDeBruitMoyen();
        System.out.println("rsb = "+rsb);
        //System.out.println("puissance signal = "+puissanceSignal(informationRecue));
        variance = ((puissanceSignal(informationRecue)))/Math.pow(10, rsb/10);
        //System.out.println("rsb = "+rsb);
        //System.out.println("10^rsb/10 = "+Math.pow(10, rsb/10));
        //System.out.println("variance = "+variance);
        //System.out.println("variance  = " + variance);
        Information<Float> bruit = new Information<Float>();
        bruit = generationWhiteNoise(variance, informationRecue.nbElements(), random);
        System.out.println("puissance sig = "+ puissanceSignal(informationRecue));
        System.out.println("puissance noise = "+puissanceSignal(bruit));
        System.out.println("rsb = "+ puissanceSignal(informationRecue)/puissanceSignal(bruit));
        
        System.out.println("SNR = "+rsb+ "SNR reel : "+Math.pow(10, snrReel/10));
        try {
        	//System.out.println("ajout de bruit" + bruit);
            informationTraite=additionSignaux(bruit, informationRecue);
        } catch (LongueurDifferenteException e) {
            System.out.println("Pas le même nombre d'échantillons");
            e.printStackTrace();
        }
    }
}