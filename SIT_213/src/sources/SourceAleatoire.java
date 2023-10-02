package sources;

import information.Information;

import java.util.Random;

/**
 * 
 */
public class SourceAleatoire extends Source<Boolean>{
    /**
     * @param nbElement .
     * @param seed .
     */
    public SourceAleatoire(int nbElement, int seed){
        Random random = new Random(seed);
        informationGeneration(nbElement, random);
    }

    /**
     * @param nbElement .
     */
    public SourceAleatoire(int nbElement){
        Random random = new Random();
        informationGeneration(nbElement, random);
    }

    /**
     * @param nbElements .
     * @param random .
     */
    private void informationGeneration(int nbElements, Random random)
    {
        informationGeneree = new Information<Boolean>();
        while(informationGeneree.nbElements() < nbElements)
        {
            informationGeneree.add(random.nextBoolean());
        }
    }

    /**
     * @param args .
     */
    public static void main(String args[]) {
        SourceAleatoire satest = new SourceAleatoire(10, 500);
    }
}
