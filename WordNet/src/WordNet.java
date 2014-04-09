import java.lang.*;
import java.util.*;

public class WordNet {
    private int V;
    private int E;
    private Digraph G;
    private SAP sap;
//    private String ancestor = null;
    private Bag<Integer>[] adj;
    
    private boolean test = false;
    
//    private SET<String> wordNetNounSet;
//    private HashMap<String, Integer> synsetsTable;
//    private HashMap<String, Bag<Integer>[] > hash; 
    
    private LinearProbingHashST<String, Integer> linearProbingHashST;
    
    private RedBlackBST<String, Integer> wordNetNounSet;

    private HashMap<Integer, String> ancestor;
    
 // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        // space use in linear with synsets and hypernyms;
        // time linearithmic (better) in input size;

        In inSynsets = new In(synsets);
        In inHypernyms = new In(hypernyms);
        
        this.ancestor = new HashMap<Integer, String>();
        
        this.linearProbingHashST = new LinearProbingHashST();
        
        this.wordNetNounSet = new RedBlackBST<String, Integer>();

        
        int vertice = 0;
        while (inSynsets.hasNextLine()) {
            String[] arr = inSynsets.readLine().split(",");            
            int id = Integer.parseInt(arr[0]);
            String key = arr[1];
            this.ancestor.put(id, key);
            
            if (test) StdOut.printf("id is "+ id + " , key is " + key + " \n");
            if (test) StdOut.printf(" ancestor get: " + this.ancestor.get(id) + "\n");
            
            String[] nouns = key.split(" ");
            for(int k=0; k < nouns.length; k++  ){
                this.linearProbingHashST.put(nouns[k], id);
                
                if (test) StdOut.printf(" nouns: " + nouns[k] + " id: " + id + "\n");
                if (test) StdOut.printf(" linearProbingHashST get id: " + this.linearProbingHashST.get(nouns[k]) + "\n" );
                
                if (!this.wordNetNounSet.contains(nouns[k])) {
                    this.wordNetNounSet.put(nouns[k],id);
                }
                if (test) StdOut.printf("test wordNetNounSet get: " + this.wordNetNounSet.keys() + "\n" );
            }
            vertice++;
        }
        this.V = vertice;
        this.G = new Digraph(this.V);
        this.sap = new SAP(this.G);
        this.adj = (Bag<Integer>[]) new Bag[V];


        while (inHypernyms.hasNextLine()) {
          String[] iDs = inHypernyms.readLine().split(",");
          int v = Integer.parseInt(iDs[0]);
          for (int i = 1; i < iDs.length ; i++) {    
              int w = Integer.parseInt(iDs[i]);
              this.adj[v] = new Bag<Integer>();
              this.G.addEdge(v, w);
              this.adj[v].add(w);
//              StdOut.print( v + "-" + w + "\n" );
          }
        }
  
//        StdOut.println(this.G);  
          
        //rooted DAG: it is acylic 
        //            and has one vertex that is an ancestor of every other vertex. 
        //            However, it is not necessarily a tree because a synset can have more than one hypernym
        // if not rooted DAG, throw java.lang.IllegalArgumentException

        int root, number = 0;
        for ( int x = 0; x < this.V; x++) { 
            if (this.adj[x] == null){
                root = x;
//                StdOut.println("root is " + x); 
                number++;
                if (number > 1) throw new java.lang.IllegalArgumentException( x + ": more than 1 root ! \n " );
            }
            //cycle
            else {
                for(int j: this.G.adj(x)){
                    if (j == x) throw new java.lang.IllegalArgumentException( x + ": cycle ! \n " );
                }
            }
        } 

    }
    
    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns(){
        return this.wordNetNounSet.keys();

    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        //run in time logarithmic (or better) in the number of nouns;
        return this.wordNetNounSet.contains(word);
        
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        //run in time linear in the size of the WordNet digraph;
//        if ( /* not both A,B are WordNEt nouns */) throw java.lang.IllegalArgumentException
        if (this.linearProbingHashST.contains(nounA) && this.linearProbingHashST.contains(nounB) ) {
            int length = -1;
//            SAP sap = new SAP(this.G);
            length = this.sap.length(this.linearProbingHashST.get(nounA), this.linearProbingHashST.get(nounB));
            return length;
        }
        else 
            throw new java.lang.IllegalArgumentException(" no such words: " + nounA + " , " + nounB + "\n");
            
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        //run in time linear in the size of the WordNet digraph;
        
//        if ( /* not both A,B are WordNEt nouns*/) throw java.lang.IllegalArgumentException
        if (this.linearProbingHashST.contains(nounA) && this.linearProbingHashST.contains(nounB) ) {
            int root = -1;
//            SAP sap = new SAP(this.G);
            root = this.sap.ancestor(this.linearProbingHashST.get(nounA), this.linearProbingHashST.get(nounB));
            if (root > -1) {
                return this.ancestor.get(root);
            }
            else 
                return null;
        }
        else
            return null;
    }

    // for unit testing of this class
    public static void main(String[] args){
          WordNet wordnet = new WordNet(args[0], args[1]);
          
//          StdOut.printf(" wordNetNounSet get: " + wordnet.wordNetNounSet.keys() + "\n" );

            StdOut.println(wordnet.distance("pinhead", "Abila"));
          
//          StdOut.println(wordnet.sap("Black_Plague", "black_marlin"));
//          StdOut.println(wordnet.sap("American_water_spaniel", "histology"));
//          StdOut.println(wordnet.sap("Brown_Swiss", "barrel_roll"));
    }


    
}
