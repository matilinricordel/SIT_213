package transmetteurs;

import java.util.ArrayList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConformeException;

public class RecepteurMultiTrajet2 extends Transmetteur<Float, Boolean	> {
	
	//private float coefficientFiltre=1.f;
	protected float amplitudeMax=1.0f;	// Amplitude maximale du signal
	protected float amplitudeMin=0.0f;	// Amplitude minimale du signal
	protected String codage="RZ";			// Type de codage (par defaut : RZ)
	protected int nbEch=30;				// Nombres d'echantillons
	protected Information<Float> informationAConvertir; // Informations a  convertir (analogique)
	private Information<Float> informationFiltre;
	protected ArrayList<Float> instantDecisions;
	private Information<Float> informationBase;
	
    /**
     *  Constructeur personnalise de la classe Recepteur
     * @param amplitudeMax
     * @param amplitudeMin
     * @param codage
     * @param nbEch
     */
	public RecepteurMultiTrajet2(float amplitudeMax,float amplitudeMin,String codage,int nbEch) {
		this.amplitudeMax=amplitudeMax;
		this.amplitudeMin=amplitudeMin;
		this.codage=codage;
		this.nbEch=nbEch;
		informationAConvertir=new Information<Float>();
		informationFiltre=new Information<Float>();
	}
	

    /**
     *  Methode pour emettre l'information convertie
     */
    public void emettre() throws InformationNonConformeException {
        for (DestinationInterface<Boolean> destinationInterface : destinationsConnectees) {
            destinationInterface.recevoir(informationEmise);
        }
	}
    
	
    public void recevoirInformationBase(Information<Float> information) throws InformationNonConformeException{
    	this.informationBase=new Information<Float>();
    	for(Float f : information) {
    		informationBase.add(f);
    	}
    }

	@Override
	public void recevoir(Information<Float> information) throws InformationNonConformeException {
		this.informationRecue=new Information<Float>();
		this.informationAConvertir=new Information<Float>();
		instantDecisions=new ArrayList<Float>();
		//for(int i=0;i<information.nbElements();i++) {
		//	informationRecue.add(information.iemeElement(i));
		//}
		
		for(Float f:information) {
			informationRecue.add(f);
			informationAConvertir.add(f);
		}
		
		//this.informationAConvertir=informationRecue;
		//informationFiltre=filtrerSignal(informationAConvertir);
		//System.out.println(informationRecue);
		
		instantDecisions=creerFiltreAdapte();
		//System.out.println("Taille des maxima : "+instantDecisions.size());
		
		/*for (int i=0;i<instantDecisions.size();i++) {
			//instantDecisions.set(i, instantDecisions.get(i)+nbEch*i);
			System.out.println("Instant "+i+" : "+instantDecisions.get(i));
		}*/
		decision(instantDecisions);
		//convertionAnalogiqueNumerique();
		emettre();
	} 
	
