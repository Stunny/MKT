package model.impl;

import model.Configuration;
import model.Mark;
import model.SolutionValue;
import model.Solver;
import model.map.Map;
import view.MultiKeyTreasureGUI;

/**
 * Created by Alex on 08/04/2017.
 */
public class MKTBranchAndBound implements Solver {

    /**
     * Solve GUI
     */
    private MultiKeyTreasureGUI gui;

    /**
     * Labirynth map
     */
    private Map map;

    /**
     * Configuration
     */

    /**
     * Best configuration value
     */
    private SolutionValue vMillor;



    private Configuration x;

    /**
     * Best configuration
     */
    private Configuration xMillor;

    public MKTBranchAndBound(Map m, MultiKeyTreasureGUI gui){}


    @Override
    public void solve(Configuration x, int k) {
        System.out.println("ayy");
    }

    @Override
    public void improvedSolve(Configuration x, int k, Mark m) {

    }
}
