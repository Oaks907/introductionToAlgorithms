import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/8 23:19
 **/
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Node<Item> first;

    private Node<Item> last;

    private int size;

    private class Node<Item> {


        public Item item;

        public Node<Item> pre;

        public Node<Item> next;

        public Node(Item item) {
            this.item = item;
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        first = null;
        last = null;
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (null == item) {
            throw new IllegalArgumentException();
        }
        Node<Item> node = new Node<>(item);
        if (null == last) {
            first = node;
            last = node;
        } else {
            node.pre = last;
            last.next = node;
            last = node;
        }

        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (first ==  last) {
            Item item = first.item;
            first = null;
            last = null;
            size--;
            return item;
        }
        int uniformIndex = StdRandom.uniform(0, size);
//        System.out.println("SIZE:" + size + " index:" + uniformIndex);
        Iterator<Item> iterator = iterator();
        Node<Item> current = first;
        while (iterator.hasNext()) {
            Item next = iterator.next();
            if (uniformIndex-- == 0) {
                remove(current);
                return next;
            }
            current = current.next;
        }
        return null;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int uniformIndex = StdRandom.uniform(0, size);
        Iterator<Item> iterator = iterator();
        while (iterator.hasNext()) {
            Item next = iterator.next();
            uniformIndex--;
            if (uniformIndex == 0) {
                return next;
            }
        }
        return null;
    }

    /**
     *
     * @param current
     * @return 入参节点的下一个节点
     */
    private void remove(Node<Item> current) {
        if (first.equals(current)) {
            first = first.next;
            if (null != first) {
                first.pre = null;
            }
        } else if (last.equals(current)) {
            last = last.pre;
            if (null != last) {
                last.next = null;
            }
        } else {
            Node<Item> next = current.next;
            Node<Item> pre = current.pre;
            pre.next = next;
            next.pre = pre;
        }
        size--;
        current.item = null;
        current.pre = null;
        current.next = null;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

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

    // unit testing (required)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        printArr(queue);

        for (int i = 0; i < n; i++) {
            System.out.println(queue.dequeue());
        }
        printArr(queue);

        while (!queue.isEmpty()) {
            System.out.println(queue.dequeue());
        }
        printArr(queue);

        StdOut.println("current size:" + queue.size());
    }

    private static void printArr(RandomizedQueue<String> queue) {
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.print(" " + next);
        }
    }

}
