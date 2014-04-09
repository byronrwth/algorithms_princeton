import java.util.Arrays;

/*
 * examines 4 points at a time and checks whether
 *  they all lie on the same line segment, printing
 *   out any such line segments to standard output 
 *   and drawing them using standard drawing. To check
 *    whether the 4 points p, q, r, and s are collinear, 
 *    check whether the slopes between p and q, between p and r,     
 *     and between p and s are all equal.*/

//The order of growth of the running time of your program should be N4 
//in the worst case and it should use space proportional to N.

/*Write code to iterate through all 4-tuples and
 *  check if the 4 points are collinear. To draw the line segment,
 *   you need to know the endpoints. One approach is to print out 
 *   a line segment only if the 4 points are in ascending order 
 *   (say, relative to the natural order), in which case, the 
 *   endpoints are the first and last points.
Hint: don't waste time micro-optimizing the brute-force solution.
 Though, if you really want to, there are two easy opportunities. 
 First, you can iterate through all combinations of 4 points 
 (N choose 4) instead of all 4 tuples (N^4), saving a factor of 
 4! = 24. Second, you don't need to consider whether 4 points are
  collinear if you already know that the first 3 are not collinear; 
  this can save you a factor of N on typical inputs.
 * */
public class Brute {
    public static void main(String[] args){
          //setup x,y scales
//        StdDraw.setCanvasSize(800, 800);
//        StdDraw.setXscale(0, 100);
//        StdDraw.setYscale(0, 100);
//        StdDraw.setPenRadius(.005);
        
        
        

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
            
//            System.out.println("x is " + x);
            
            int y = in.readInt();
            
//            System.out.println("y is " + y);
            
            
            points[i] = new Point(x, y);
//            StdDraw.setPenColor(StdDraw.GREEN);
//            points[i].draw();
            

        }
        
        Arrays.sort(points,0,N,null);
        
        for (int i = 0; i < N; i++) {
            
            for (int j = i + 1; j < N; j++) {
                
                for (int k = j + 1; k < N; k++) {
                    
                    if (points[i].SLOPE_ORDER.compare(points[j], points[k]) == 0) {
                       
                        for (int l = k + 1; l < N; l++) {
                            
                            if (points[i].SLOPE_ORDER.compare(points[k], points[l]) == 0) {
                                System.out.println(points[i].toString() + " -> " + points[j].toString() + " -> " + points[k].toString() + " -> " + points[l].toString());
                                
//                                System.out.println(points[i].slopeTo(points[j]));
//                                System.out.println(points[i].slopeTo(points[k]));
//                                System.out.println(points[i].slopeTo(points[l]));
                                        
                                StdDraw.setPenColor(StdDraw.RED);
                                points[i].draw();
                                StdDraw.show(10);
                                points[j].draw();
                                StdDraw.show(10);
                                points[k].draw();
                                StdDraw.show(10);
                                points[l].draw();
                                StdDraw.show(10);
                                
                                StdDraw.setPenColor(StdDraw.BLUE);
                                points[i].drawTo(points[j]);
                                StdDraw.show(10);
                                points[j].drawTo(points[k]);
                                StdDraw.show(10);
                                points[k].drawTo(points[l]);
                                StdDraw.show(10);
                            }
                        }
                    }
                    
                }
            }

        }
        // display to screen all at once
//        StdDraw.show(0);
    }
}
