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

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class Permutation {
    public static void main(String[] args) {
        int k;
        RandomizedQueue<String> rque = new RandomizedQueue<String>();

        if (args.length != 1)
        {
            System.out.println("Usage: Permutation k");
            return;
        }

        k = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            // StdOut.println(value);
            rque.enqueue(value);
        }

        for (int i = 0; i < k; i++)
        {
            String value = rque.dequeue();
            StdOut.println(value);
        }
    }
}
