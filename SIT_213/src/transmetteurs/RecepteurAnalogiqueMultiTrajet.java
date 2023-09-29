package transmetteurs;

import java.awt.ItemSelectable;
import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;
import simulateur.ArgumentsException;

public class RecepteurAnalogiqueMultiTrajet extends Transmetteur<Float, Float> {


	private ArrayList<Integer> tau;
	private ArrayList<Float> alpha;
	/** 
	 * Constructeur initialisant un récépteur multitrajet
	 * @param tau tableau des décalages
	 * @param alpha tableau des atténuations
	 * @throws RecepteurAnalogiqueMultiTrajetNonConforme
	 */
	public RecepteurAnalogiqueMultiTrajet(ArrayList<Integer> tau, ArrayList<Float> alpha) throws Exception {
			
		if(tau.size() < 1 || tau.size() > 5 || alpha.size() > 5 || alpha.size() < 1) {
			throw new ArgumentsException("On ne peut avoir qu'entre 1 et 5 multitrajet max");	
		}
		
		if(tau.size() != alpha.size()) {
			throw new ArgumentsException("On doit forcement avoir 1 tau associé à 1 alpha");
		}
		
		this.tau = tau;
		this.alpha = alpha;
		
		if(this.tauMin() < 1) {
			throw new ArgumentsException("Le décalage ne peux pas être négatif !");
		}
		
		if(this.alphaMax() > 1 || this.alphaMin() < 0) {
			throw new MissingFormatArgumentException("L'atténuation ne peut pas être négatif ou supérieure à 1");
		}
	}
	
	/**
	 * Méthode permettant de recevoir une information d'enlever du multi-trajet et de transmettre 
	 * @param information : L'information (de type double) recue
	 * @throws InformationNonConforme : L'information n'est pas conforme (exemple : information null)
	*/
	
	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConformeException {
		// TODO Auto-generated method stub 
		if(information == null || information.nbElements() == 0) {
			throw new InformationNonConformeException();

		}
		
		int nbEch = information.nbElements() - tauMax(); //Calcul du nombre d'échantillons utiles
		informationRecue = information;
		informationEmise = new Information <Float>();


		Information<Float> infoTemp = new Information<Float>();
		
		
		
		for(float val : informationRecue) {
			infoTemp.add(val);
		}
		
		for (int i = 0; i < nbEch; i++) //parcours des échantillons significatifs
		{
			float temp = infoTemp.iemeElement(i);
			for (int j = 0; j < tau.size(); j++)
			{
				int delta = tau.get(j);
				if (i>= delta)
				{
					temp -= alpha.get(j)*infoTemp.iemeElement(i-delta); //Si superposition de plusieurs trajets, on soustrait les multitrajets
					
				}
			}
			infoTemp.setIemeElement(i, temp); //Valeur "nettoyée" des multi trajets
		}
		
		
				for(int k = 0; k < nbEch; k++)
				{
					informationEmise.add(infoTemp.iemeElement(k)); // Construction de l'info émise
				}
				
				// On libère l'information temporaire (utile ou pas ?)
				//infoTemp.vider();
				
			}

	/**
     * Méthode permettant d'émettre l'information emise (modifier au préalable par la methode recevoir)
     * @throws InformationNonConforme : L'information n'est pas conforme
     *
    */
	
	@Override
	public void emettre() throws InformationNonConformeException {
		// TODO Auto-generated method stub

		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
			destinationConnectee.recevoir(informationEmise);
		}
	}

	
	public void recyclerRAM() {
		// TODO Auto-generated method stub
		//informationRecue.vider();
	}
	
	//Quelques methodes utiles pour connaitre les valeures min et max des listes 
	private int tauMax() {
		int max = tau.get(0);
		
		for(int val : tau) {
			if(val > max) {
				max = val;
			}
		}
		
		return max;
	}

	private int tauMin() {
		int min = tau.get(0);
		
		for(int val : tau) {
			if(val < min) {
				min = val;
			}
		}
		
		return min;
	}
	
	private float alphaMax() {
		float max = alpha.get(0);
		
		for(float val : alpha) {
			if(val > max) {
				max = val;
			}
		}
		
		return max;
	}
	
	private float alphaMin() {
		float min = alpha.get(0);
		
		for(float val : alpha) {
			if(val < min) {
				min = val;
			}
		}
		
		return min;
	}

}
