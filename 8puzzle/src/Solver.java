import java.lang.*;
import java.util.*;


/*
 * public class Solver {
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable()             // is the initial board solvable?
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    public static void main(String[] args)  // solve a slider puzzle (given below)
}
 * */
public class Solver {
    

    private Board start,goal;
    private int N;
    
    private int steps = -1;
    
    private class Node {
        Board current;
        Board prev;
        int moves;
        
        public Node(Board newBoard , Board lastBoard, int i) {
            current = newBoard;
            prev = lastBoard;
            moves = i;
        }
        
    }

  private Comparator<Node> ManhattanOrder = new ManhattanOrder() ;       
  private class ManhattanOrder implements Comparator<Node> {
           public int compare(Node a, Node b) {
             int A = a.current.manhattan() + a.moves;
             int B = b.current.manhattan() + b.moves;
             
             if (A < B) return -1;
             else if (A > B) return 1;
             else return 0;
             
          }
       
   }
  
  private MinPQ<Node> p = new MinPQ<Node>(ManhattanOrder);  
  
    public Solver(Board initial) {
        start = initial;
        N = start.dimension();
        
        Node startNode = new Node(start, null, 0);
        p.insert(startNode);
        
        int [][] block = new int [N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                
                    block[i][j] = i * N + j + 1;
                    if ( i == N -1 && j == N - 1) {
                        block[i][j] = 0;
                    }
//                    StdOut.println("creating Board goal: value is " + block[i][j]);
                }
            }
        goal = new Board(block);
    }
    
    public boolean isSolvable() {
        boolean result = false;
        
        Solver that = new Solver(start.twin());
        
        
        if (this.solution() != null && that.solution() == null) {
            result = true;
        }
        else if (this.solution() == null && that.solution() != null){
            result = false;
        }
        
        return result;
    }

    //min number of moves to solve initial board; -1 if no solution
    public int moves()  {
        
//        //remember how many tiems delMin() has been called before equal final goal !
//        if (this.solution() != null) {
//            steps = ;
//        }
        
        return steps;
    }
    
    //sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        //return null;
        
        Queue<Board> q = new Queue<Board>();
        
        
        
        while (!p.isEmpty()) {
            
            Node output = p.delMin();
            q.enqueue(output.current);
            
            if (output.current.equals(goal)) {
                steps = output.moves;
                break;
            }
            else {
                // enqueue neighbouts of output 
                Queue<Board> queNb = (Queue) output.current.neighbors();
            
                while (!queNb.isEmpty()) {
                    Board next = queNb.dequeue();
                
                    //critical optimization
                    if (!next.equals(output.prev)) {
                        Node nextNode = new Node(next, output.current, output.moves+1);
                        p.insert(nextNode);
                    }
                }
            }
            
        }
        
        
        return q;
    }
    
    public static void main(String[] args) {
        // for each command-line argument
        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int N = in.readInt();
            int[][] tiles = new int[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            Solver solver = new Solver(initial);
            
            // print solution to standard output
            if (!solver.isSolvable())
                StdOut.println("No solution possible");
            else {
                StdOut.println("Minimum number of moves = " + solver.moves());
                for (Board board : solver.solution())
                    StdOut.println(board);
            }
        }
    }
}
