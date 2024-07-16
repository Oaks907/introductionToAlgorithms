import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/16 0:18
 **/
public class BruteCollinearPoints {

    private Point[] pointsArray;
    private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) {

        this.pointsArray = points.clone();
        checkPoint(pointsArray);
        Arrays.sort(this.pointsArray);
        calculateLineSegment();
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

    private void calculateLineSegment() {
        ArrayList<LineSegment> list = new ArrayList<>();

        int len = pointsArray.length;
        for (int i = 0; i < len - 3; i++) {
            Point first = pointsArray[i];
            for (int j = i + 1; j < len - 2; j++) {
                double v = first.slopeTo(pointsArray[j]);
                for (int m = j + 1; m < len - 1; m++) {
                    double fk = first.slopeTo(pointsArray[m]);
                    if (fk != v) {
                        continue;
                    }
                    for (int n = m + 1; n < len; n++) {
                        if (v == first.slopeTo(pointsArray[n])) {
                            LineSegment lineSegment = new LineSegment(first, pointsArray[n]);
                            list.add(lineSegment);
                        }
                    }
                }
            }
        }
        this.lineSegments = list.toArray(new LineSegment[list.size()]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
