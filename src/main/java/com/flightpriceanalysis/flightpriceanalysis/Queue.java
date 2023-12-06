package com.flightpriceanalysis.flightpriceanalysis;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *>> The <tt>Queue</tt> class represents a FIFO
 *>> queue of generic items.
 *>> It supports the usual <em>enqueue</em> and <em>dequeue</em>
 *>> operations, along with methods for peeking at the first item,
 *>> testing if the queue is empty, and iterating through
 *>> the items in FIFO order.
 *>> <p>
 *>> This implementation uses a singly-linked list with a static nested class for
 *>> linked-list nodes.for the version from the
 *>> textbook that uses a non-static nested class.
 *>> The <em>enqueue</em>, <em>dequeue</em>, <em>peek</em>, <em>ht_size</em>, and <em>isEmpty</em>
 *>> operations all take constant time in the worst case.
 */
public class Queue<Item> implements Iterable<Item> {
    private int ht_N;               // num of elements--queue
    private Node<Item> ht_first;    // beginning---queue
    private Node<Item> ht_last;     // end-of-queue

    // helper linked list-class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
    }

    /**
     * Init an empty--queue.
     */
    public Queue() {
        ht_first = null;
        ht_last = null;
        ht_N = 0;
    }

    public boolean isEmpty() {
        return ht_first == null;
    }

    /**
     * Returns the num-of-items in this queue.
     *
     * @return the num-of-items in this queue
     */
    public int size() {
        return ht_N;
    }


    public Item peek() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        return ht_first.item;
    }

    /**
     * Adds the item-to this queue.
     *
     * @param item the item to add
     */
    public void enqueue(Item item) {
        Node<Item> oldlast = ht_last;
        ht_last = new Node<Item>();
        ht_last.item = item;
        ht_last.next = null;
        if (isEmpty()) ht_first = ht_last;
        else oldlast.next = ht_last;
        ht_N++;
    }

    /**
     * Removes and returns the item on this queue that was least-recently added.
     *
     * @return the item on this queue that was least-recently added
     * @throws java.util.NoSuchElementException if this queue empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = ht_first.item;
        ht_first = ht_first.next;
        ht_N--;
        if (isEmpty()) ht_last = null;   // to avoid loitering
        return item;
    }

    /**
     * Returns a string-repr of this queue.
     *
     * @return the seq-of-items in FIFO order, sep-by-spaces
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this)
            s.append(item).append(" ");
        return s.toString();
    }

    /**
     * Returns an itr that iterates over the items in this queue in FIFO order.
     *
     * @return an itr that iterates over the items in this queue
     */
    public Iterator<Item> iterator() {
        return new ListIterator<Item>(ht_first);
    }

    // an itr, doesn't implement remove() --optional
    @SuppressWarnings("hiding")
    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
}