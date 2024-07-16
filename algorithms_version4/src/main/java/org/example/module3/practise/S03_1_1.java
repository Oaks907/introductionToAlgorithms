package org.example.module3.practise;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import org.example.module3.ST;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/6/18 21:35
 **/
public class S03_1_1 {

    public static void main(String[] args) {
        ST<String, Double> grades = new ST<String, Double>();
        grades.put("A",  4.00);
        grades.put("B",  3.00);
        grades.put("C",  2.00);
        grades.put("D",  1.00);
        grades.put("F",  0.00);
        grades.put("A+", 4.33);
        grades.put("B+", 3.33);
        grades.put("C+", 2.33);
        grades.put("A-", 3.67);
        grades.put("B-", 2.67);


        // read grades from standard input and compute gpa
        int n = 0;
        double total = 0.0;
        for (n = 0; !StdIn.isEmpty(); n++) {
            String grade = StdIn.readString();
            double value = grades.get(grade);
            total += value;
        }
        double gpa = total / n;
        StdOut.println("GPA = " + gpa);
    }
}
