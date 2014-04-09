import java.lang.*;
import java.util.*;
import java.awt.Color;

public class EdgeWeighted2dArray {
    private boolean test = true;
    
    private int W;
    private int H;
    private Bag<DirectedEdge>[] adj;
    
    private EdgeWeightedDigraph G;
    
    private int node(int col, int row) {
        return col + row * this.W + 1;
    }
    
    // for local test, set energy(i,j) == 1
    private int energy(int col, int row) {
        return 1;
    }
    
    /**
     * Create an edge-weighted 2d Array by inputting from W * H vertex array
     */
    public EdgeWeighted2dArray(int x, int y) {
        /*        
        //int[][] 2dArray

//        if (V < 0) throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");
        this.W = 2dArray.length;
        this.H = 2dArray;

        // virtual top, has W neighbors, W pathto()
        int virtualS = W * H;

        // virtual bottom, W neighbors,  W pathfrom()
        int virtualT = W * H + 1; 

        // else each node in each row of W nodes, has 3 pathfrom(), 3 pathth(), select min energy node as its neighbor 
        adj = (Bag<DirectedEdge>[]) new Bag[ W * H ];
        for (int x = 0; x < W; x++) {
            for (int y = 0; y < H; y++) {
                adj[v] = new Bag<DirectedEdge>();
            }
        }

        //
         */
        this.W = x;
        this.H = y;
        
        if( test) StdOut.println("now draw AcyclicSP for 2d array: W= "+ this.W + " H= " +this.H);

         int V = 1 + this.W * this.H + 1;

         int s = 0;
         int t = V-1;
         // for each pixel ( col i, row j) transfer to node(i + j * W + 1) in DAG  

         double Seam = 0.0;

         this.G = new EdgeWeightedDigraph(V);

         for (int j= 0; j< this.H; j++ ) {

             for (int i= 0; i< this.W; i++) {

                 int v = this.node(i, j);

                 // first row j==0, direct connect from s to each node 
                 if (j== 0) {
                     // virtual source has no energy
                     this.G.addEdge(new DirectedEdge(s, v, 0.0));
                 }

                 if (j== this.H - 1) {
                     this.G.addEdge(new DirectedEdge(v, t, this.energy(i, j)));
                 }

                 if (j < this.H-1) {
                     if (i > 0) {
                         int w = this.node(i-1, j+1); 
                         double weight = this.energy(i, j);
                         this.G.addEdge(new DirectedEdge(v, w, weight));
                     }

                     if (i  < this.W-1) {
                         int w = this.node(i+1, j+1); 
                         double weight = this.energy(i, j);
                         this.G.addEdge(new DirectedEdge(v, w, weight));
                     }
                     int w = this.node(i, j+1); 
                     double weight = this.energy(i, j);
                     this.G.addEdge(new DirectedEdge(v, w, weight));
                 }

             }

         }

         if( test) StdOut.println(this.G);


         // find shortest path from s to bottom vertex in DAG
         AcyclicSP sp = new AcyclicSP(G, s);

         if (sp.hasPathTo(t)) {
             StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
             for (DirectedEdge e : sp.pathTo(t)) {
                 StdOut.print(e + "   ");
             }
             StdOut.println();
         }  
    } 
    
    /**
     * Test client.
     */
    public static void main(String[] args) {

        In in1 = new In(args[0]);
        In in2 = new In(args[1]);
        int x = in1.readInt();
        int y = in2.readInt();
        
        EdgeWeighted2dArray array = new EdgeWeighted2dArray(x,y);
        
    }
}
