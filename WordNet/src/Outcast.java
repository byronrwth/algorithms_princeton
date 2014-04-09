import java.lang.*;
import java.util.*;

public class Outcast {
    private WordNet wordnet;
    
    private boolean test = false;
    
 // constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        // argument array contains at least two such nouns;
        int maxDistance = 0;
        String farest = null;
        
        for (int i = 0; i < nouns.length; i++) {
            
           int distance = 0;
           for (int j = 0; j < nouns.length; j++) {
               int temp = this.wordnet.distance(nouns[i], nouns[j]);
               distance += temp;
               if (test) StdOut.println(" temp: " + temp + " noun: " + nouns[j] );
               
           }
           if (test) StdOut.println(" distance: " + distance + " now: " + nouns[i]);
           
           if (maxDistance < distance) {
               maxDistance = distance;
               farest = nouns[i];
               if (test) StdOut.println(" maxDistance: " + maxDistance + " fastest: " + farest);
           }
        }
        return farest;
    }

    // for unit testing of this class (such as the one below):
    // e.g  bed out of : water soda bed orange_juice milk apple_juice tea coffee
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        
        //% java Outcast synsets.txt hypernyms.txt outcast5.txt outcast8.txt outcast11.txts
        for (int t = 2; t < args.length; t++) {
            //StdOut.println(" file: " + args[t] );
            String[] nouns = In.readStrings(args[t]);
            
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
