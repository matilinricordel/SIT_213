package sources;

import information.Information;

import java.util.Random;

public class SourceAleatoire extends Source<Boolean>{
    public SourceAleatoire(int nbElement, int seed){
        Random random = new Random(seed);
        informationGeneration(nbElement, random);
    }

    public SourceAleatoire(int nbElement){
        Random random = new Random();
        informationGeneration(nbElement, random);
    }

    private void informationGeneration(int nbElements, Random random)
    {
        informationGeneree = new Information<Boolean>();
        while(informationGeneree.nbElements() < nbElements)
        {
            informationGeneree.add(random.nextBoolean());
        }
    }

    public static void main(String args[]) {
        SourceAleatoire satest = new SourceAleatoire(10, 500);
    }
}
