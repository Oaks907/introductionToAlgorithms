package org.example.c1;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/3 23:26
 **/
public class QuickUnionUF {

    private int[] ids;

    public QuickUnionUF(int N) {
        ids = new int[N];
        for (int i = 0; i < N; i++) {
            ids[i] = i;
        }
    }

    private int find(int p) {

        while (p != ids[p]) {
            p = ids[p];
        }

        return p;
    }

    private void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

       if (qRoot == qRoot) {
           return;
       }

       ids[pRoot] = qRoot;
    }
}
