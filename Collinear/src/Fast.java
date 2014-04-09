/*
 * Given a point p, the following method determines whether p participates in a set of 4 or more collinear points. 
 * 1,Think of p as the origin.
   2 For each other point q, determine the slope it makes with p.
   3, Sort the points according to the slopes they makes with p.
   4, Check if any 3 (or more) adjacent points in the sorted order 
        have equal slopes with respect to p. If so, these points, 
        together with p, are collinear.*/
// Applying this method for each of the N points in turn yields an efficient algorithm to the problem. 
// The algorithm solves the problem because points that have equal slopes with respect to p are collinear,
// and sorting brings such points together. The algorithm is fast because the bottleneck operation is sorting.
// The order of growth of the running time of your program should be N2 log N in the worst case and 
// it should use space proportional to N.
import java.lang.*;
import java.util.*;

public class Fast {

    public static void main(String[] args) {
        //setup x,y scales
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
  
        int N = in.readInt();
        Point[] points = new Point[N];
//        System.out.println("N points are " + N);
  
        for (int i = 0; i < N; i++) {
            int x = in.readInt();       
            int y = in.readInt();        
            points[i] = new Point(x, y);
//      StdDraw.setPenColor(StdDraw.GREEN);
//      points[i].draw();
        }
        
        Arrays.sort(points,0,N,null);
        
//        for (int i = 0; i < N; i++) {
//            System.out.println(points[i].toString());
//        }
//        

        Point[] p = new Point[N];
//        int j;
//        for (j = 0; j < N; j++) {   
//          p[j] = points[j];
////          System.out.println(p[j].toString());
//        }
        
        for (int i = 0; i < N; i++) {
            
            int j;
            for (j = 0; j < N; j++) {   
                p[j] = points[j];
//                System.out.println(p[j].toString());
            }
            
            // sorts the subarray from a[lo] to a[hi-1] according to the natural order of a[]
            Arrays.sort(p, 0, p.length, points[i].SLOPE_ORDER);
            
//            for (j = 0; j < N; j++) {
//                System.out.println(points[i].slopeTo(p[j]));
//                System.out.println("j is " + j + ", " + points[i].toString() + "->" + p[j].toString());
//                System.out.println(p[0].slopeTo(p[j]));
//                
//            }
            
            for (j = 1; j < N-2; j++) {   

                if (p[0].SLOPE_ORDER.compare(p[j], p[j+1]) == 0 && p[0].SLOPE_ORDER.compare(p[j], p[j+2]) == 0 && p[0].compareTo(p[j]) < 0) {
                    System.out.println(p[0].toString() + " -> " + p[j].toString() + " -> " + p[j+1].toString() + " -> " + p[j+2].toString());
//                    System.out.println(p[0].slopeTo(p[j]));
//                    System.out.println(p[0].slopeTo(p[j+1]));
//                    System.out.println(p[0].slopeTo(p[j+2]));
                    
                    StdDraw.setPenColor(StdDraw.RED);
                        p[0].draw();
                        StdDraw.show(0);
                      p[j].draw();
                      StdDraw.show(0);
                      p[j+1].draw();
                  StdDraw.show(0);
                  points[j+2].draw();
                  StdDraw.show(0);
            
                  StdDraw.setPenColor(StdDraw.BLUE);
                  p[0].drawTo(p[j]);
                  StdDraw.show(0);
                  p[j].drawTo(p[j+1]);
                  StdDraw.show(0);
                  p[j+1].drawTo(p[j+2]);
                  StdDraw.show(0);
 
                }

            }
//            System.out.println(p[0].toString() + "->" + p[j].toString() + "->" + p[j+1].toString() + "->" + p[j+2].toString());
//            System.out.println(p[0].slopeTo(p[j]));
//            System.out.println(p[0].slopeTo(p[j+1]));
//            System.out.println(p[0].slopeTo(p[j+2]));
            
        }
            
//            for (int j = i + 1; j < N; j++) {  
//                for (int k = j + 1; k < N; k++) {  
//                    if (points[i].SLOPE_ORDER.compare(points[j], points[k]) == 0) {
//                        for (int l = k + 1; l < N; l++) {
//                            if (points[i].SLOPE_ORDER.compare(points[k], points[l]) == 0) {
//                                System.out.println(points[i].toString() + "->" + points[j].toString() + "->" + points[k].toString() + "->" + points[l].toString());
////                          System.out.println(points[i].slopeTo(points[j]));
////                          System.out.println(points[i].slopeTo(points[k]));
////                          System.out.println(points[i].slopeTo(points[l]));                               
//                                StdDraw.setPenColor(StdDraw.RED);
//                                points[i].draw();
//                                StdDraw.show(10);
//                                points[j].draw();
//                                StdDraw.show(10);
//                                points[k].draw();
//                                StdDraw.show(10);
//                                points[l].draw();
//                                StdDraw.show(10);
//                          
//                                StdDraw.setPenColor(StdDraw.BLUE);
//                                points[i].drawTo(points[j]);
//                                StdDraw.show(10);
//                                points[j].drawTo(points[k]);
//                                StdDraw.show(10);
//                                points[k].drawTo(points[l]);
//                                StdDraw.show(10);
//                            }
//                        }
//                    } 
//                }
//            }
        
            
//            }
//        }
    } 
}
