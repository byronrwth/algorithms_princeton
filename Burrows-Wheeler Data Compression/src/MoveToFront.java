import java.lang.*;
import java.util.*;

public class MoveToFront {
    private static boolean test = false; 
    
    private static int N = 0;
    
    
    // R =256 for extended ASCII  
    private static Alphabet alpha = new Alphabet();
    private static int R = alpha.R();
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        
        if (test) System.out.println("MoveTofront encoding: \n ");
 
        if (test) StdOut.println("R: " + alpha.R());
        
        char[] alphabet = new char[R];
        int[] inverse = new int[R];
        
        for (int i = 0; i < R; i++) {
            alphabet[i] = (char) i;
            inverse[i] = i;
        }
        
//        In in = new In("abra.txt");
//        String input = in.readString();
//        N = input.length();
        
//        String s = BinaryStdIn.readString();
//        int N = s.length();
//        BinaryStdOut.write(N);
        
        /*=============Huffman=========================*/
        
        String s = BinaryStdIn.readString();
        
        int N = s.length();
        char[] input = new char[N];
        input = s.toCharArray();

        /*==============Huffman========================*/
        
        for (int i=0; i < N; i++) {
           
            for (int j=0; j < R; j++) {
                
                if(alphabet[j] == s.charAt(i)) {
//                    int d = alpha.toIndex(s.charAt(i));
                    
//                    BinaryStdOut.write(s.charAt(i), 8);
                    
                    
//                    char c = BinaryStdIn.readChar();
                    //test
                    
//                    StdOut.printf("%02x", j & 0xff);
                    BinaryStdOut.write((char)j);
                    
                    if (test) StdOut.println("found: " + s.charAt(i) + " at alphabet:" + j + " char: " + alphabet[j] );
                    if (test) StdOut.println(j);
                    
                    //MoveToFront
                    
                    for ( int k = j; k > 0; k--) {
                        alphabet[k] = alphabet[k-1];
                    }
                    alphabet[0] = s.charAt(i);
                    
                    
//                    for (int k = 0; k < R; k++) {
//                        if (test) StdOut.println(alpha.toChar(k));
//                    }
                    
                    break;
                }
            }

        }
        
        BinaryStdOut.close();
        
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        
        char[] alphabet = new char[R];
        int[] inverse = new int[R];
        
        for (int i = 0; i < R; i++) {
            alphabet[i] = (char) i;
            inverse[i] = i;
        }
        
//        BinaryStdIn.readInt();
        String s = BinaryStdIn.readString();
        
        int N = s.length();
//        int[] input = new int[N];
//        input = s.toCharArray();
        
        for (int i=0; i < N; i++) {
            int j = s.charAt(i);
//            StdOut.println("s: " + s.charAt(i) + " j: " + j + " + alphabet(" + alphabet[j] + ") + ");
            BinaryStdOut.write(alphabet[j]);
            char c = alphabet[j]; 
            for ( int k = j; k > 0; k--) {
                
                alphabet[k] = alphabet[k-1];
            }
            alphabet[0] = c;

        }
        
        BinaryStdOut.close();
        
            /*
            for (int j=0; j < R; j++) {
                
                if(alphabet[j] == s.charAt(i)) {
//                    int d = alpha.toIndex(s.charAt(i));
                    
//                    BinaryStdOut.write(s.charAt(i), 8);
                    
                    
//                    char c = BinaryStdIn.readChar();
                    //test
                    
//                    StdOut.printf("%02x", j & 0xff);
                    BinaryStdOut.write((char)j);
                    
                    if (test) StdOut.println("found: " + s.charAt(i) + " at alphabet:" + j + " char: " + alphabet[j] );
                    if (test) StdOut.println(j);
                    
                    //MoveToFront
                    
                    for ( int k = j; k > 0; k--) {
                        alphabet[k] = alphabet[k-1];
                    }
                    alphabet[0] = s.charAt(i);
                    
                    
//                    for (int k = 0; k < R; k++) {
//                        if (test) StdOut.println(alpha.toChar(k));
//                    }
                    
                    break;
                }
            }*/

    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        
      if      (args[0].equals("-")) encode();
      else if (args[0].equals("+")) decode();
      else throw new IllegalArgumentException("Illegal command line argument");


//      
//      if (StdIn.readString().equals("-")) {
//          encode();
//          //test
//          decode();
//      }
//      if (StdIn.readString().equals("+")) {
//          decode();
//      }
      
    }
}
