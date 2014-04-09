package org.chengalgorithm.first;



	public class QuickFindUF {
		public int[] id;
	    public int count;
	    
	public QuickFindUF(int N) {
		
		count = N;
		
		id = new int[N];
		
		for(int i = 0; i < N; i++){
			id[i] = i;       // N array access
		}
	}
	
    // return number of connected components
    public int count() {
        return count;
    }
    
    // Return component identifier for component containing p
    public int find(int p) {
        return id[p];
    }
    
	public boolean connected(int p, int q) {
		return id[p] == id[q];    // 2 array access
	}

	// union: index=  0  1  2  3  4     5  6
	//         id[]= |0 |1 |2 |3 |4->3 |5 |6 |
	public void union(int p, int q) {
		
		if (connected(p, q)) return;
		
		// change pid to be qid !
		int pid = id[p];
		int qid = id[q]; // 2 array access
		
		for( int i = 0; i < id.length; i++){
			if(id[i] == pid){
				id[i] = qid;     // max. 2N array access
			}
		}
		count--;
	}
	
	public static void main(String[] args) {
	
		// write to stdout
		StdOut.println("QuickfindUF.java : input N now ");

    	int N = StdIn.readInt();
  
    	StdOut.println(" N is " + N);
  
  		QuickFindUF uf = new QuickFindUF(N);
  		
//  		for(int i = 0; i < N; i++){
//  			StdOut.println(" id[" +i +"] is " + uf.id[i]);
//  		}
	
        // read in a sequence of pairs of integers (each in the range 0 to N-1),
        // calling find() for each pair: If the members of the pair are not already
        // call union() and print the pair.
        while (!StdIn.isEmpty()) {
        	
        	//StdOut.println("QuickfindUF.java : input p now ");
            int p = StdIn.readInt();
            StdOut.println(" p is " + p);
            
            //StdOut.println("QuickfindUF.java : input q now ");
            int q = StdIn.readInt();
            StdOut.println(" q is " + q);
            
            if (uf.connected(p, q)) continue;
            
            uf.union(p, q);
            StdOut.println(p + " " + q);
            
            for(int j=0; j< N; j++){
            	StdOut.println( j + ", "+ uf.id[j]);
            }
        }
        StdOut.println(uf.count() + " components");
	}	    

}