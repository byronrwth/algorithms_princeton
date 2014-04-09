

/****************************************************************************
 *  Compilation:  javac QuickUnionUF.java
 *  Execution:  java QuickUnionUF < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *
 *  Quick-union algorithm.
 *
 ****************************************************************************/
public class QuickUnionUF {
	

    public static int[] id;    // id[i] = parent of i
    public int count;   // number of components

    // instantiate N isolated components 0 through N-1
    public QuickUnionUF(int N) {
        id = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    // return number of connected components
    public int count() {
        return count;
    }

    // return root of component corresponding to element p
    public int find(int p) {
        while (p != id[p])
            p = id[p];
        return p;
    }

    // are elements p and q in the same component?
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    // merge components containing p and q
    public void union(int p, int q) {
        int i = find(p); StdOut.println("root of "+p+" is "+i);
        int j = find(q); StdOut.println("root of "+q+" is "+j);
        if (i == j){ 
        	StdOut.println(p+"and"+q+"are already connected");
        	return;
        }
        id[i] = j; 
        StdOut.println("root of "+p+" is "+i+", now connect to "+j+", which is root of "+q);
        count--;
    }

    public static void main(String[] args) {
    	
    	StdOut.println("QuickUnionUF.java : input N now ");
    	
        int N = StdIn.readInt();
        QuickUnionUF uf = new QuickUnionUF(N);

        // read in a sequence of pairs of integers (each in the range 0 to N-1),
         // calling find() for each pair: If the members of the pair are not already
        // call union() and print the pair.
        while (!StdIn.isEmpty()) {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
            
            for(int j=0; j< N; j++){
            	StdOut.println( j + ", "+id[j]);
            }
        }
        StdOut.println(uf.count() + " components");
    }


}
