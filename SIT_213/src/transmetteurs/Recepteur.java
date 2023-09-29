package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

import java.util.Iterator;

public abstract class Recepteur extends Transmetteur<Float, Boolean>{
	/*
	 * Instanciation des param�tres par d�faut
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * signalDemodule - information qui contient des bool�ens et qui correspond au signal d�modul�
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * vmin - float qui correspond � la tension minimal du signal analogique
	 * 
	 */
    protected int nbTechantillon;
    protected Information<Boolean> signalDemodule;
    protected float vmax;
    protected float vmin;

    /* 
     * Constructeur g�n�rique d'un r�cepteur
     * 
     * @param
     * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * vmin - float qui correspond � la tension minimal du signal analogique
     * 
     */
    public Recepteur(int nbTechantillon, float vmax, float vmin) {
        this.nbTechantillon = nbTechantillon;
        this.vmax = vmax;
        this.vmin = vmin;
    }

    /* 
     * Surcharge d'un constructeur g�n�rique d'un r�cepteur
     * 
     * @param
     * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * vmax - float qui correspond � la tension maximal du signal analogique
	 * vmin - float qui correspond � la tension minimal du signal analogique 
	 * 		  �gale � 0 par d�faut
     * 
     */
    public Recepteur(int nbTechantillon, float vmax) {
        this.nbTechantillon = nbTechantillon;
        this.vmax = vmax;
        this.vmin = 0f;
    }

    /* 
     * La m�thode pour r�cup�rer la variable nbTechantillon
     * 
     * @return 
     * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
     * 
     */
    public int getNbTechantillon() {
        return nbTechantillon;
    }

    /* 
     * La m�thode pour isntancier la variable nbTechantillon
     * 
     * @param 
     * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
     * 
     */
    public void setNbTechantillon(int nbTechantillon) {
        this.nbTechantillon = nbTechantillon;
    }

    /* 
     * Initialise l'information re�ue, la d�convertit et l'�met
     * 
     * @param
     * information - information � d�convertir qui contient des bool�ens
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    @Override
    public void recevoir(Information<Float> information) throws InformationNonConformeException {
        informationRecue=information;
        deconversion();
        emettre();
    }

    /* 
     * Emet le signal d�modul�s � la destination connect�e
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(signalDemodule);
        }
        // Instancie l'information �mise avec la valeur du signal d�modul�
        informationEmise=signalDemodule;
    }

    /* 
     * M�thode abstraite d�conversion() pour d�convertir un signal NRZ, NRZT et RZ
     */
    public abstract void deconversion();

    
    public static <T> Information<Information<Float>> partition(Information<Float> information, int nbTechantillon)
    {
    	//System.out.println("nbech  "+nbTechantillon);
        /* Regroupement des �chantillons en partitions repr�sentant les bits
        Partition contenant les bits
        */
        Information<Float> subpartition = new Information <Float>();
        // Bits avec n �chantillons
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
