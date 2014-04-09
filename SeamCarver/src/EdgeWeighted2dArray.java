import java.lang.*;
import java.util.*;
import java.awt.Color;

public class EdgeWeighted2dArray {
    private boolean test = false;
    
    private int W;
    private int H;
//    private Bag<DirectedEdge>[] adj;
    
    private EdgeWeightedDigraph G;
    private AcyclicSP sp;
    
    public double sum = 0.0;
    public int[] index;
    

    /**
     * Create an edge-weighted 2d Array by inputting from W * H vertex array
     */
    public EdgeWeighted2dArray(int x, int y, double array[][]) {

        this.W = x;
        this.H = y;
        
        if (test) StdOut.println("now draw AcyclicSP for 2d array: W= "+ this.W + " H= " +this.H);

         int V = 1 + this.W * this.H + 1;

         int s = 0;
         int t = V-1;
         // for each pixel ( col i, row j) transfer to node(i + j * W + 1) in DAG  

         this.sum = 0.0;
         double weight = 0.0;
             
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
                     weight = array[j][i];

                     this.G.addEdge(new DirectedEdge(v, t, weight));
                 }

                 if (j < this.H-1) {
                     if (i > 0) {
                         int w = this.node(i-1, j+1); 
                         weight = array[j][i];
                         this.G.addEdge(new DirectedEdge(v, w, weight));
                     }

                     if (i  < this.W-1) {
                         int w = this.node(i+1, j+1); 
                         weight = array[j][i];
                         this.G.addEdge(new DirectedEdge(v, w, weight));
                     }
                     int w = this.node(i, j+1); 
                     weight = array[j][i];
                     this.G.addEdge(new DirectedEdge(v, w, weight));
                 }

             }

         }

//         if( test) StdOut.println(this.G);

         // find shortest path from s to bottom vertex in DAG
         this.sp = new AcyclicSP(this.G, s);

         if (this.sp.hasPathTo(t)) {

             //             StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
             if( test) StdOut.printf("%d to %d (%.2f)  ", s, t, this.sp.distTo(t));

             this.index = new int[this.H];


             for (DirectedEdge e : this.sp.pathTo(t)) {

                 if( test) StdOut.print(e + "   ");

                 this.sum = this.sum + e.weight();
                 int value = e.from();

                 int row = (value - 1) / this.W;
                 int col = (value - 1) % this.W;
                 this.index[row] = col;

                 if( test) StdOut.print("pixel col= " + col + " , row= " + row + "\n");

             }

             if (test) {
                 for (int row = 0; row < this.H; row++){
                     StdOut.println(this.index[row]);
                 }
             }

             if( test) StdOut.println("vertical seam has sum weight: " + this.sum);


         }  
    } 
    
    private int node(int col, int row) {
        return col + row * this.W + 1;
    }
    
    public int[] getSeam() {
        return this.index;
    }
    
    /**
     * Test client.
     */
    /*
    public static void main(String[] args) {
        
        int width = StdIn.readInt();
        int height = StdIn.readInt();
        
        In in = new In(args[0]);
        double[][] array = new double[height][width];
        
        for ( int i=0; i < height; i++) {
            for(int j=0; j < width; j++) {
                array[i][j] =  in.readInt();
//                StdOut.println(array[i][j]);
                System.out.print(array[i][j] + " ");
            }
            System.out.print("\n");
        }


        System.out.println();
        
        EdgeWeighted2dArray dag2d = new EdgeWeighted2dArray(width, height, array);
        
       
        int[] vertical1 = new int[height];
        int[] vertical2 = dag2d.getSeam();
        
        for (int row = 0; row < height; row++){

            vertical1[row] = dag2d.index[row];

            {
                StdOut.println("vertical1: " + vertical1[row]);
                StdOut.println("dag2d: " + dag2d.index[row]);
                StdOut.println("vertical2: " + vertical2[row]);
            }
        }

    }*/
}
