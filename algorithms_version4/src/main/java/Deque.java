import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/8 21:26
 **/
public class Deque<Item> implements Iterable<Item> {

    private Node<Item> first;

    private Node<Item> last;

    private int size;

    public class Node<Item> {

        public Node(Item item) {
            this.item = item;
        }

        public Item item;

        public Node<Item> pre;

        public Node<Item> next;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (null == item) {
            throw new IllegalArgumentException();
        }
        Node<Item> node = new Node<>(item);
        if (null == first) {
            first = node;
            last = node;
        } else {
            first.pre = node;
            node.next = first;
            first = node;
        }

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
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

    // remove and return the item from the front
    public Item removeFirst() {

        if (first == null) {
            throw new NoSuchElementException();
        }
        Item result = first.item;

        first.item = null;
        first = first.next;
        if (null != first) {
            first.pre = null;
        }
        size--;

        return result;

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Item result = last.item;

        last.item = null;
        last = last.pre;
        if (null != last) {
            last.next = null;
        }
        size--;
        return result;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator<>(first);
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private Node<Item> current;

        public ListIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> queue = new Deque<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.addFirst(item);
            printArr(queue);
        }
        StdOut.println("(" + queue.size() + " left on queue)");

        queue.removeFirst();
        queue.removeFirst();
        printArr(queue);

        queue.removeLast();
        queue.removeLast();
        printArr(queue);

        while(!queue.isEmpty()) {
            queue.removeLast();
        }
        StdOut.println("(" + queue.size() + " left on queue)");
        printArr(queue);
    }

    private static void printArr(Deque<String> queue) {
        Iterator<String> iterator = queue.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            System.out.print(" " + next);
        }
        System.out.println();
    }
}
