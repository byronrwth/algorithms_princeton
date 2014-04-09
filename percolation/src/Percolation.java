
/*
* test whether a N-by-N area is percolates or not
* 
* */
public class Percolation {
    // ...
    // your data members here
    // ...
    private int constant;
    private int virtualTop;
    private int virtualBottom;
    private WeightedQuickUnionUF ufPerc; 
    private WeightedQuickUnionUF ufFull; 

    private boolean[] open;    // 
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N is illegal ");
        }
        else {
        ufPerc = new WeightedQuickUnionUF(N * N + 2);
        
        ufFull = new WeightedQuickUnionUF(N * N + 2);
        
        constant = N;
        virtualTop = N * N;
        virtualBottom = N * N + 1;
        open = new boolean[N * N + 2];
        open[virtualTop] = true;
        open[virtualBottom] = true;
        }
    }

    // self defined 2-dimention array[i,j] to 1-dimention array[] transfer
    private int xyTo1D(int i, int j) {
        if (i <= 0 ||  i > constant) {
            //throw new IllegalArgumentException();
            throw new IndexOutOfBoundsException("row index i out of bounds");
        }
        if (j <= 0 || j > constant) {
            throw new IndexOutOfBoundsException("column index j out of bounds");
        }
        else {
            return ((i - 1) * constant + j - 1);
        }
    }
    
    // open site (row i, column j) if it is not already, 1<=i,j<=N
    public void open(int row, int col) {
        // if invalide indices, exception throw
        int index = xyTo1D(row, col);
//        int k;
        // make this site open
        if (isOpen(row, col)) { 
            return; 
        }
        else {
            open[index] = true;
        }
        
        
        if (row > 1) {
            int up = xyTo1D(row - 1, col);
            if (open[up]) {
                if (!ufPerc.connected(index, up)) { 
                    ufPerc.union(index, up);
                }
                if (!ufFull.connected(index, up)) { 
                    ufFull.union(index, up);
                }
            }
        }
        
        if (col < constant) {
            int right = xyTo1D(row, col + 1);
            if (open[right]) {
                if (!ufPerc.connected(index, right)) {  
                    ufPerc.union(index, right);
                }
                if (!ufFull.connected(index, right)) {  
                    ufFull.union(index, right);
                }
            }
        }
        
        if (col > 1) {
            int left = xyTo1D(row, col - 1);
            if (open[left]) {
                if (!ufPerc.connected(index, left)) {   
                    ufPerc.union(index, left);
                }
                if (!ufFull.connected(index, left)) {   
                    ufFull.union(index, left);
                }
            }
        }
        
        if (row < constant) {
            int down = xyTo1D(row + 1, col);
            if (open[down]) {
                if (!ufPerc.connected(index, down)) {    
                    ufPerc.union(index, down);
                }
                if (!ufFull.connected(index, down)) {    
                    ufFull.union(index, down);
                }
            }
        }
        
        
        if (row == 1) {
            ufPerc.union(index, virtualTop);
            ufFull.union(index, virtualTop);
        }
        if (row == constant) {
            ufPerc.union(index, virtualBottom);
        }
        
    }

    // is site (row i, column j) open? 1<=i,j<=N
    public boolean isOpen(int i, int j) {
        int index = xyTo1D(i, j);
        
        if (open[index]) {
            return true;
        }

        return false;

    }

    // is site (row i, column j) full?  1<=i,j<=N
    public boolean isFull(int i, int j)
    {
        if (isOpen(i, j)) {
            int index = xyTo1D(i, j);
            
            // 1st row must be full
            if (i == 1) {
                return true;
            }
            // if connect to 1st row, must be full
            else {
                // to define (i,j) has been connected to virtualTop
                if (ufFull.connected(index, virtualTop)) {
                    return true;
                }
            }
        }
        return false;

    }

    // does the system percolate?
    public boolean percolates()
    {
        //if (isFull(constant, bottom_root_col)==true)
        if (ufPerc.connected(virtualTop, virtualBottom))
        {
            return true;
        }
        return false;
    }

}

