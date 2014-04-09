import java.lang.*;
import java.util.*;

import javax.swing.event.AncestorEvent;

public class SAP {
    private int V;
    private int E;

    private Digraph G;
    private int ancestor;
    
    private boolean test = false;
 // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        //use space proportional to E + V;
        //take time at most proportional to E + V in the worst case;

        Digraph G1 = G;
        this.G = new Digraph(G1);
        this.V = this.G.V();
        this.E = this.G.E();
        
        this.ancestor = -1;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if (v < 0 || v >= this.V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (this.V-1));
        if (w < 0 || w >= this.V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (this.V-1));
        
        int length = -1;
        this.ancestor = -1;
        
        if ( v == w) {
            length = 0;
            this.ancestor = v;
            return length;
        }
        // algorithm
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.G, w);
        
        for (int i = 0; i < this.G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int distance = bfs1.distTo(i) + bfs2.distTo(i);
                if (test) StdOut.printf("distance is : %d \n ", distance);
                
                if (length == -1 && distance > 0) {
                    length = distance;
                    this.ancestor = i;
                }
                else if( length > distance){
                    length = distance;
                    this.ancestor = i;
                }
                

//                for (int x : bfs1.pathTo(i)) {
//                    if (x == v) StdOut.print(x);
//                    else        StdOut.print("-" + x);
//                }
//                StdOut.println();
//                for (int x : bfs2.pathTo(i)) {
//                    if (x == w) StdOut.print(x);
//                    else        StdOut.print("-" + x);
//                }
//                StdOut.println();
            }
        }
        if (test) StdOut.printf("legnth is : %d \n ", length);
        return length;

        
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if (v < 0 || v >= this.V) throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (this.V-1));
        if (w < 0 || w >= this.V) throw new IndexOutOfBoundsException("vertex " + w + " is not between 0 and " + (this.V-1));
        
        if (this.length(v, w) > -1 ) {
            return this.ancestor;
        }
        else return -1;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        //iterable arguments contain at least one integer;
        
        //if ( /* one (or more) of the input arguments is not between 0 and G.V() - 1 */) throw java.lang.IllegalArgumentException
        
        
        // a common ancestor should exist for both iterable v and iterable w:
        int length = -1;
        this.ancestor = -1;
        
        // algorithm
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(this.G, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(this.G, w);
        
        for (int i = 0; i < this.G.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int distance = bfs1.distTo(i) + bfs2.distTo(i);
                
                if (test) StdOut.printf("distance to %d is : %d \n ", i, distance);
                
                if (length == -1 && distance > 0) {
                    length = distance;
                    this.ancestor = i;
                }
                else if( length > distance){
                    length = distance;
                    this.ancestor = i;
                }
                
//                StdOut.println(bfs1.pathTo(i));
//                StdOut.println(bfs2.pathTo(i));

            }
        }
        if (test) StdOut.println(bfs1.pathTo(this.ancestor));
        if (test) StdOut.println(bfs2.pathTo(this.ancestor));
        if (test) StdOut.printf("legnth to ancestor %d is : %d \n ", this.ancestor, length);
        
        return length;
        
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        //iterable arguments contain at least one integer;;
   
        //if ( /* one (or more) of the input arguments is not between 0 and G.V() - 1 */) throw java.lang.IllegalArgumentException
        
        if (this.length(v, w) > -1 ) {
            return this.ancestor;
        }
        else return -1;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            
            /* test any v and w */
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
            
            sap.G.addEdge(v, 0);
            /* test immutable of SAP */
            length   = sap.length(v, w);
            ancestor = sap.ancestor(v, w);
            StdOut.printf("after add (v,0) to G: length = %d, ancestor = %d\n", length, ancestor);
            
        }
    }
}
