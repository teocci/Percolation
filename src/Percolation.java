import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by teocci on 10/19/16.
 */
public class Percolation {
    private int count, size;
    private boolean[][] grid;
    private WeightedQuickUnionUF gridPercolates;
    private WeightedQuickUnionUF gridFull;


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (!(n > 0))
            throw new java.lang.IllegalArgumentException();
        count = n;
        
        grid = new boolean[count][count];
        for (int row = 0; row < count; ++row) {
            for (int col = 0; col < count; ++col) {
                grid[row][col] = true;
            }
        }

        size = count * count;
        gridPercolates = new WeightedQuickUnionUF(size + 1);
        gridFull = new WeightedQuickUnionUF(size + 1);
        for (int i = 1; i < count; ++i) {
            gridPercolates.union(1, i + 1);
            gridPercolates.union(size, size - i);
            gridFull.union(1, i + 1);
        }

    }


    /*
     *
     * +-----+-----+-----+-----+-----+
     * | 1,1 | 1,2 | 1,3 | 1,4 | 1,5 |
     * |     |     |     |     |     |
     * +-----+-----+-----+-----+-----+
     * | 2,1 | 2,2 | 2,3 | 2,4 | 2,5 |
     * |     |     | up  |     |     |
     * +-----+-----+-----+-----+-----+
     * | 3,1 | 3,2 | 3,3 | 3,4 | 3,5 |
     * |     | lef | r,c | rig |     |
     * +-----+-----+-----+-----+-----+
     * | 4,1 | 4,2 | 4,3 | 4,4 | 4,5 |
     * |     |     | dow |     |     |
     * +-----+-----+-----+-----+-----+
     * | 5,1 | 5,2 | 5,3 | 5,4 | 5,5 |
     * |     |     |     |     |     |
     * +-----+-----+-----+-----+-----+
     *
     * open site (row, col) if it is not open already
     *
     */
    public void open(int row, int col) {
        validateCoords(row, col);
        if (!isBlocked(row, col))
            return;

        unlock(row, col) ;
        int index = coords2Index(row, col);
        // Up
        if (row > 1 && isOpen(row - 1, col)) {
            gridPercolates.union(coords2Index(row - 1, col), index);
            gridFull.union(coords2Index(row - 1, col), index);
        }

        // Down
        if (row < count && isOpen(row + 1, col)) {
            gridPercolates.union(coords2Index(row + 1, col), index);
            gridFull.union(coords2Index(row + 1, col), index);
        }

        // Left
        if (col > 1 && isOpen(row, col - 1)) {
            gridPercolates.union(coords2Index(row, col - 1), index);
            gridFull.union(coords2Index(row, col - 1), index);
        }

        // Right
        if (col < count && isOpen(row, col + 1)) {
            gridPercolates.union(coords2Index(row, col + 1), index);
            gridFull.union(coords2Index(row, col + 1), index);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateCoords(row, col);
        return !isBlocked(row, col);
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return isOpen(row, col) && gridFull.connected(1, coords2Index(row, col));
    }

    // does the system percolate?
    public boolean percolates() {
        // If case n-by-n = 1
        if (count == 1)
            return isOpen(1,1);
        return gridPercolates.connected(1, size);
    }

    private boolean isBlocked(int row, int col) {
        return grid[row-1][col-1];
    }

    private void unlock(int row, int col) {
         grid[row-1][col-1] = false;
    }

    private int coords2Index(int row, int col) {
        return (row - 1) * count + col;
    }

    private void validateCoords(int row, int col) {
        validate(row);
        validate(col);
    }

    // validate that p is a valid index
    private void validate(int p) {
        if (p < 1 || p > count) {
            throw new IndexOutOfBoundsException("Index " + p + " is not between 1 and " + count);
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
