package sources;
import information.Information;

/**
 * 
 */
public class SourceFixe extends Source<Boolean>{
    /**
     * @param message .
     */
    public SourceFixe(String message)
    {
        informationGeneree = new Information<Boolean>();
        for(char character: message.toCharArray())
        {
            if(character == '0')
                informationGeneree.add(false);
            else
                informationGeneree.add(true);
        }
    }

}
