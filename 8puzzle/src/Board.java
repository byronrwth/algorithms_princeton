
import java.lang.*;
import java.util.*;

/*
 * public class Board {
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension()                 // board dimension N
    public int hamming()                   // number of blocks out of place
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal()                // is this board the goal board?
    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row,
                                           // 1 out of 5 
    public boolean equals(Object y)        // does this board equal y?
    public Iterable<Board> neighbors()     // all neighboring boards
    public String toString()               // string representation of the board (in the output format specified below)
}*/

public class Board {
    private int N = 0;
//    private int hamdistance = 0; 
    private int [][] myblock;
    private int blank_col = 0;
    private int blank_row = 0;
    

    
    public Board(int[][] blocks) {
        myblock = blocks;
        int i, j;
        N = myblock.length;
        boolean blank = false;
//        StdOut.println(" N is " + N);
        
        for (i = 0; i < N; i++) {
            for (j = 0; j < N; j++) {
                
//                StdOut.println("reading value of myblock = " + myblock[i][j]);
                
                if (myblock[i][j] == 0) {
                    blank_col = j;
                    blank_row = i;
                    blank = true;
                    break;
                }
            }
            if (blank) {
                break;
            }
        }
    }
    
    public int dimension() {
//        N = myblock.length;
        return N;
    }
    
    public int hamming() {
        int hamdistance = 0; 
//        N = this.dimension();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                
//                StdOut.println(" myblock has a: " + myblock[i][j]);
                
                if (myblock[i][j] != 0 && myblock[i][j] != i * N + j + 1) {
                    hamdistance++;
                    
                }
            }
        }
        return hamdistance;
    }
    
    public int manhattan() {
        int mandistance = 0; 
        int row = 0;
        int col = 0;
        int temp = 0;
//        N = this.dimension();
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                
                if (myblock[i][j] != 0 && myblock[i][j] != i * N + j + 1) {
                     row = (myblock[i][j]-1) / N ;
                     col = myblock[i][j] % N - 1;
                     if (col < 0) col += N; 
                     
                     temp = Math.abs(row-i)+Math.abs(col-j) ;
                     
//                     StdOut.println(" manhattan: i: " + i + " j: " + j);
//                     StdOut.println(" manhattan: myblock: " + myblock[i][j]);
//                     StdOut.println(" manhattan: row is " + row);
//                     StdOut.println(" manhattan: col is " + col);
//                     StdOut.println(" manhattan: temp is " + temp);
//                     StdOut.println(" manhattan: mandistance is " + mandistance);
                     
                     mandistance += temp;
                }
            }
        }
        return mandistance;
    }
    
    public boolean isGoal() {
//        boolean goal = false; 
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                
                if (myblock[i][j] != 0 && myblock[i][j] != i * N + j + 1) {
                    return false;
                }
            }
        }
        return true;
    }
    
    //return any twin , just swap two adjacent tiles in the same row, neither of which are the blank tile, 0.
    public Board twin() {
        
        int[][] twinBlocks = new int[N][N];
        boolean twined = false;
        int temp;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twinBlocks[i][j] = myblock[i][j];
            }
        }
        
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                 if ( !twined && j + 1 < N && twinBlocks[i][j] != 0 && twinBlocks[i][j+1] != 0) {
                            temp = twinBlocks[i][j];
                            twinBlocks[i][j] = twinBlocks[i][j+1];
                            twinBlocks[i][j+1] = temp;
                            twined = true;
                            break;
                 }

            }
            if (twined)
                break;
        }
        
        Board twin = new Board(twinBlocks);
        
        return twin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y==null) return false; 
//        if (y==this) return true;
        boolean result = false;
        if (y instanceof Board) {
        Board that = (Board)y;
        if (that.N != this.N) return false; 
        
        result = Arrays.deepEquals(this.myblock, that.myblock);
        
//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {   
//                if ( that.myblock[i][j] != this.myblock[i][j]) {
//                    return false;
//                }
//            }
//        }
        }
        return result;
    }
    
    // all neighboring boards, return something can iterate over !
    public Iterable<Board> neighbors() {
        
//        private MinPQ<Board> copy;
        
        Queue<Board> q = new Queue<Board>();
        int[][] newblock = new int[N][N];
        
        //already known blank_row, blank_col ;


        
        //enqueue max 4 neighbouts Board
        
        if (blank_col > 0) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    newblock[i][j] = myblock[i][j];
                }
            }
            newblock[blank_row][blank_col - 1] = myblock[blank_row][blank_col];
            newblock[blank_row][blank_col] = myblock[blank_row][blank_col - 1];
            Board neighbor = new Board(newblock);
            q.enqueue(neighbor);
//            StdOut.println(neighbor);
        }
        if (blank_col < N - 1) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    newblock[i][j] = myblock[i][j];
                }
            }
            newblock[blank_row][blank_col + 1] = myblock[blank_row][blank_col];
            newblock[blank_row][blank_col] = myblock[blank_row][blank_col + 1];
            Board neighbor = new Board(newblock);
            q.enqueue(neighbor);
//            StdOut.println(neighbor);
        }
        if (blank_row > 0) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    newblock[i][j] = myblock[i][j];
                }
            }
            newblock[blank_row - 1][blank_col] = myblock[blank_row][blank_col];
            newblock[blank_row][blank_col] = myblock[blank_row - 1][blank_col];
            Board neighbor = new Board(newblock);
            q.enqueue(neighbor);
//            StdOut.println(neighbor);
        }
        if (blank_row < N - 1) {
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    newblock[i][j] = myblock[i][j];
                }
            }
            newblock[blank_row + 1][blank_col] = myblock[blank_row][blank_col];
            newblock[blank_row][blank_col] = myblock[blank_row + 1][blank_col];
            Board neighbor = new Board(newblock);
            q.enqueue(neighbor);
//            StdOut.println(neighbor);
        }
        
        return q;
    }



    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", myblock[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    
    public static void main(String[] args) {
        
        In in = new In(args[0]);
        int N = in.readInt();
        
//        StdOut.println(" N is " + N);
        
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                blocks[i][j] = in.readInt();
//                StdOut.println(" value = " + blocks[i][j]);
            }
        
        
        Board initial = new Board(blocks);
        StdOut.println(initial);
        StdOut.println(" dimension is " + initial.dimension());
        StdOut.println(" hamming is " + initial.hamming());
        StdOut.println(" manhattan is " + initial.manhattan());
        StdOut.println(" blank_col is " + initial.blank_col);
        StdOut.println(" blank_row is " + initial.blank_row);
        
        Queue<Board> q = (Queue)initial.neighbors();
        
        while (!q.isEmpty()){
            StdOut.println(q.dequeue());
        }
        
        Board twin = initial.twin();
        StdOut.println(twin);

    }
    
}
