package simulateur;
import destinations.*;
import information.Information;
import sources.Source;
import sources.SourceAleatoire;
import sources.SourceFixe;
import transmetteurs.*;
import visualisations.Sonde;
import visualisations.SondeAnalogique;
import visualisations.SondeLogique;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


/** La classe Simulateur permet de construire et simuler une chaîne de
 * transmission composée d'une Source, d'un nombre variable de
 * Transmetteur(s) et d'une Destination..
 * @author cousin
 * @author prou
 */
public class Simulateur {
      	
	private Boolean rOne = true;
    /** indique si le Simulateur utilise des sondes d'affichage */
    private boolean affichage = false;

	/** indique si type de modulation numérique dans le simulateur */
	private String form="RZ";
    
    /** indique si le Simulateur utilise un message généré de manière aléatoire (message imposé sinon) */
    private boolean messageAleatoire = true;
    
    /** indique si le Simulateur utilise un germe pour initialiser les générateurs aléatoires */
    private boolean aleatoireAvecGerme = false;
    
    /** la valeur de la semence utilisée pour les générateurs aléatoires */
    private Integer seed = null; // pas de semence par défaut
    
    /** la longueur du message aléatoire à transmettre si un message n'est pas imposé */
    private int nbBitsMess = 100;
    /** la chaîne de caractères correspondant à m dans l'argument -mess m */
    private String messageString = "100";

	/** indique le nombre d'échantillons par symbole en analogique*/
	private int nombreEchantillon=30;

	/** indique le type de l'échange*/
	private String type="Logique";

    /** le  composant Source de la chaine de transmission */
    private Source <Boolean>  source = null;
    
    /** le  composant Transmetteur parfait logique de la chaine de transmission */
    private Transmetteur <Boolean, Boolean>  transmetteurLogique = null;

	/** le  composant Transmetteur parfait analogique de la chaine de transmission */
	private Transmetteur <Float, Float>  transmetteurAnalogique = null;

	/** amplitude max */
	private float vmax=1f;

	/** amplitude min */
	private float vmin=0f;

	/** indique niveau du SNR du transmetteur */
	private Float snrt= null;
    
    /** le  composant Destination de la chaine de transmission */
    private Destination <Boolean>  destination = null;
    
    /**
     * Composant qui gère les trajets multiples
     */
    private TrajetsMultiples trajetsMultiples = null;
    
    /**
     * 
     */
   	private Transmetteur recepteurMultiTrajet = null;
   	
   	/**
     * Indique si le simulateur doit utiliser le codeur ou non
     */
    private boolean codeurOn = false;//false
   
