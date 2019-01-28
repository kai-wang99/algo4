/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private Node first;  // First node
    private Node last;  // last node;
    private int qsize;       // size of queue;

    private class Node
    {
        LineSegment item;         // Data item
        Node next;         // next node
    }

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points)
    {
        if (points == null)
            throw new IllegalArgumentException();

        first = null;
        last = null;
        qsize = 0;

        // this.points = points;
        int size = points.length;
        if (size < 4) return;

        // sort points
        Arrays.sort(points);
        Comparator<Point> c;

        Point[] pt;

        for (int i = 0; i < size-3; i++)
        {
            pt = points.clone();
            Arrays.sort(pt, i, size, pt[i].slopeOrder());
            /* for (int k = 0; k < size; k++)
            {
                System.out.println(pt[k]);
            } */

            int fidx = i+1;
            int lidx;
            double fslope = pt[i].slopeTo(pt[fidx]);
            double slope;

            for (int j = i+1; j < size; j++)
            {
                lidx = j;
                slope = pt[i].slopeTo(pt[lidx]);
                // System.out.println("i:"+i+" j:"+j + " First:" + fidx + " Last:" + lidx + " Fslope:" + fslope + " slope:" + slope);

                if (slope != fslope || j == size-1)
                {
                    // find the segment
                    if (slope != fslope )    lidx--;

                    if((lidx - fidx) >= 2) {
                        add(new LineSegment(pt[i], pt[lidx]));
                        // System.out.println(
                        //        "Add segment: First:" + i + " Last:" + lidx + " Fslope:" + fslope);
                    }

                    fidx = j;
                    fslope = slope;
                }
            }
        }
    }
    // the number of line segments
    public int numberOfSegments()
    {
        return qsize;
    }
    // the line segments
    public LineSegment[] segments()
    {
        LineSegment[] result;
        Node node;

        if (numberOfSegments() <= 0)
            return new LineSegment[0];

        node = first;
        result = new LineSegment[numberOfSegments()];
        for (int i = 0; i < result.length; i++)
        {
            if (node != null && node.item != null)
            {
                result[i] = node.item;
                node = node.next;
            }
            else
            {
                // something wrong
                throw new IllegalArgumentException("Null node or node item!");
            }
        }
        return result;
    }

    private void add(LineSegment item)
    {
        Node node = new Node();

        if (item == null)
            throw new IllegalArgumentException();

        node.item = item;
        node.next = null;

        if (first == null && last == null)
        {
            // first item
            first = node;
            last = node;
        }
        else
        {
            // add to last
            last.next = node;
            last = node;
        }

        qsize++;
    }

    public static void main(String[] args)
    {
        if (args.length != 1)
        {
            System.out.println("Usage: BruteCollinearPoints file");
            return;
        }

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            // System.out.println("Point: " + i + " X:" + x + " Y:" + y);
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
