
import java.util.*;
import java.lang.*;

public class KdTree /*extends BST*/{
    private Node root;
    private int N = 0;
    
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int level;
        
        private Node(Point2D p, RectHV rect, int level) {
            this.p = p;
            this.rect = rect;
            this.level = level;
        }
     }
    
    public KdTree() {
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public int size() {
        return N;
    }
    
    private Node put(Node x, Point2D p, int level, RectHV area) {
        if (x == null) {
            N++;
//            System.out.printf(" rect %s for new node %s for put()! \n", area.toString(),p.toString());
            return new Node(p, area, level);  //public RectHV(double xmin, double ymin, double xmax, double ymax) 
        }
        else {
            if( level == 0) {
                    if (p.x() < x.p.x()) {
//                        RectHV area1 = new RectHV(x.rect.xmin(), x.rect.ymin(), x.p.x(), x.rect.ymax());
                        area = new RectHV( area.xmin(),area.ymin(), x.p.x(), area.ymax());
                        x.lb = put(x.lb, p, 1, area);
                    }
                    else if (p.x() >= x.p.x()) {
//                        RectHV area1 = new RectHV(x.p.x(), x.rect.ymin(), x.rect.xmax(), x.rect.ymax());
                        area = new RectHV( x.p.x(), area.ymin(), area.xmax(), area.ymax());
                        x.rt = put(x.rt, p, 1, area);
                    }
            }    
            else if (level == 1) {
                if (p.y() < x.p.y()) {
//                    RectHV area1 = new RectHV(x.rect.xmin(), x.rect.ymin(), x.rect.xmax(), x.p.y());
                    area = new RectHV( area.xmin(), area.ymin(), area.xmax(), x.p.y());
                    x.lb = put(x.lb, p, 0, area);
                }
                else if (p.y() >= x.p.y()) {
//                    RectHV area1 = new RectHV(x.rect.xmin(),  x.p.y(), x.rect.xmax(),x.rect.ymax());
                    area = new RectHV( area.xmin(), x.p.y(), area.xmax(), area.ymax());
                    x.rt = put(x.rt, p, 0, area);
                }
            }
      }
      
      return x;
    }    
    
    public void insert(Point2D p) {
        if (p == null) throw new NoSuchElementException();
        RectHV area = new RectHV(0.0, 0.0, 1.0, 1.0);
        if (!get(root, p, 0)) {
            root = put(root, p, 0, area); 
        }
//        StdOut.println(this.size());
    }

    private boolean get(Node x, Point2D p, int level) {
        boolean result = true;
        
        if (x == null) {
            result = false;
        }
        else {
            if( level == 0) {
                if (p.x() < x.p.x()) {
                    result = get(x.lb, p, 1);
                }
                else if (p.x() > x.p.x()) {
                    result = get(x.rt, p, 1);
                }
                else if (x.p.x() == p.x()) {
                        if (x.p.y() == p.y()) {
                            result = true;
                        }
                        else {
                            result = get(x.rt, p, 1);
                        }
                }
            }    
            else if (level == 1) {
                if (x.p.y() > p.y()) {
                    result = get(x.lb, p, 0);
                }
                else if (x.p.y() < p.y()) {
                    result = get(x.rt, p, 0);
                }
                else if (x.p.y() == p.y()) {
                        if (x.p.x() == p.x()) {
                            result = true;
                        }
                        else {
                            result = get(x.rt, p, 0);
                        }
                }
            }
      }
      return result;
    } 
    
    public boolean contains(Point2D p) {
        
        boolean result = false;
        
        if (p == null) throw new NoSuchElementException();
        result = get(root, p, 0);
        
        return result;
    }
    
    // level order traversal
    private Iterable<Point2D> levelOrder() {
        Queue<Node> nodeQueue = new Queue<Node>();
        Queue<Point2D> pointQueue = new Queue<Point2D>();
        
        nodeQueue.enqueue(root);
        
        while (!nodeQueue.isEmpty()) {
            Node x = nodeQueue.dequeue();

            if (x == null) continue;
            pointQueue.enqueue(x.p); 
            nodeQueue.enqueue(x.lb);
            nodeQueue.enqueue(x.rt);
        }
        return pointQueue;
    }
    
    // level order traversal
    private Iterable<Node> nodeOrder() {
        Queue<Node> nodeQueue = new Queue<Node>();
        Queue<Node> resultQueue = new Queue<Node>();
        
        nodeQueue.enqueue(root);
        
        while (!nodeQueue.isEmpty()) {
            Node x = nodeQueue.dequeue();

            
            if (x == null) continue;
            resultQueue.enqueue(x); 
            nodeQueue.enqueue(x.lb);
            nodeQueue.enqueue(x.rt);
        }
        return resultQueue;
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

        for (Node node : this.nodeOrder()) {
//            StdOut.println(x.p);
            // draw all of the points
//            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.03);
            node.p.draw();
            
            if (node.level == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(.005);
                StdDraw.line(node.p.x(), node.rect.ymin(), node.p.x(), node.rect.ymax());
            }
            else if (node.level == 1) {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(.005);
                StdDraw.line(node.rect.xmin(), node.p.y(), node.rect.xmax(), node.p.y());
            }
        }
    }
    
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NoSuchElementException();
        
        Queue<Point2D> rangePointQueue = new Queue<Point2D>();
        Queue<Node> rangeNodeQueue = new Queue<Node>();
        rangeNodeQueue.enqueue(root);

        while (!rangeNodeQueue.isEmpty()) {
            
            Node x = rangeNodeQueue.dequeue();
            
            if (x == null) continue;
            
            if (x.rect.intersects(rect) == true){
                
                if (rect.contains(x.p)) {
                    rangePointQueue.enqueue(x.p); 
                }
                
                if (x.lb != null) {
                    rangeNodeQueue.enqueue(x.lb);
                }
                
                if (x.rt != null) {
                    rangeNodeQueue.enqueue(x.rt);
                }
              }
        }
        
        return rangePointQueue;
    }
    

    public Point2D nearest(Point2D p) {
      if (p == null) throw new NoSuchElementException();
      
      if (this.isEmpty()) {
          return null;
      }
      else {
          double minDistance = 10.0;
          double leftRectDistance = 10.0;
          double rightRectDistance = 10.0;
      
          //stack ? 
          Stack<Point2D> stack = new Stack<Point2D>();   
      
          Queue<Node> nearestNodeQueue = new Queue<Node>();
          nearestNodeQueue.enqueue(root);

          while (!nearestNodeQueue.isEmpty()) {
              Node x = nearestNodeQueue.dequeue();
              if (x == null) continue;

              if (x.rect.distanceTo(p) <= minDistance )
              {
                  if ( x.p.distanceTo(p) <= minDistance ) {
                      minDistance = x.p.distanceTo(p);
//                      System.out.printf("nearest node %s, minDistance %f: \n", p.toString(), minDistance);
                      stack.push(x.p); 
                  }
                  
                  if (x.lb != null) {
                      leftRectDistance  = x.lb.rect.distanceTo(p);
                  }    
                  if (x.rt != null) {
                      rightRectDistance  = x.rt.rect.distanceTo(p);
                  } 
                  
                  if (leftRectDistance <= rightRectDistance) {
                      if (leftRectDistance <= minDistance) {
                          nearestNodeQueue.enqueue(x.lb);
                      }
                      if (rightRectDistance <= minDistance) {
                          nearestNodeQueue.enqueue(x.rt);
                      }
                  }
                  else { //rightRectDistance <= leftRectDistance
                      if (rightRectDistance <= minDistance) {
                          nearestNodeQueue.enqueue(x.rt);
                      }
                      if (leftRectDistance <= minDistance) {
                          nearestNodeQueue.enqueue(x.lb);
                      }
                  }   
              }
          }//while
      
          if ( minDistance < 10.0 ) {
              Point2D nearest = stack.pop();
              return nearest;
          }
          else return null;
      }
    }
}
