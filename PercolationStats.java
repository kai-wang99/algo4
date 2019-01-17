//  Test programe
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;        // const value for confident 95
    private final int  trinum;                       // trials number
    private final double mean;                       // mean
    private final double stddev;                     // stddev

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials)
    {
        Percolation percoTest; // The test object
        double[]  thrlist;     // list of threshhold for each run
        int i, j, idx, idy;

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        thrlist = new double[trials];
        trinum = trials;

        for (i = 0; i < trials; i++)
        {
            percoTest = new Percolation(n);
            for (j = 0; j < n*n; j++)
            {
                do {
                    idx = StdRandom.uniform(n);
                    idy = StdRandom.uniform(n);
                } while (percoTest.isOpen(idx+1, idy+1));

                percoTest.open(idx+1, idy+1);
                if (percoTest.percolates())
                    break;
                
            }
            thrlist[i] = (double) (j+1)/(n*n);
            // System.out.println("i = " + i + " Thr[j] = " + thrlist[i]);
        }

        mean = StdStats.mean(thrlist);
        stddev = StdStats.stddev(thrlist);

    }

    // sample mean of percolation threshold
    public double mean()
    {
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev()
    {
        return stddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean-CONFIDENCE_95*stddev/Math.sqrt(trinum);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean+CONFIDENCE_95*stddev/Math.sqrt(trinum);
    }

    // test client (described below)
    public static void main(String[] args) {
        PercolationStats test;
        Stopwatch watch;

        int n, t;

        if (args.length != 2)
        {
            System.out.println("Usage: PercolcationStat n T");
            return;
        }

        n = Integer.parseInt(args[0]);
        t = Integer.parseInt(args[1]);


        watch = new Stopwatch();
        test =  new PercolationStats(n, t);
        System.out.println("Time used (s):  " + watch.elapsedTime());

        System.out.println("mean " + test.mean());
        System.out.println("stddev " + test.stddev());
        System.out.println("conf low " + test.confidenceLo());
        System.out.println("conf hight " + test.confidenceHi());
    }
}
