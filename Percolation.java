// Programming Assignment 1: Percolation
// http://coursera.cs.princeton.edu/algs4/assignments/percolation.html

// import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int demision;                     // private memember for demision
    private boolean[] openStatus;             // private member , if a site is open
    private final WeightedQuickUnionUF unionFind;          // private member , connection status
    private final WeightedQuickUnionUF unionFindFull;      // private member , full status
    private int opensites;                                 // number of open sites

    // create n-by-n grid, with all sites blocked
    public Percolation(int n)
    {
        if (n <= 0)
            throw new IllegalArgumentException();

        demision = n;
        openStatus = new boolean [n*n+2];
        unionFind = new WeightedQuickUnionUF(n*n+2);
        unionFindFull = new WeightedQuickUnionUF(n*n+1);
        opensites = 0;

        int i, j;
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < n; j++)
            {
                openStatus[i*n+j] = false;
            }
        }
        openStatus[n*n] = true;    // This node connect to all notes in first row
        openStatus[n*n+1] = true;  // This node connect to all notes in last row
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (isOpen(row, col))
            return;

        openStatus[(row-1)*demision+col-1] = true;
        opensites++;

        // Connect this nodes to all open adjacent nodes
        if (row == 1)
        {
            // connect sites in first row to site (n*n)
            unionFind.union((row-1)*demision+col-1, demision*demision);
            unionFindFull.union((row-1)*demision+col-1, demision*demision);
        }

        if (row == demision)
        {
            // connect sites in last row to site (n*n+1)
            unionFind.union((row-1)*demision+col-1, demision*demision+1);
        }

        if (row > 1)
            if (isOpen(row-1, col))
            {
                unionFind.union((row-1)*demision+col-1, (row-2)*demision+col-1);
                unionFindFull.union((row-1)*demision+col-1, (row-2)*demision+col-1);
            }

        if (row < demision)
            if (isOpen(row+1, col))
            {
                unionFind.union((row-1)*demision+col-1, row*demision+col-1);
                unionFindFull.union((row-1)*demision+col-1, row*demision+col-1);
            }

        if (col > 1)
            if (isOpen(row, col-1))
            {
                unionFind.union((row-1)*demision+col-1, (row-1)*demision+col-2);
                unionFindFull.union((row-1)*demision+col-1, (row-1)*demision+col-2);
            }

        if (col < demision)
            if (isOpen(row, col+1))
            {
                unionFind.union((row-1)*demision+col-1, (row-1)*demision+col);
                unionFindFull.union((row-1)*demision+col-1, (row-1)*demision+col);
            }

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        checkrowcol(row, col);
        return (openStatus[(row-1)*demision+col-1]);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col)
    {
        checkrowcol(row, col);
        if (!isOpen(row, col))
            return false;
        else
            return unionFindFull.connected((row-1)*demision+col-1, demision*demision);
    }

    // number of open sites
    public int numberOfOpenSites()
    {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates()
    {
        /* boolean result = false;
        int i;

        for (i = 1; i <= demision; i++)
        {
            if (isFull(demision, i))
            {
                result = true;
                break;
            }
        }
        return result; */
        return unionFind.connected(demision*demision+1, demision*demision);
    }

    // check valid of inputs
    private void checkrowcol(int row, int col)
    {
        if (row > 0 && row <= demision && col > 0 && col <= demision)
            return;
        else
            throw new IllegalArgumentException();
    }

    public static void main(String[] args)   // test client (optional)
    {
        System.out.println("Hello World");
    }
}
