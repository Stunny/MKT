package model;

/**
 *
 * Created by Alex on 08/04/2017.
 */
public interface Solver {

    void solve(Configuration x, int k);

    void improvedSolve(Configuration x, int k, Mark m);

}
