import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/1 23:39
 **/
public class RandomWord {

    public static void main(String[] args) {

        String readIn;
        String result = "";
        int i = 0;
        while (!StdIn.isEmpty()) {
            readIn = StdIn.readString();
            i++;
            if (StdRandom.bernoulli(1.0 / i)) {
                result = readIn;
            }
        }

        StdOut.println(result);
    }
}
