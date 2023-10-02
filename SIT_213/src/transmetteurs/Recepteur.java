package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

import java.util.Iterator;

/**
 * 
 */
public abstract class Recepteur extends Transmetteur<Float, Boolean>{
	/*
	 * Instanciation des parametres par defaut
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'echantillon par bit
	 * signalDemodule - information qui contient des booleens et qui correspond au signal demodule
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * vmin - float qui correspond e la tension minimal du signal analogique
	 * 
	 */
    /**
     * 
     */
    protected int nbTechantillon;
    /**
     * 
     */
    protected Information<Boolean> signalDemodule;
    /**
     * 
     */
    protected float vmax;
    /**
     * 
     */
    protected float vmin;

    /* 
     * Constructeur generique d'un recepteur
     * 
     * @param
     * nbTechantillon - int qui correspond au nombre d'echantillon par bit
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * vmin - float qui correspond e la tension minimal du signal analogique
     * 
     */
    /**
     * @param nbTechantillon .
     * @param vmax .
     * @param vmin .
     */
    public Recepteur(int nbTechantillon, float vmax, float vmin) {
        this.nbTechantillon = nbTechantillon;
        this.vmax = vmax;
        this.vmin = vmin;
    }

    /* 
     * Surcharge d'un constructeur generique d'un recepteur
     * 
     * @param
     * nbTechantillon - int qui correspond au nombre d'echantillon par bit
	 * vmax - float qui correspond e la tension maximal du signal analogique
	 * vmin - float qui correspond e la tension minimal du signal analogique 
	 * 		  egale e 0 par defaut
     * 
     */
    /**
     * @param nbTechantillon .
     * @param vmax .
     */
    public Recepteur(int nbTechantillon, float vmax) {
        this.nbTechantillon = nbTechantillon;
        this.vmax = vmax;
        this.vmin = 0f;
    }

    /* 
     * La methode pour recuperer la variable nbTechantillon
     * 
     * @return 
     * nbTechantillon - int qui correspond au nombre d'echantillon par bit
     * 
     */
    /**
     * @return .
     */
    public int getNbTechantillon() {
        return nbTechantillon;
    }

    /* 
     * La methode pour isntancier la variable nbTechantillon
     * 
     * @param 
     * nbTechantillon - int qui correspond au nombre d'echantillon par bit
     * 
     */
    /**
     * @param nbTechantillon .
     */
    public void setNbTechantillon(int nbTechantillon) {
        this.nbTechantillon = nbTechantillon;
    }

    /* 
     * Initialise l'information reeue, la deconvertit et l'emet
     * 
     * @param
     * information - information e deconvertir qui contient des booleens
     * 
     * @exception 
     * InformationNonConformeException - Verifie que l'information est conforme
     * 
     */
    /**
     *
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        informationRecue=information;
        deconversion();
        emettre();
    }

    /* 
     * Emet le signal demodules e la destination connectee
     * 
     * @exception 
     * InformationNonConformeException - Verifie que l'information est conforme
     * 
     */
    /**
     *
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(signalDemodule);
        }
        // Instancie l'information emise avec la valeur du signal demodule
        informationEmise=signalDemodule;
    }

    /* 
     * Methode abstraite deconversion() pour deconvertir un signal NRZ, NRZT et RZ
     */
    /**
     * 
     */
    public abstract void deconversion();

    
    /**
     * @param <T> .
     * @param information .
     * @param nbTechantillon .
     * @return .
     */
    public static <T> Information<Information<Float>> partition(Information<Float> information, int nbTechantillon)
    {
    	//System.out.println("nbech  "+nbTechantillon);
        /* Regroupement des echantillons en partitions representant les bits
        Partition contenant les bits
        */
        Information<Float> subpartition = new Information <Float>();
        // Bits avec n echantillons
        Information<Information<Float>> partitions = new Information<Information<Float>>();
        //System.out.println("nbElement total = "+ information.nbElements());
        for(int i = 0; i<information.nbElements(); i++)
        {
        	
            if(i!=0 && i%nbTechantillon == 0){
            	//System.out.print("NOUVEAU BIT ");
            	//System.out.println(subpartition);
                partitions.add(subpartition);
                subpartition = new Information<Float>();
            };
            //System.out.println("ech NUM "+i);
        	subpartition.add(information.iemeElement(i));
            
        }
        partitions.add(subpartition);
        return partitions;
    }
}
