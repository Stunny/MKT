package model;

/**
 *
 * Created by Alex on 08/04/2017.
 */
public interface Solver {

    /**
     * Solves the Labirynth problem
     * @param x Configuration
     * @param k Current search depth
     */
    void solve(Configuration x, int k);

    /**
     * Solves the Labirynth problem with efficiency improvements
     * @param x Configuration
     * @param k Current search depth
     * @param m Mark
     */
    void improvedSolve(Configuration x, int k, Mark m);

}
