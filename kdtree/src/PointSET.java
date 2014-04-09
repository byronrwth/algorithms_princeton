import java.util.*;
import java.lang.*;

public class PointSET {
    private TreeSet<Point2D> set;
    
    public PointSET() {
        set = new TreeSet<Point2D>();
    }
    
    public boolean isEmpty() {
        return set.isEmpty();
    }
    
    public int size() {
        return set.size();
    }
    
    public void insert(Point2D p) {
        if (p == null) throw new NoSuchElementException();
        if (!set.contains(p)) {
            set.add(p);
        }
    }
    
    public boolean contains(Point2D p) {
        if (p == null) throw new NoSuchElementException();
        return set.contains(p);
    }
    
    public void draw() {
        StdDraw.show(0);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.005);
        StdDraw.line(0.0, 0.0, 0.0, 1.0);
        StdDraw.line(0.0, 0.0, 1.0, 0.0);
        StdDraw.line(1.0, 0.0, 1.0, 1.0);
        StdDraw.line(0.0, 1.0, 1.0, 1.0);
        StdDraw.show(0);
        
        for (Point2D p : set) {
//            StdOut.println(p);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.03);
            p.draw();
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NoSuchElementException();
        
        Queue<Point2D> q = new Queue<Point2D>();
        
        for (Point2D p : set) {
            if (rect.contains(p)) {
//                StdOut.println(p);
                q.enqueue(p);
            }
        }
        return q;
    }
    
    public Point2D nearest(Point2D p) {
        // a nearest neighbor in the set to p; null if set is empty
        if (p == null) throw new NoSuchElementException();
        
        if (this.isEmpty()) {
            return null;
        }
        else {
            
            Stack<Point2D> s = new Stack<Point2D>(); 
            double minDistance = 10.0;
       
            for (Point2D p1 : set) {
                if (p.distanceTo(p1) <= minDistance) {
                       minDistance = p.distanceTo(p1);
                       s.push(p1);
                }
            }
            if (minDistance < 10.0 ) return s.pop();
            else return null;
        }
    }
}
