package org.example.module1;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/3 23:48
 **/
public class WeightedQuickUnionUF {

    private int[] id;

    private int[] sz;

    private int count;

    public WeightedQuickUnionUF(int N) {

        count = N;
        id = new int[N];
        sz = new int[N];

        for (int i = 0; i < N; i++) {
            id[i] = i;
            sz[i] = 1;
        }
    }

    public int find(int p) {

        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (qRoot == pRoot) {
            return;
        }

        if (sz[pRoot] > sz[qRoot]) {
            id[qRoot] = pRoot;
            sz[pRoot] = sz[pRoot] + sz[qRoot];
        } else {
            id[pRoot] = qRoot;
            sz[qRoot] = sz[pRoot] + sz[qRoot];
        }
    }
}
