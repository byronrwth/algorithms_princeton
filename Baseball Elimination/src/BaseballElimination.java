import java.lang.*;
import java.util.*;

public class BaseballElimination {
    private boolean test = false; 
    
    private int N;

    private HashMap<String, Integer> team; 
    private int[] win,loss,remain;
    private int[][] capacity; 
    private String[] name;
    
    private boolean[]  isEliminated;
    
    private IndexMaxPQ<Integer> indexMaxPQwin;
    
    private Queue<String> elimination = null; // = new Queue<String>();

    public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
    {
        In in = new In(filename);
        this.N = in.readInt();
        if (test) StdOut.println("we have " + this.N + " clubs! ");
        
        this.team = new HashMap<>();
        this.win = new int[this.N];
        this.loss = new int[this.N];;
        this.remain = new int[this.N];
        this.capacity = new int[this.N][this.N];
        this.name = new String[this.N];;
        this.indexMaxPQwin = new IndexMaxPQ<Integer>(this.N);
        this.isEliminated = new boolean[this.N];
        
        for (int line = 0; line < this.N; line++ ) {

            String club = in.readString();
            name[line] = club;
//            this.team.enqueue(club);
            this.team.put(club, line);
            
//            if (test) StdOut.println("team: " + team);
            win[line] = in.readInt();
            this.indexMaxPQwin.insert(line, win[line]);
            
            loss[line] = in.readInt();
            remain[line] = in.readInt();
            if (test) StdOut.println(club + ", win: " + win[line] + ", loss: " + loss[line] + ", remain: " + remain[line]);
            
            
            for ( int i = 0; i < this.N ; i++) {
              capacity[line][i] = in.readInt();
            }
        }
    }
    
    public int numberOfTeams()                        // number of teams
    {
        return this.N;
    }
    
    public Iterable<String> teams()                                // all teams
    {
        return this.team.keySet();
    }
    
    public int wins(String team)                      // number of wins for given team
    {
        if ( !this.team.containsKey(team) ) throw new java.lang.IllegalArgumentException();

        int wins = 0;
        int id = this.team.get(team);
        
        return this.win[id];
    }
    
    public int losses(String team)                    // number of losses for given team
    {
        if ( !this.team.containsKey(team) ) throw new java.lang.IllegalArgumentException();
        
        int loss = 0;
        int id = this.team.get(team);
        
        return this.loss[id];

    }
    
    public int remaining(String team)                 // number of remaining games for given team
    {
        if ( !this.team.containsKey(team) ) throw new java.lang.IllegalArgumentException();
        
        int remain = 0;
        int id = this.team.get(team);
        return this.remain[id];
    }
    
    public int against(String team1, String team2)    // number of remaining games between team1 and team2
    {
        if ( !this.team.containsKey(team1) || !this.team.containsKey(team2) ) throw new java.lang.IllegalArgumentException();
        
        int against = 0;
        int id1 = this.team.get(team1);
        int id2 = this.team.get(team2);
        return this.capacity[id1][id2];
    }

    public boolean isEliminated(String team)              // is given team eliminated?
    {
        if ( !this.team.containsKey(team) ) throw new java.lang.IllegalArgumentException();

        boolean result = false;
        this.elimination = null;
        
        int myID = this.team.get(team);
        this.isEliminated[myID] = false;
        
//        if( test) StdOut.println("team ID: "+ myID);
        
        int myPlays = this.win[myID] + this.remain[myID];

        //for checking trivial eliminaiton, find Max Win team 
        if (  this.indexMaxPQwin.maxKey() > myPlays) {

            this.elimination = new Queue<String>();
            this.elimination.enqueue(this.name[this.indexMaxPQwin.maxIndex()]);

            return true;
        }
        else {   // for checking non-trivial elimination , start FlowNetwork
            
            if( test) StdOut.println("now draw flownetwork for: "+ team);
            
            int V = 1 + this.N * (this.N - 1)/2 + this.N + 1;
            FlowNetwork G = new FlowNetwork(V);
            int s = 0;
            int t = V-1;
            int games = 1;
            FlowEdge e;

            double sum = 0.0;

            for ( int i = 0; i < this.N ; i++) {
                if ( i != myID) {
                    
                    int v = V - N - 1 + i;

                    double capacity = myPlays - this.win[i];
                    if (capacity > 0) {
                        e = new FlowEdge(v, t, capacity);
                        G.addEdge(e);
                        

                        if( test) StdOut.println("edge: "+ v + " -> " + t + " : capacity= " + capacity);
                        if( test) StdOut.println("v stand: "+ this.name[i]);
                        if( test) StdOut.println("t collects capacity: "+ sum);
                    }
                    
                    int count = 0;
                    for ( int j = i+1; j < this.N; j++) {
                        
                        if( test) StdOut.println("i: "+ i + " j: " + j);
                        
                        if (j != myID) {
                            if (this.capacity[i][j] > 0) {
                                count++;
                                int w = V - N - 1 + j;
                                capacity = this.capacity[i][j];
                                e = new FlowEdge(s, games, capacity);
                                G.addEdge(e);
                                sum += capacity;
                                
                                if( test) StdOut.println("edge: "+ s + " -> " + games + " : capacity= " + capacity);
                                if( test) StdOut.println("games stand: " + v + " -> " + w + " :" + this.name[i] + this.name[j]);
//                                if( test) StdOut.println("t collects capacity: "+ sum);
                                
                                e = new FlowEdge(games, v, Double.POSITIVE_INFINITY);
                                G.addEdge(e);
                                e = new FlowEdge(games, w, Double.POSITIVE_INFINITY);
                                G.addEdge(e);
                                games++;
                            }//capacity[i][j] > 0
                        }
                        
                    }// for capacity[i][j] 
                } // skip myID
            }// for N teams
            
            FordFulkerson ff = new FordFulkerson( G, s, t);
            
            
//            if( test) StdOut.println(G);
//
//            if( test) StdOut.println("maxflow: "+ ff.value() + " ,sum capacity from s:"+sum);
//            
//            // compute maximum flow and minimum cut
//            if( test) StdOut.println("Max flow from " + s + " to " + t);
//            for (int n = 0; n < G.V(); n++) {
//                for (FlowEdge e1 : G.adj(n)) {
//                    if ((n == e1.from()) && e1.flow() > 0)
//                        StdOut.println("   " + n);
//                }
//            }
//
//            // print min-cut
//            if( test) StdOut.print("Min cut: ");
//            for (int n = 0; n < G.V(); n++) {
//                if (ff.inCut(n)) StdOut.print(n + " ");
//            }
//            if( test) StdOut.println();
            
            
            if (ff.value() >= sum) {
                result = false;
            }
            else { // now non-trivial elimination
                result = true;
                this.elimination = new Queue<String>();
                //find mincut of team
                for ( int i = 0; i < this.N ; i++) {
                    int v = V - N - 1 + i;
                    if (ff.inCut(v)) {
                        this.elimination.enqueue(this.name[i]);
                    }
                }
            }

        }

        return result;
    }
    
    public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
    {
        if ( !this.team.containsKey(team)) throw new java.lang.IllegalArgumentException();
        
        return this.elimination;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        
        for (String team : division.teams()) {

            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team))
                    StdOut.print(t + " ");
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
