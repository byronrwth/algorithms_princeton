package org.chengalgorithm.first;

/****************************************************************************
 *  Compilation:  javac WeightedQuickUnionUF.java
 *  Execution:  java WeightedQuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Weighted quick-union (without path compression).
 *
 ****************************************************************************/

public class WeightedQuickUnionUF {
    public int[] id;    // id[i] = parent of i
    public int[] sz;    // sz[i] = number of objects in subtree rooted at i
    public int count;   // number of components

    // Create an empty union find data structure with N isolated sets.
    public WeightedQuickUnionUF(int N) {
        count = N;
        id = new int[N];
        sz = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    // Return the number of disjoint sets.
    public int count() {
        return count;
    }

    // Return component identifier for component containing p
    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

   // Are objects p and q in the same set?
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

  
   // Replace sets containing p and q with their union.
    public void union(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;
        
    	StdOut.println("size of "+i+" is "+sz[i]+", size of "+j+" is "+sz[j]);
    	StdOut.println("size of "+p+" is "+sz[p]+", size of "+q+" is "+sz[q]);
        
    	// make smaller root point to larger one
        if   (sz[i] < sz[j]) {
        	id[i] = j; sz[j] += sz[i]; 
        	StdOut.println("root of "+p+" is "+i+", now connect to "+j+", which is root of "+q);
        }
        else{ 
        	id[j] = i; sz[i] += sz[j]; 
        	StdOut.println("root of "+q+" is "+j+", now connect to "+i+", which is root of "+p);
        }
    	StdOut.println("size of "+i+" is "+sz[i]+", size of "+j+" is "+sz[j]);
    	StdOut.println("size of "+p+" is "+sz[p]+", size of "+q+" is "+sz[q]);
        count--;
    }


    public static void main(String[] args) {
    	StdOut.println("WeightedQuickUnionUF.java : input N now ");
        int N = StdIn.readInt();
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(N);

        // read in a sequence of pairs of integers (each in the range 0 to N-1),
         // calling find() for each pair: If the members of the pair are not already
        // call union() and print the pair.
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            
            uf.union(p, q);
            StdOut.println(p + " " + q);
            
            for(int k=0; k< N; k++){
                StdOut.println( k + ", "+ uf.id[k]);
            }
        }
        
        StdOut.println(uf.count() + " components");
    }

}
