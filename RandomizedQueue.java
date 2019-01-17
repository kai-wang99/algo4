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

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] s;        // list of items
    private int size = 0;    // size of queue

    // construct an empty randomized queue
    public RandomizedQueue()
    {
        s = (Item[]) new Object[1];
    }
    // is the randomized queue empty?
    public boolean isEmpty()
    {
        return (size == 0);
    }
    // return the number of items on the randomized queue
    public int size()
    {
        return size;
    }
    // add the item
    public void enqueue(Item item)
    {
        if (item == null)
            throw new IllegalArgumentException();

        if (size == s.length) resize(2 * s.length);
        s[size++] = item;
    }
    // remove and return a random item
    public Item dequeue()
    {
        if (size <= 0)
            throw new NoSuchElementException();

        int idx = StdRandom.uniform(size);
        Item item = s[idx];
        s[idx] = s[--size];
        s[size] = null;
        if (size > 0 && size == s.length/4) resize(s.length/2);
        return item;
    }
    // return a random item (but do not remove it)
    public Item sample()
    {
        if (size <= 0)
            throw new NoSuchElementException();

        int idx = StdRandom.uniform(size);
        return s[idx];
    }

    private class RdmQueueIterator implements Iterator<Item>
    {
        private int current = 0; // ref to current item
        private final int [] idx;      // random idx into queue

        // constructor
        public RdmQueueIterator()
        {
            idx = new int[size];
            for (int i = 0; i < size; i++) idx[i] = i;
            StdRandom.shuffle(idx);
        }
        public boolean hasNext()
        {
            return (current < idx.length);
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (current >= idx.length) throw new NoSuchElementException();

            Item item = s[idx[current++]];
            return item;
        }
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()
    {
        return new RdmQueueIterator();
    }

    // resize the queue
    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++)
            copy[i] = s[i];
        s = copy;
    }

    // unit testing (optional)
    public static void main(String[] args)
    {
        System.out.println("Hellow world");

        RandomizedQueue<Integer> rque = new RandomizedQueue<Integer>();

        for (int i = 0; i < 10; i++) rque.enqueue(i);

        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);
        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);
        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);

        rque.dequeue();
        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);

        rque.dequeue();
        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);

        rque.dequeue();
        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);

        rque.dequeue();
        System.out.println("Len of rque " + rque.size());
        for (Integer i : rque)
            System.out.println(i);

    }
}
