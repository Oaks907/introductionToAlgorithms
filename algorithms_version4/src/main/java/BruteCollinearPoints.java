import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/16 0:18
 **/
public class BruteCollinearPoints {

    private Point[] points;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {
        if (null == points) {
            throw new IllegalArgumentException("points is null");
        }
        this.points = points;
        Arrays.sort(this.points);
        checkPoint();
        calculateLineSegment();
    }

    private void checkPoint() {
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

    private void calculateLineSegment() {
        ArrayList<LineSegment> list = new ArrayList<>();

        int len = points.length;
        for (int i = 0; i < len - 3; i++) {
            Point first = points[i];
            for (int j = i + 1; j < len - 2; j++) {
                double v = first.slopeTo(points[j]);
                for (int k = j + 1; k < len - 1; k++) {
                    double fk = first.slopeTo(points[k]);
                    if (fk != v) {
                        continue;
                    }
                    for (int l = k + 1; l < len; l++) {
                        if (v == first.slopeTo(points[l])) {
                            LineSegment lineSegment = new LineSegment(first, points[l]);
                            list.add(lineSegment);
                        }
                    }
                }
            }
        }
        this.lineSegments = list.toArray(new LineSegment[list.size()]);
    }

    public static void main(String[] args) throws FileNotFoundException {


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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