	public ArrayList<Float> creerFiltreAdapte() {
		float[] filtreAdapte=new float[nbEch];
		float[] informationTemp=new float[nbEch];
		float max=-1;
		int temp=0;
		int i = 1;
		int k=0;
		//System.out.println("taille filtre : "+filtreAdapte.length);
		//System.out.println("Taille info : "+informationTemp.length);
		ArrayList<Integer> instantT=new ArrayList<Integer>();
		ArrayList<Float> instantMax=new ArrayList<Float>();
		
		/*switch(codage) {
		case "NRZ":
			for(int j=1;j<filtreAdapte.length+1;j++) {
				filtreAdapte[nbEch-j]=amplitudeMax;
			}
			break;
		case "NRZT":
			for(int j=1;j<filtreAdapte.length+1;j++) {
				if(j<(int)nbEch/3) {
					filtreAdapte[nbEch-j]= j * amplitudeMax / (nbEch / 3);
				}
				else if(j>(int)nbEch/3 && j!=(nbEch - 2 * ((int) (nbEch / 3)))) {
					filtreAdapte[nbEch-j]=amplitudeMax;
				}
				else {
					filtreAdapte[nbEch-j]=amplitudeMax - (j * amplitudeMax) / (nbEch / 3);
				}
				filtreAdapte[nbEch-j]=amplitudeMax;
				
			}
			break;
		case "RZ":
			for(int j=1;j<filtreAdapte.length;j++) {
				if(j<(int)nbEch/3) {
					filtreAdapte[nbEch-j]=0.0f;
				}
				else if(j>(int)nbEch/3 && j<(int)2*nbEch/3) {
					filtreAdapte[nbEch-j]=amplitudeMax;
				}
				else {
					filtreAdapte[nbEch-j]=0.0f;
				}
				filtreAdapte[nbEch-j]=amplitudeMax;
			}
			break;
		}*/
		
		for(int j =0;j<filtreAdapte.length-1;j++) {
			//System.out.println(filtreAdapte[j]);
		}
		
		
		for (Float f : informationAConvertir) {
			if (i < nbEch) {
				informationTemp[i]=f;
				filtreAdapte[nbEch-i]=amplitudeMax;
			} else {
	            // Effectuer le produit de convolution
	            float[] produitConv = produitConvolution(filtreAdapte, informationTemp);
	            
	            //System.out.println("Taille convolution :"+produitConv.length);
	            /*for(int j=0;j<produitConv.length;j++) {
	            	System.out.println(produitConv[j]);
	            }*/
	            
	            // Trouver l'instant T avec la valeur maximale
	            int m=(filtreAdapte.length+informationTemp.length)-1;
	            
	            for(int j=0;j<produitConv.length;j++) {
	            	//System.out.println(j);
	            	if(Math.abs(produitConv[j])>max) {
	            		//System.out.println(max);
	            		max=produitConv[j];
	            		temp=j;
	            		//System.out.println(temp);
	            	}
	            	//System.out.println(k);
	            	
	            	//System.out.println("out");
	            }
	            //System.out.println("Fin du for");
	            instantT.add(temp);
	            instantMax.add(max);
        		max=0;
        		temp=0;
	            
	            //instantT.add((float) instantMax);

	            // Rï¿½initialiser les compteurs et les informations temporaires
	            i = 0;
			}
			i++;
		}
		
		/*for(int h=0;h<instantMax.size();h++) {
			//System.out.println(instantT.get(h));
			//instantT.set(h, instantT.get(h)+h*nbEch);
			System.out.println("Instant max : "+instantMax.get(h));
		}*/
		//System.out.println("Taille instantT :"+instantT.size());
        return instantMax;
    }
	
	public static float[] produitConvolution(float[] filtreAdapte, float[] informationTemp) {
        int m = filtreAdapte.length;
        int n = informationTemp.length;
        int tailleResultat = m + n - 1;
        float[] resultat = new float[tailleResultat];

        for (int i = 0; i < tailleResultat; i++) {
            resultat[i] = 0;
            for (int j = 0; j < n; j++) {
                if (i - j >= 0 && i - j < m) {
                    resultat[i] += filtreAdapte[i - j] * informationTemp[j];
                }
            }
        }
        
        /*for(int h=0;h<resultat.length;h++) {
        	System.out.println(resultat[h]);
		}*/
        //System.out.println("Taille : "+resultat.length);
        
        return resultat;
    }
	
	public void decision(ArrayList<Float> instantsDecision) {
		informationEmise=new Information<Boolean>();
		//System.out.println(maxima.size());
		//System.out.println("Taille instants :"+instantsDecision.size());
		if(amplitudeMin!=0) {
		float seuil=0;
		
		if(codage=="RZ") {
			seuil=(amplitudeMax+amplitudeMin)/6;
		}
		else if(codage=="NRZ" || codage=="NRZT") {
			seuil=(amplitudeMax+amplitudeMin)/2;
		}
		
		for(Float f : instantsDecision) {
			if(f>=seuil) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
		}
		}
		else {
			float seuil=0;
			float somme=0;
			for(Float f : instantsDecision) {
				somme+=f;
			}
			seuil=somme/instantsDecision.size();
			
			for(Float f : instantsDecision) {
				if(f>=seuil) {
					informationEmise.add(true);
				}
				else {
					informationEmise.add(false);
				}
			}
		}
		
		
		/* seuilBruite=(amplitudeMax+amplitudeMin)/2;
		
		for(int i=0;i<instantsDecision.size()-1;i++) {
			//System.out.println(i);
			if(informationRecue.iemeElement(instantsDecision.get(i))>seuilBruite) {
				informationEmise.add(true);
			}
			else {
				informationEmise.add(false);
			}
		}
		if(informationRecue.iemeElement(instantsDecision.get(instantsDecision.size()-2))>seuilBruite) {
			informationEmise.add(true);
		}
		else {
			informationEmise.add(false);
		}
		
		/*if(instantsDecision.get(0)<instantsDecision.get(1)) {
			informationEmise.add(false);
		}
		else {
			informationEmise.add(true);
		}
		
		for(int i=1;i<instantsDecision.size();i++) {
			if(instantsDecision.get(i-1)>instantsDecision.get(i)) {
				informationEmise.add(false);
			}
			else{
				informationEmise.add(true);
			}
		}*/
			
		}
		
		
	}