    /** Le constructeur de Simulateur construit une chaîne de
     * transmission composée d'une Source Boolean, d'une Destination
     * Boolean et de Transmetteur(s) [voir la méthode
     * analyseArguments]...  <br> Les différents composants de la
     * chaîne de transmission (Source, Transmetteur(s), Destination,
     * Sonde(s) de visualisation) sont créés et connectés.
     * @param args le tableau des différents arguments.
     *
     * @throws ArgumentsException si un des arguments est incorrect
     *
     */   
    public  Simulateur(String [] args) throws ArgumentsException {
    	/** Analyser et récupérer les arguments */
    	analyseArguments(args);

		/** Instanciation de la source 
		 * en fonction de l'argument -mess
		 * 
		 * */
		if(!messageAleatoire && messageString != null)
		{
			source = new SourceFixe(messageString);
		}
		else if (aleatoireAvecGerme){
			source = new SourceAleatoire(nbBitsMess, seed);
		}
		else {
			source = new SourceAleatoire(nbBitsMess);
		}

		
		/**
		 * Creation des recepteurs/emetteurs analogiques en fonction de l'argument -form
		 */
		if(type.equals("Analogique")&&form.equals("")) {form="RZ";}
		Transmetteur<Boolean, Float> Emetteur=null;
		Transmetteur<Float, Boolean> Recepteur=null;
		if(form.equals("NRZ")) {
			Emetteur = new EmetteurNRZ(vmax, vmin, nombreEchantillon);
			Recepteur = new RecepteurNRZ(nombreEchantillon, vmax, vmin);
		}
		else if(form.equals("RZ")) {
			Emetteur = new EmetteurRZ(vmax, nombreEchantillon);
			Recepteur = new RecepteurRZ(nombreEchantillon, vmax);

		}
		else if(form.equals("NRZT")) {
			Emetteur = new EmetteurNRZT(vmax, vmin, nombreEchantillon);
			Recepteur = new RecepteurRZ(nombreEchantillon, vmax, vmin);
		}

		// creation de la Destinaton
		destination = new DestinationFinale();

		// Instanciation transmetteurs ici transmetteur bruit si snrt
		if(type.equals("Analogique")) {
			transmetteurAnalogique= new TransmetteurParfait<Float,Float>();
			if(snrt != null) {
				if (aleatoireAvecGerme) {transmetteurAnalogique= new TransmetteurGaussien(snrt, nombreEchantillon,seed); /*new TransmetteurBruit(nombreEchantillon, snrt, seed);*/}
				else {transmetteurAnalogique= new TransmetteurGaussien(snrt, nombreEchantillon); /* new TransmetteurBruit(nombreEchantillon, snrt);*/}
			}
			else {transmetteurAnalogique= new TransmetteurParfait<Float,Float>();}
			transmetteurLogique=null;
		}

		else if(type.equals("Logique")) {
			transmetteurLogique = new TransmetteurParfait<Boolean,Boolean>();
			transmetteurAnalogique=null;
		}

		// Création chaine de sondes
		ArrayList<Object> chaineSondes = new ArrayList<Object>();

		if(affichage)
		{
			chaineSondes.add(new SondeLogique("source (Sonde logique)",10));
			if(codeurOn)chaineSondes.add(new SondeLogique(" codeur (Sonde logique)",10));
			chaineSondes.add(new SondeAnalogique("emetteur (Sonde analogique)"));
			chaineSondes.add(new SondeAnalogique("transmetteur bruité(Sonde analogique)"));
			
			if(trajetsMultiples!=null) {
				chaineSondes.add(new SondeAnalogique("transmetteur avec trajets multiple(Sonde analogique)"));
				if(!rOne)chaineSondes.add(new SondeAnalogique("Recepteur avec correction des trajets(Sonde analogique)"));
				
			}
			
			chaineSondes.add(new SondeLogique(" recepteur (Sonde logique)",10));
			if(codeurOn)chaineSondes.add(new SondeLogique(" decodeur (Sonde logique)",10));
			chaineSondes.add(new SondeLogique("destination (Sonde logique)",10));
			
		}
		

		// Créatio chaine de transmission
		ArrayList<Object> chaineTransmission = new ArrayList<Object>();

		// Liste des composants de la chaine de transmission
		chaineTransmission.add(source);
		if(codeurOn)chaineTransmission.add(new Codeur());
		chaineTransmission.add(Emetteur);
		if(type.equals("Logique")) chaineTransmission.add(transmetteurLogique);
		if(!type.equals("Logique"))chaineTransmission.add(transmetteurAnalogique);
		if(trajetsMultiples!=null) {
			chaineTransmission.add(trajetsMultiples);
			chaineTransmission.add(recepteurMultiTrajet);
		}
		/*if(trajetsMultiples==null)*/
			if(!rOne)chaineTransmission.add(Recepteur);
		
		if(codeurOn)chaineTransmission.add(new DecodeurNG());
		chaineTransmission.add(destination);
		//System.out.println(chaineTransmission);
		

		// Connexion des composants de la chaine de transmission
		try{
			for(int i=0;i<chaineTransmission.size();i++) {
				Sonde sonde = null;
				if(affichage&&chaineSondes.get(i)!=null) {
					switch(chaineSondes.get(i).getClass().toString()) {
						case"class visualisations.SondeLogique":
							sonde = (SondeLogique) chaineSondes.get(i);
							break;
						case"class visualisations.SondeAnalogique":
							sonde = (SondeAnalogique) chaineSondes.get(i);
							break;
					}
				}
				//System.out.println(chaineTransmission.get(i).getClass().getPackageName());
				switch(chaineTransmission.get(i).getClass().getPackageName()) {
					case"sources":
						Source src1 = (Source) chaineTransmission.get(i);
						switch(chaineTransmission.get(i+1).getClass().getPackageName()) {
							case"transmetteurs":
								Transmetteur trs2 = (Transmetteur) chaineTransmission.get(i+1);
								src1.connecter(trs2);
								if(sonde!=null) src1.connecter(sonde);
								//System.out.println(src1.getClass()+" - Connect�e � -"+sonde.getClass());
								break;
							case"destinations":
								Destination dst2 = (Destination) chaineTransmission.get(i+1);
								src1.connecter(dst2);
								if(sonde!=null) src1.connecter(sonde);
								//System.out.println(src1.getClass()+" - Connect�e � -"+sonde.getClass());
								break;
						}
						break;
					case"transmetteurs":
						//System.out.println("connexion ");
						Transmetteur trs1 = (Transmetteur) chaineTransmission.get(i);
						switch(chaineTransmission.get(i+1).getClass().getPackageName()) {
							case"transmetteurs":
								Transmetteur trs2 = (Transmetteur) chaineTransmission.get(i+1);
								trs1.connecter(trs2);
								if(sonde!=null) {
									trs1.connecter(sonde);
									//System.out.println(trs1.getClass()+" - Connect�e � -"+sonde.getClass());
								}
								break;
							case"destinations":
								Destination dst2 = (Destination) chaineTransmission.get(i+1);
								trs1.connecter(dst2);
								if(sonde!=null) {
									trs1.connecter(sonde);
									//System.out.println(trs1.getClass()+" - Connect�e � -"+sonde.getClass());
								}
								break;
						}
						break;
				}
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}

	}

   
   
    /** La méthode analyseArguments extrait d'un tableau de chaînes de
     * caractères les différentes options de la simulation.  <br>Elle met
     * à jour les attributs correspondants du Simulateur.
     *
     * @param args le tableau des différents arguments.
     * <br>
     * <br>Les arguments autorisés sont : 
     * <br> 
     * <dl>
     * <dt> -mess m  </dt><dd> m (String) constitué de 7 ou plus digits à 0 | 1, le message à transmettre</dd>
     * <dt> -mess m  </dt><dd> m (int) constitué de 1 à 6 digits, le nombre de bits du message "aléatoire" à transmettre</dd> 
     * <dt> -s </dt><dd> pour demander l'utilisation des sondes d'affichage</dd>
     * <dt> -seed v </dt><dd> v (int) d'initialisation pour les générateurs aléatoires</dd> 
     * </dl>
     *
     * @throws ArgumentsException si un des arguments est incorrect.
     *
     */   
    public  void analyseArguments(String[] args)  throws  ArgumentsException {

    	for (int i=0;i<args.length;i++){ // traiter les arguments 1 par 1

    		if (args[i].matches("-s")){
    			affichage = true;
    		}
    		
    		else if (args[i].matches("-seed")) {
    			aleatoireAvecGerme = true;
    			i++; 
    			// traiter la valeur associee
    			try { 
    				seed = Integer.valueOf(args[i]);
    			}
    			catch (Exception e) {
    				throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
    			}           		
    		}

			else if (args[i].equals("-ampl")) {
				i++;
				// traiter la valeur associee
				try {
					vmin = Float.valueOf(args[i++]);
					vmax = Float.valueOf(args[i]);
					type="Analogique";
					if(vmax<vmin) {throw new Exception();}
				}
				catch (Exception e) {
					throw new ArgumentsException("Valeur du parametre -ampl  invalide :" +args[i-1] +" "+ args[i]+ "   Vérifier la relation "+args[i-1] +"<"+ args[i]);
				}
			}

    		else if (args[i].matches("-mess")){
    			i++; 
    			// traiter la valeur associee
    			messageString = args[i];
    			if (args[i].matches("[0,1]{7,}")) { // au moins 7 digits
    				messageAleatoire = false;
    				nbBitsMess = args[i].length();
    			} 
    			else if (args[i].matches("[0-9]{1,6}")) { // de 1 à 6 chiffres
    				messageAleatoire = true;
    				nbBitsMess = Integer.valueOf(args[i]);
    				if (nbBitsMess < 1) 
    					throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
    			}
    			else 
    				throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
    		}

			else if (args[i].equals("-form")) {
				i++;
				try {
					form = String.valueOf(args[i]);
					type="Analogique";
					if((!form.equals("NRZ"))&&(!form.equals("NRZT"))&&(!form.equals("RZ"))) {throw new Exception();}
				}
				catch (Exception e) {
					throw new ArgumentsException("Valeur du parametre -form  invalide : " + form);
				}
			}

			else if (args[i].equals("-snrpb")) {
				i++;
				type="Analogique";
				snrt=Float.valueOf(args[i]);
			}

			else if (args[i].equals("-nbEch")) {
				i++;
				try {
					nombreEchantillon=Integer.valueOf(args[i]);
					type="Analogique";
					if((nombreEchantillon<1)) {throw new Exception();}
				}
				catch (Exception e) {
					throw new ArgumentsException("Valeur du parametre -nbEch  invalide :" + nombreEchantillon +", Doit être supérieur à 1.");
				}

			}
			else if (args[i].matches("-rOne")) {
				rOne = true;
			}
    		
			else if (args[i].matches("-ti")) {
                if ((args.length - i) < 2) {
                    throw new ArgumentsException("Les trajets multiples doivent contenir un décalage temporel et une amplitude relative");
                }

                trajetsMultiples = new TrajetsMultiples();
                
                ArrayList<Integer> tabdt = new ArrayList<Integer>();
                ArrayList<Float> tabar = new ArrayList<Float>();
                
                for (int j = i + 1; j < args.length; j++) {
                    if (args.length - j + 1 < 1) {
                        throw new ArgumentsException("Les trajets multiples doivent contenir un décalage temporel et une amplitude relative");
                    } else if (args[j].startsWith("-")) {
                        i = j - 1;
                        break;
                    }

                    if (trajetsMultiples.nbTrajets() >= 5)
                        throw new ArgumentsException("Vous ne pouvez pas ajouter plus de 5 trajets multiples");

                    int dt = Integer.valueOf(args[j]);
                    float ar = Float.valueOf(args[++j]);
                    
                    tabdt.add(dt);
                    tabar.add(ar);

                    if (dt < 0)
                        throw new ArgumentsException("Le décalage temporel ne peut pas être négatif");

                    if (ar < 0 || ar > 1)
                        throw new ArgumentsException("L'amplitude relative ne peut être inférieur à 0 ou supérieur à 1.");

                    trajetsMultiples.addTrajetMultiple(dt, ar);
                    //System.out.println(tabar);
                    //System.out.println(tabdt);
                    i = j;
                }
                
                try {
                	//System.out.println("recepteur mutli ok");
					recepteurMultiTrajet = new RecepteurAnalogiqueMultiTrajet(tabdt, tabar);
					if(rOne)recepteurMultiTrajet = new RecepteurMultiTrajet2(vmax, vmin, type , nombreEchantillon);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
    		
    		
			else if (args[i].matches("-codeur")) {
                //i++;
                codeurOn = true;

            }
    		
    		//TODO : ajouter ci-après le traitement des nouvelles options

    		else throw new ArgumentsException("Option invalide :"+ args[i]);
    	}
      
    }
     
    
   	
    /** La méthode execute effectue un envoi de message par la source
     * de la chaîne de transmission du Simulateur.
     *
     * @throws Exception si un problème survient lors de l'exécution
     *
     */ 
    public void execute() throws Exception {      
         source.emettre();
    }
   
   	   	
   	
    /** La méthode qui calcule le taux d'erreur binaire en comparant
     * les bits du message émis avec ceux du message reçu.
     *
     * @return  La valeur du Taux dErreur Binaire.
     */   	   
    public float  calculTauxErreurBinaire() {
    	
    	//System.out.println("calcul TEB");
		Information<Boolean> emission = source.getInformationEmise();
		Information<Boolean> reception = destination.getInformationRecue();
		//System.out.println(source.getInformationEmise());
		//System.out.println(destination.getInformationRecue());
		if(emission == reception)
			return 0.0f;
		Iterator<Boolean> se = emission.iterator();
		Iterator<Boolean> sr = reception.iterator();
		int biterronne = 0;
		while(se.hasNext() && sr.hasNext())
		{
			if(se.next() != sr.next())
			{
				biterronne++;
			}
		}
		return  (float) biterronne/nbBitsMess;
		
    	//return 0.0f;
    }
   
   
   
    /** La fonction main instancie un Simulateur à l'aide des
     *  arguments paramètres et affiche le résultat de l'exécution
     *  d'une transmission.
     *  @param args les différents arguments qui serviront à l'instanciation du Simulateur.
     * @throws IOException .
     */
    public static void main(String [] args) throws IOException {

		Simulateur simulateur = null;
		

		try {
				simulateur = new Simulateur(args);
			} catch (Exception e) {
				System.out.println(e);
				System.exit(-1);
			}

			try {
				simulateur.execute();
				String s = "java  Simulateur  ";
				for (int i = 0; i < args.length; i++) { //copier tous les paramètres de simulation
					s += args[i] + "  ";
				}

				System.out.println(s + "  =>   TEB : " + simulateur.calculTauxErreurBinaire());
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
				System.exit(-2);
			}
		}
}

