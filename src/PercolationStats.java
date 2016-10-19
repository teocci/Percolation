import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.Arrays;

/**
 * Created by teocci on 10/19/16.
 */
public class PercolationStats {

    private double[] results;
    private int trials, count, size;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int t) {
        if (!(n > 0 && t > 0))
            throw new IllegalArgumentException();

        count = n;
        trials = t;
        size = count * count;
        results = new double[trials];
        Percolation material;

        for (int i = 0; i < trials; ++i) {
            material = new Percolation(n);

            int row, col;
            int openNum = 0;

            while (!material.percolates() && openNum < size) {
                row = StdRandom.uniform(1, count + 1);
                col = StdRandom.uniform(1, count + 1);

                if (material.isOpen(row, col))
                    continue;
                material.open(row, col);
                openNum++;
            }

            results[i] = (double) openNum / size;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.printf("mean = %f\n", stats.mean());
        StdOut.printf("stddev = %f\n", stats.stddev());
        StdOut.printf("95%% confidence interval = %f, %f\n", stats.confidenceLo(), stats.confidenceHi());
    }
}