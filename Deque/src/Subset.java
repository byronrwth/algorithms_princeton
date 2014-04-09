import java.util.Iterator;
/*
 * takes a command-line integer k, 
 * reads in a sequence of N strings from standard input 
 * using StdIn.readString(), and prints out exactly k of them, 
 * uniformly at random. Each item from the sequence can be
 *  printed out at most once. You may assume that k ≥ 0 and 
 *  no greater than the number of string on standard input.
 *  e.g. >echo 1 2 3 4 5 6 7 8 9 10 | java Unitest 3
 * **/
public class Subset {
    public static void main(String[] args) {
        
      int k = 0;
      k = Integer.parseInt(args[0]);
//      StdOut.println("k is " + k);
      
      RandomizedQueue<String> q = new RandomizedQueue<String>();

      while (!StdIn.isEmpty()) {      
          String item = StdIn.readString();
          //if (!item.equals("-")) {
             q.enqueue(item);
          //}
//          else if (!q.isEmpty()) {
//             StdOut.print(q.dequeue() + " ");
//          }
          
//          StdOut.println("(" + q.size() + " left on queue)");

      }
        
      Iterator A = q.iterator();
//      Iterator B = q.iterator();
      for ( int i = 0; i < k; i++) {
             StdOut.print(A.next() + "\n");
//             StdOut.print("iterator.next() is " + B.next() + " ; \n " );
             
      }


        
    }
}
