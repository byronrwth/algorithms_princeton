import java.lang.*;
import java.lang.reflect.Array;
import java.util.*;

public class BurrowsWheeler {
    
    private static boolean test = false; 
    
    private static int N = 0;
    private static int first = -1;
    private static int[] next;
    
    private static char[] o;
    private static char[] t;
    
    private static CircularSuffixArray suffix;
    
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        
        if (test) System.out.println("Burrows Wheeler encoding: \n ");
        
//        String input = BinaryStdIn.readString();
//        String input = StdIn.readString();
        
        /*        
        Alphabet DNA = new Alphabet("ACTG");
        String s = BinaryStdIn.readString();
        int N = s.length();
        BinaryStdOut.write(N);

        // Write two-bit code for char. 
        for (int i = 0; i < N; i++) {
            int d = DNA.toIndex(s.charAt(i));
            BinaryStdOut.write(d, 2);
        }
        BinaryStdOut.close();
        */
        String s = BinaryStdIn.readString();
        
        int N = s.length();
        char[] input = new char[N];
        input = s.toCharArray();
        
//        In in = new In("abra.txt");
//
//        String input = in.readString();
        
        suffix = new CircularSuffixArray(s);
        
//        String output = new String();
        
        N = s.length();
//        BinaryStdOut.write(N);
        o= new char[N];
        t= new char[N];
        
        for (int i=0; i < suffix.length(); i++) {
            if (test) StdOut.println("i: " + i + " index(i): " + suffix.index(i) + " letter: " + s.charAt(suffix.index(i)));
            
            o[i] = s.charAt(suffix.index(i));
            
            if(suffix.index(i) == 0) {
                first = i;
                
                t[i] = s.charAt(N-1);
                
//                StdOut.println("i: " + i + " index(i): " + suffix.index(i) + " char: " + s.charAt(suffix.index(i))) ;
//                BinaryStdOut.write((byte)first);
                
            }
            else {
                t[i] = s.charAt(suffix.index(i)-1);
            }

//            System.out.printf("%c", c[i]);
            
//            BinaryStdOut.write(t[i]);
        }
        
        if (test) StdOut.println(" input: " + input);
        if (test) StdOut.println(" first: " + first);

        int x = first;
        BinaryStdOut.write(x);
        
        for ( int i = 0; i< N; i++) {
            BinaryStdOut.write(t[i]);
        }
//        
//        System.out.print("\n");
        
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        
        if (test) System.out.println("Burrows Wheeler decoding: \n ");

        
        
        int first = BinaryStdIn.readInt();
       
        String s = BinaryStdIn.readString();
        N = s.length();
        char[] t = new char[N];
//        BinaryStdOut.write(first);
//        
        for ( int i = 0; i< N; i++) {
            t[i] = s.charAt(i);
            
//            BinaryStdOut.write(t[i]);
        }
        
        
        suffix = new CircularSuffixArray(s);
        char[] o= new char[N];

        for (int i=0; i < suffix.length(); i++) {            
            o[i] = s.charAt(suffix.index(i));
            
//            BinaryStdOut.write(o[i]);
        }


        char[] orig = new char[N];
        int[] next = new int[N];
        boolean[] mark = new boolean[N];
        

        for( int i= 0; i< N; i++) {
            for (int j=0; j <N; j++ ) {
                
//                BinaryStdOut.write(t[i]);
                
                if ( mark[j] != true && t[i] == o[j]) {
                    next[j] = i; 
                    mark[j] = true;
//                    BinaryStdOut.write(i);
                    
                    break;
                }
                
            }
        }
        
        
        if (t != null) {
            orig[0] = t[next[first]];
        }
        
        int x = next[first];
        
        for( int i= 1 ; i< N; i++) {
            x = next[x];
            orig[i] = t[x];
            
//            if (test) System.out.printf("%c", orig[i]);
        }
        
        for( int i= 0; i< N; i++) {
//            if (test) System.out.printf("%c", orig[i]);
            BinaryStdOut.write(orig[i]);
        }
        
        
        BinaryStdOut.close();
        
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");


        
//        if (StdIn.readString().equals("-")) {
//            encode();
//            //test
//            decode();
//        }
//        if (StdIn.readString().equals("+")) {
//            decode();
//        }
        
    }
}
