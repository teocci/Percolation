import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by teocci on 10/19/16.
 */
public class Percolation {
    private int count, size;
    private boolean[][] grid;
    private WeightedQuickUnionUF unidimGrid; // for percolates()


    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (!(n > 0))
            throw new java.lang.IllegalArgumentException();
        count = n;
        
        grid = new boolean[count][count];
        for (int row = 0; row < count; ++row) {
            for (int col = 0; col < count; ++col) {
                grid[row][col] = false;
            }
        }

        size = count * count;
        unidimGrid = new WeightedQuickUnionUF(size + 1);
        for (int i = 1; i < count; ++i) {
            unidimGrid.union(1, i + 1);
            unidimGrid.union(size, size - i);
        }

    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validArgs(row,col);

        grid[row-1][col-1] = true;
        int idx = coord2Index(row, col);

        if (row > 1 && isOpen(row-1, col)) {
            uf.union(coord2Index(row-1, col), idx);
            ufBack.union(coord2Index(row-1, col), idx);
        }
        if (row < N && isOpen(row+1, col)) {
            uf.union(coord2Index(row+1, col), idx);
            ufBack.union(coord2Index(row+1, col), idx);
        }
        if (col > 1 && isOpen(row, col-1)) {
            uf.union(coord2Index(row, col-1), idx);
            ufBack.union(coord2Index(row, col-1), idx);
        }
        if (col < N && isOpen(row, col+1)) {
            uf.union(coord2Index(row, col+1), idx);
            ufBack.union(coord2Index(row, col+1), idx);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        return true;
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        return true;
    }

    // does the system percolate?
    public boolean percolates() {
        return true;
    }

    private int coord2Index(int row, int col) {
        return (row - 1) * count + col;
    }

    private void validCoordinates(int i, int j) {
        if (i < 1 || i > count || j < 1 || j > count)
            throw new java.lang.IndexOutOfBoundsException();
    }

    // validate that p is a valid index
    private void validate(int p) {
        if (p < 0 || p >= count) {
            throw new IndexOutOfBoundsException("ndex " + p + " is not between 1 and " + (n-1));
        }
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
