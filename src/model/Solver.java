package model;

/**
 *
 * Created by Alex on 08/04/2017.
 */
public interface Solver {

    String SOLVE_BT = "BTSM";
    String SOLVE_IMPBT = "BTAM";
    String SOLVE_BB = "BBSM";
    String SOLVE_IMPBB = "BBAM";

    int OUTPUT_BOTH = 0;
    int OUTPUT_BEST = 1;

    void solve(Configuration x, int k);

    void improvedSolve(Configuration x, int k, Mark m);

}
