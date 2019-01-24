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

public class BruteCollinearPoints {
    private Node first;  // First node
    private Node last;  // last node;
    private int qsize;       // size of queue;

    private class Node
    {
        LineSegment item;         // Data item
        Node next;         // next node
    }

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points)
    {
        if (points == null)
            throw new IllegalArgumentException();

        first = null;
        last = null;
        qsize = 0;

        // this.points = points;
        int size = points.length;
        int c1, c2;
        Comparator<Point> c;

        // sort points
        Arrays.sort(points);

        for (int i = 0; i < size-3; i++)
        {
            for (int j = i+1; j < size-2; j++)
            {
                for (int k = j+1; k < size-1; k++)
                {
                    for (int kk = k+1; kk < size; kk++)
                    {
                        if (points[i] == null || points[j] == null || points[k] == null || points[kk] == null)
                            throw new IllegalArgumentException();

                        c = points[i].slopeOrder();
                        c1 = c.compare(points[j], points[k]);
                        c2 = c.compare(points[j], points[kk]);

                        if(c1 == 0 && c2 == 0)
                        {
                            add(new LineSegment(points[i], points[kk]));
                            // System.out.println("i:"+i+" j:"+j+" k:"+k+" kk:"+kk);
                        }

                    }
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
            return null;

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

        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
