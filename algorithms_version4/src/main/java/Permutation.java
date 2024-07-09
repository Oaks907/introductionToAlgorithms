import edu.princeton.cs.algs4.StdIn;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/9 0:04
 **/
public class Permutation {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        for (int i = 0; i < n; i++) {
            System.out.println(queue.dequeue());
        }
    }
}
