import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/4 7:38
 **/
public class Percolation {


    private final WeightedQuickUnionUF weightedQuickUnionUF;

    // record site status. true means open
    private final boolean[] status;

    private int openCount;

    private final int n;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        openCount = 0;
        int size = n * n + 1; // 行，列的取值范围都是 1 - n
        weightedQuickUnionUF = new WeightedQuickUnionUF(size);
        status = new boolean[size];
    }

    private void validate(int p) {
        if (p < 1 || p > n) {
            throw new IllegalArgumentException("index " + p + " is not between 1 and " + n);
        }
    }

    private boolean isInGrid(int i, int j) {
        return (i >= 1 && i <= n) && (j >= 1 && j <= n);
    }

    /**
     * get index by row and col.
     *
     * @param row value between 1 and N
     * @param col value between 1 and N
     * @return
     */
    private int getIndex(int row, int col) {
        return (row - 1) * n + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row);
        validate(col);

        if (isOpen(row, col)) {
            return;
        }

        int index = getIndex(row, col);
        status[index] = true;

        int up = index - n;
        if (isInGrid(row - 1, col) && status[up]) {
            weightedQuickUnionUF.union(index, up);
        }
        int bottom = index + n;
        if (isInGrid(row + 1, col) && status[bottom]) {
            weightedQuickUnionUF.union(index, bottom);
        }
        int left = index - 1;
        if (isInGrid(row, col - 1) && status[left]) {
            weightedQuickUnionUF.union(index, left);
        }
        int right = index + 1;
        if (isInGrid(row, col + 1) && status[right]) {
            weightedQuickUnionUF.union(index, right);
        }
        openCount++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return status[getIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        validate(row);
        validate(col);

        if (!isOpen(row, col)) {
            return false;
        }

        int index = getIndex(row, col);
        for (int i = 1; i <= n; i++) {
            if (status[i] && (weightedQuickUnionUF.find(index) == weightedQuickUnionUF.find(i))) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        int row = n;
        for (int col = 1; col <= n; col++) {
            if (isFull(row, col)) {
                return true;
            }
        }
        return false;
    }

}
