/*************************************************************************
 *  Compilation:  javac KdTreeVisualizer.java
 *  Execution:    java KdTreeVisualizer
 *  Dependencies: StdDraw.java Point2D.java KdTree.java
 *
 *  Add the points that the user clicks in the standard draw window
 *  to a kd-tree and draw the resulting kd-tree.
 *
 *************************************************************************/

public class KdTreeVisualizer {

    public static void main(String[] args) {
        StdDraw.show(0);

        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0.0, 0.0, 0.0, 1.0);
        StdDraw.line(0.0, 0.0, 1.0, 0.0);
        StdDraw.line(1.0, 0.0, 1.0, 1.0);
        StdDraw.line(0.0, 1.0, 1.0, 1.0);
        
        KdTree kdtree = new KdTree();
//        PointSET brute = new PointSET();
        while (true) {
            if (StdDraw.mousePressed()) {

                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
//                System.out.printf("Visualizer: %8.6f %8.6f\n", x, y);
                Point2D p = new Point2D(x, y);
                kdtree.insert(p);
//                brute.insert(p);
//                StdDraw.clear();
                kdtree.draw();
//                brute.draw();
//                StdOut.println(p);
            }
            StdDraw.show(50);
        }

    }
}
