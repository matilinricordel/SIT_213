package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

/**
 * 
 */
public abstract class Emetteur extends Transmetteur<Boolean, Float>{
	/*
	 * Instanciation des param�tres par d�faut
	 * 
	 * @param
	 * nbTechantillon - int qui correspond au nombre d'�chantillon par bit
	 * 
	 */
    /**
     * 
     */
    protected int nbTechantillon;

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
     /**
     *
     */
    @Override
    public void recevoir(Information<Boolean> information) throws InformationNonConformeException {
        this.informationRecue = information;
        conversion();
        emettre();
    }

    /* 
     * Emet l'information � la destination connect�e
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    /**
     *
     */
    @Override
    public void emettre() throws InformationNonConformeException {
        for(DestinationInterface <Float> destinationConnectee : destinationsConnectees)
        {
            destinationConnectee.recevoir(informationEmise);
        }
    }
    
    /* 
     * Classe abstraite
     * 
     * Convertit l'information re�ue
     * 
     * @exception 
     * InformationNonConformeException - V�rifie que l'information est conforme
     * 
     */
    /**
     * 
     */
    public abstract void conversion();
}
