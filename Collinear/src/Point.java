/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *public class Point implements Comparable<Point> {
   public final Comparator<Point> SLOPE_ORDER;        // compare points by slope to this point

   public Point(int x, int y)                         // construct the point (x, y)

   public   void draw()                               // draw this point
   public   void drawTo(Point that)                   // draw the line segment from this point to that point
   public String toString()                           // string representation

   public    int compareTo(Point that)                // is this point lexicographically smaller than that point?
   public double slopeTo(Point that)                  // the slope between this point and that point
}
 *************************************************************************/


import java.util.Comparator;


public class Point implements Comparable<Point> {
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
            /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    // compare points by slope
    /* The SLOPE_ORDER comparator should compare points by the slopes 
     * they make with the invoking point (x0, y0). Formally, the point
     *  (x1, y1) is less than the point (x2, y2) if and only if the
     *   slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 − x0). 
     *   Treat horizontal, vertical, and degenerate line segments as in the slopeTo() method.
     * */
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder() ;       
    //compare(q, r) compares the slopes that q and r make with the invoking object p.
    private class SlopeOrder implements Comparator<Point> {
        
        // 1, either use static int compare(p,q) or 
        // 2, call Point.this.slopeTo(p) and Point.this.slopeTo(q)
        
        public int compare(Point p, Point q) {
            //horizontal, 
            if (Point.this.slopeTo(p) == 0 && Point.this.slopeTo(q) == 0) {
                return 0;
//                throw new IndexOutOfBoundsException();
            }
            //vertical, 
            else if (Point.this.slopeTo(p) == Double.POSITIVE_INFINITY && Point.this.slopeTo(q) == Double.POSITIVE_INFINITY) {
                return 0;
//                throw new IndexOutOfBoundsException();
            }
            //and degenerate line segments
            else if (Point.this.slopeTo(p) == Double.NEGATIVE_INFINITY && Point.this.slopeTo(q) == Double.NEGATIVE_INFINITY) {
                return 0;  
//                throw new IndexOutOfBoundsException();
            }
            else if (Point.this.slopeTo(p) < Point.this.slopeTo(q)) return -1;
            else if (Point.this.slopeTo(p) > Point.this.slopeTo(q)) return +1;
            else return 0;    
        }
    }
    


    
    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    /*  The slopeTo() method should return the
     *  slope between the invoking point (x0, y0) and the argument point (x1, y1), 
     *  which is given by the formula (y1 − y0) / (x1 − x0). 
     *  Treat the slope of a horizontal line segment as positive zero ; 
     *  treat the slope of a vertical line segment as positive infinity; 
     *  treat the slope of a degenerate line segment (between a point and itself) as negative infinity
     * 
     * */
    // consider a variety of corner cases, including horizontal, vertical, and degenerate line segments.
    public double slopeTo(Point that) {
        
        if (this.y == that.y && this.x == that.x) {
            return Double.NEGATIVE_INFINITY;//negative infinity
        }
        //horizontal line segment
        else if (this.y == that.y) {
//            if (that.x > this.x) {
                return 0.0; //positive zero
//            }
//            else return -0.0;  //negative zero
        }
        //vertical line segment
        else if (this.x == that.x) {
//            if (that.y > this.y) {
                return Double.POSITIVE_INFINITY; //positive infinity
//            }
//            else return Double.NEGATIVE_INFINITY;
        }
        else {
            return (1.0 * (that.y -this.y) / 1.0 / (that.x - this.x)); 
        }
        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    // The compareTo() method should compare points by their y-coordinates, 
    // breaking ties by their x-coordinates. Formally, the invoking point (x0, y0)
    // is less than the argument point (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        }
        else if (this.y == that.y) {
            if (this.x < that.x) {
                return -1;
            }
            else if (this.x == that.x) {
                return 0;
            }    
            else return +1;
        }
        else return +1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /*
        int x0 = Integer.parseInt(args[0]);
        int y0 = Integer.parseInt(args[1]);
        int N = Integer.parseInt(args[2]);

        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenRadius(.005);
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            int x = StdRandom.uniform(100);
            int y = StdRandom.uniform(100);
            points[i] = new Point2D(x, y);
            points[i].draw();
        }

        // draw p = (x0, x1) in red
        Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(.02);
        p.draw();


        // draw line segments from p to each point, one at a time, in polar order
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.POLAR_ORDER);
        for (int i = 0; i < N; i++) {
            p.drawTo(points[i]);
            StdDraw.show(100);
        }
        */
    }
}