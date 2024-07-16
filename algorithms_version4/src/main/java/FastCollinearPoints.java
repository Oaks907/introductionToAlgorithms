import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/16 14:27
 **/
public class FastCollinearPoints {

    private Point[] pointArray;
    private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        this.pointArray = points.clone();
        Arrays.sort(this.pointArray);
        checkPoint(pointArray);

        ArrayList<LineSegment> list = new ArrayList<>();
        for (Point origin : this.pointArray) {
            Point[] clone = this.pointArray.clone();
            Arrays.sort(clone, origin.slopeOrder());
            calculateLineSegment(origin, clone, list);
        }
        this.lineSegments = list.toArray(new LineSegment[list.size()]);
    }

    private void checkPoint(Point[] points) {
        if (null == points) {
            throw new IllegalArgumentException("points is null");
        }

        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            if (null == point) {
                throw new IllegalArgumentException("point is null");
            }
            if (i > 0 && point.compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("two point is equal");
            }
        }
    }

    public int numberOfSegments() {
        return lineSegments.length;
    }

    public LineSegment[] segments() {
        return lineSegments.clone();
    }

    private void calculateLineSegment(Point origin, Point[] clone, ArrayList<LineSegment> list) {
        double slop = origin.slopeTo(clone[0]); // clone[0] is origin

        int count = 1;
        int left = 1;
        for (int i = 1; i < clone.length; i++) {
            double nextSlop = origin.slopeTo(clone[i]);
            if (slop == nextSlop) {
                count++;
            }
            if (slop != nextSlop || i == clone.length - 1) {
                if (count > 3 && origin.compareTo(clone[left]) < 0) {
                    list.add(new LineSegment(origin, clone[i - 1]));
                }

                slop = nextSlop;
                count = 2;
                left = i;
            }
        }
    }

    public static void main(String[] args) {


        // read the n points from a file
        In in = new In();
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
