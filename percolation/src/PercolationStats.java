//import java.lang.*;

/*
* include a main() method that takes two command-line arguments N and T, 
* performs T independent computational experiments (discussed above) 
* on an N-by-N grid, 
 * and prints out the mean, standard deviation, 
 * and the 95% confidence interval for the percolation threshold. 
 * Use standard random from our standard libraries to generate random numbers; 
 * use standard statistics to compute the sample mean and standard deviation.
 * */
public class PercolationStats
{
    // ...
    // your data members here
    // ...
    private int times;
    private int constant;

    private boolean debug = false;
    
    private double[] prob;
    private double average = 0.0;
    private double standardDev = 0.0;
    private double high = 0.0;
    private double low = 0.0;
    
    // perform T independent computational experiments on an N-by-N grid
    public PercolationStats(int N, int T) {   
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException(" input illegal ");
        }
        else {
        // integer N >= 1 and an integer T >= 1.  
        // throw a java.lang.IllegalArgumentException if either N <= 0 or T <= 0
        constant = N;
        times = T;
        
        prob = new double[T];
        
        int row, col;
        
        for (int j = 1; j <= T; j++) {
            
            Percolation percolation = new Percolation(N);
            for (int i = 1; i <= N * N; i++) {
                
                //for each i, uniformly generate row between [1,N] and col between [1,N]
                while (true) {
                    row = 1 + StdRandom.uniform(0 , N);
                    if (debug) StdOut.println("for " + i +" time: row: " + row);
                    
                    col = 1 + StdRandom.uniform(0, N);
                    if (debug) StdOut.println("for " + i +" time: col: " + col);
                    
                    if (!percolation.isOpen(row, col)) {
                        
                        percolation.open(row, col);
                        
                        if (debug) StdOut.println("the No."+ i +" site:( "+ row +" ,  "+ col +" ) is opened !");
                        break;
                    }
                }
                
                if (percolation.percolates()) {
                    
                    prob[j-1] = 1.0 * i / (constant * constant);
                    
                    if (debug) {
                        StdOut.printf(" for try:" + j + ", i: " + i);
                        StdOut.printf(" probability is: %f \n" , prob[j - 1]);
                    }
                    break;
                }
            }
        }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        average = StdStats.mean(prob);   
        return average;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (times == 1) return Double.NaN;
        else {
            standardDev = Math.sqrt(StdStats.var(prob)); 
            return standardDev;
        }
    }

    // returns lower bound of the 95% confidence interval
    public double confidenceLo() {
       
        if (times == 1) {
            return Double.NaN;
        }
        else {
            low = this.mean() - (1.96 * this.stddev() / Math.sqrt(times));
            return low;
        }
    }

    // returns upper bound of the 95% confidence interval
    public double confidenceHi() {

        if (times == 1) {
            return Double.NaN;
        }
        else {
            high = this.mean() + (1.96 * this.stddev() / Math.sqrt(times));
            return high;
        }
    }
    
    public static void main(String[] args) {

        StdOut.println("PercolationStats.java : input N and T now ");
        
        while (!StdIn.isEmpty()) {
            int N = StdIn.readInt();         // int N = Integer.parseInt(args[0]);
            int T = StdIn.readInt();         // int T = Integer.parseInt(args[1]);
            try {
                PercolationStats percolationStats = new PercolationStats(N, T);
                
                StdOut.printf("mean is: %f \n" , percolationStats.mean());
                StdOut.printf("standard deviation is: %f \n" , percolationStats.stddev());
                StdOut.printf("95 percent condifence interval low is: %f \n" , percolationStats.confidenceLo());
                StdOut.printf("95 percent condifence interval high is: %f \n" , percolationStats.confidenceHi());
            }
            catch (Exception e) {
                System.out.println(e);
            }

            
        }
    }
}
