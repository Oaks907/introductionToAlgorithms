import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/9 10:55
 **/
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] arr; // 8bit

    private Integer size; // 4 bit

    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        arr = (Item[]) new Object[1];
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
            throw new IllegalArgumentException("the item is null!");
        }
        arr[size++] = item;

        if (size == arr.length) {
            arr = resize(arr, size * 2);
        }
    }

    private Item[] resize(Item[] oldArr, int length) {
        Item[] newArr = (Item[]) new Object[length];
        for (int i = 0; i < size; i++) {
            newArr[i] = oldArr[i];
        }
        return newArr;
    }


    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("the RandomizeQueue is empty!");
        }

        int uniform = StdRandom.uniform(0, size);
        size--;
        Item temp = arr[uniform];
        arr[uniform] = arr[size];
        arr[size] = null;

        if (size * 4 < arr.length) {
            arr = resize(arr, arr.length / 2);
        }

        return temp;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("the RandomizeQueue is empty!");
        }

        int uniform = StdRandom.uniform(0, size);
        return arr[uniform];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator<>();
    }

    private class ListIterator<Item> implements Iterator<Item> {
        private int n; // 记录遍历次数

        private Item[] iteratorArr;


        public ListIterator() {
            n = size;
            iteratorArr = (Item[]) new Object[size];
            for (int i = 0; i < size; i++) {
                iteratorArr[i] = (Item) arr[i];
            }
        }

        public boolean hasNext() {
            return n > 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (n == 0) {
                throw new NoSuchElementException("there are no more items!");
            }
            int uniform = StdRandom.uniform(0, n);
            n--;
            Item temp = iteratorArr[uniform];
            iteratorArr[uniform] = iteratorArr[n];
            iteratorArr[n] = temp;
            return temp;
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
            System.out.print(queue.dequeue() + " ");
        }
        System.out.println();

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
        System.out.println();
    }
}
