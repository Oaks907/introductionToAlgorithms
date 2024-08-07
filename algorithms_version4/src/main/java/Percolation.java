import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * @Description
 * @Author Administrator
 * @Date 2024/7/4 7:38
 **/
public class Percolation {


    private final WeightedQuickUnionUF weightedQuickUnionUF;

    // record site status. true means open
    private final byte[][] status;

    private int openCount;

    private final int n;

    private static final byte OPEN = 1;

    private static final byte PERCOLATION = 2;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        this.n = n;
        openCount = 0;
        int size = n * n + 1; // 行，列的取值范围都是 1 - n
        weightedQuickUnionUF = new WeightedQuickUnionUF(size);
        status = new byte[n + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                status[i][j] = 0;
            }
        }
    }

    private void validate(int p) {
        if (p < 1 || p > n) {
            throw new IllegalArgumentException("index " + p + " is not between 1 and " + n);
        }
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

    private int getRowFromIndex(int index) {
        return index / n + ((index % n == 0) ? 0 : 1);
    }

    private int getColFromIndex(int index) {
        int i = index % n;
        return i == 0 ? n : i;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
//        System.out.println(row + " " + col);

        validate(row);
        validate(col);

        if (isOpen(row, col)) {
            return;
        }

        int index = getIndex(row, col);

        status[row][col] = OPEN;
        openCount++;

        if (row == 1) {
            weightedQuickUnionUF.union(index, 0);
        }
        if (row == n) {
            status[row][col] = PERCOLATION;
        }

        if (row > 1 && isOpen(row - 1, col)) {
            this.update(row, col, row - 1, col);
        }
        if (row < n && isOpen(row + 1, col)) {
            this.update(row, col, row + 1, col);
        }
        if (col > 1 && isOpen(row, col - 1)) {
            this.update(row, col, row, col - 1);
        }
        if (col < n && isOpen(row, col + 1)) {
            this.update(row, col, row, col + 1);
        }
    }

    private void update(int row, int col, int r, int c) {
//        System.out.println(row + " " + col + " " + r + " " + c + " " + n);
        int index = getIndex(row, col);
        int nearIndex = getIndex(r, c);
        int pRoot = weightedQuickUnionUF.find(index);
        int qRoot = weightedQuickUnionUF.find(nearIndex);
        weightedQuickUnionUF.union(pRoot, qRoot);

        int pRootRow = getRowFromIndex(pRoot);
        int pRootCol = getColFromIndex(pRoot);

        int qRootRow = getRowFromIndex(qRoot);
        int qRootCol = getColFromIndex(qRoot);

        if (status[pRootRow][pRootCol] == PERCOLATION || status[qRootRow][qRootCol] == PERCOLATION) {
            int newRoot = weightedQuickUnionUF.find(nearIndex);
            qRootRow = getRowFromIndex(newRoot);
            qRootCol = getColFromIndex(newRoot);
            if (qRoot == 0) {
                status[0][0] = 2;
            } else {
                status[qRootRow][qRootCol] = 2;
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row);
        validate(col);
        return status[row][col] > 0;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row);
        validate(col);

        if (!isOpen(row, col)) {
            return false;
        }

        return weightedQuickUnionUF.connected(0, getIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        int root = weightedQuickUnionUF.find(0);
//        for (int i = 0; i <= n; i++) {
//            for (int j = 0; j <= n; j++) {
//                System.out.print(status[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("-----");
        if (root == 0) {
            return status[0][0] == PERCOLATION;
        } else {
            return status[getRowFromIndex(root)][getColFromIndex(root)] == PERCOLATION;
        }
    }

    public static void main(String[] args) {
//        Percolation percolation = new Percolation(8);
//        percolation.open(1,3);
//        System.out.println(percolation.percolates());
//        percolation.open(2,6);
//        System.out.println(percolation.percolates());;
//        percolation.open(3,3);
//        System.out.println(percolation.percolates());;
//        percolation.open(4,6);
//        System.out.println(percolation.percolates());;
//        percolation.open(3,2);
//        System.out.println(percolation.percolates());;
//        percolation.open(5,6);
//        System.out.println(percolation.percolates());;
//        percolation.open(2,5);
//        System.out.println(percolation.percolates());;
//        percolation.open(7,5);
//        System.out.println(percolation.percolates());;
//        percolation.open(4,7);
//        System.out.println(percolation.percolates());;
//        percolation.open(3,1);
//        System.out.println(percolation.percolates());;
//        percolation.open(7,8);
//        System.out.println(percolation.percolates());;
//        percolation.open(2,7);
//        System.out.println(percolation.percolates());;
//        percolation.open(2,1);
//        System.out.println(percolation.percolates());;
//        percolation.open(4,3);
//        System.out.println(percolation.percolates());;
//        percolation.open(7,1);
//        System.out.println(percolation.percolates());;
//        percolation.open(6,8);
//        System.out.println(percolation.percolates());;
//        percolation.open(1,4);
//        System.out.println(percolation.percolates());;
//        percolation.open(2,8);
//        System.out.println(percolation.percolates());;
//        percolation.open(5,2);
//        System.out.println(percolation.percolates());;
//        percolation.open(5,4);
//        System.out.println(percolation.percolates());;
//        percolation.open(7,7);
//        System.out.println(percolation.percolates());;
//        percolation.open(4,4);
//        System.out.println(percolation.percolates());;
//        percolation.open(1,5);
//        System.out.println(percolation.percolates());;
//        percolation.open(2,4);
//        System.out.println(percolation.percolates());;
//        percolation.open(7,6);
//        System.out.println(percolation.percolates());;
//        percolation.open(3,6);
//        System.out.println(percolation.percolates());;
//        percolation.open(3,7);
//        System.out.println(percolation.percolates());;
//        percolation.open(5,3);
//        System.out.println(percolation.percolates());;
//        percolation.open(8,6);
//        System.out.println(percolation.percolates());;
//        percolation.open(6,2);
//        System.out.println(percolation.percolates());;
//        percolation.open(7,3);
//        System.out.println(percolation.percolates());;
//        percolation.open(4,8);
//        System.out.println(percolation.percolates());;
//        percolation.open(6,7);
//        System.out.println(percolation.percolates());;
//        percolation.open(5,7);
//        System.out.println(percolation.percolates());;
    }

}
