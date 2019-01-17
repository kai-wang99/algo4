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

public class Deque<Item> implements Iterable<Item> {
    private final Node first;  // First node
    private final Node last;  // last node;
    private int size = 0;       // size of queue;

    private class Node
    {
        Item item;         // Data item
        Node previous;     // previouse node
        Node next;         // next node
    }

    // construct an empty deque
    public Deque()
    {
        first = new Node();
        last = new Node();

        first.next = last;
        first.previous = null;

        last.previous = first;
        last.next = null;
    }
    // is the deque empty?
    public boolean isEmpty()
    {
        return (first.next.equals(last) && last.previous.equals(first));
    }
    // return the number of items on the deque
    public int size()
    {
        return size;
    }
    // add the item to the front
    public void addFirst(Item item)
    {
        Node node = new Node();

        if (item == null)
            throw new IllegalArgumentException();

        Node next = first.next;
        next.previous = node;

        node.item = item;
        node.next = next;
        node.previous = first;

        first.next = node;

        size++;
    }
    // add the item to the end
    public void addLast(Item item)
    {
        Node node = new Node();

        if (item == null)
            throw new IllegalArgumentException();

        Node previous = last.previous;
        previous.next = node;

        node.item = item;
        node.next = last;
        node.previous = previous;

        last.previous = node;

        size++;
    }
    // remove and return the item from the front
    public Item removeFirst()
    {
        if (size <= 0)
            throw new NoSuchElementException();

        Node node = first.next;

        node.next.previous = first;
        first.next = node.next;

        size--;

        return node.item;
    }
    // remove and return the item from the end
    public Item removeLast()
    {
        if (size <= 0)
            throw new NoSuchElementException();

        Node node = last.previous;

        node.previous.next = last;
        last.previous = node.previous;

        size--;

        return node.item;
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first.next; // ref to current item

        public boolean hasNext()
        {
            return !current.equals(last);
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if (current.equals(last)) throw new NoSuchElementException();

            Item item = current.item;
            current   = current.next;
            return item;
        }
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args)
    {
        System.out.println("Hellow world");

        Deque<Integer> dequei = new Deque<Integer>();
        dequei.addFirst(2);
        dequei.addFirst(1);
        dequei.addLast(3);
        dequei.addLast(4);

        System.out.println("Len of dequei " + dequei.size());
        for (Integer i : dequei)
            System.out.println(i);

    }
}
