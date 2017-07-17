package model.utils;

import model.Configuration;
import model.SolutionValue;
import model.map.Casilla;
import model.map.Map;
import view.MultiKeyTreasureGUI;

import javax.swing.*;

/**
 * Created by Alex on 12/07/2017.
 */
public class SolutionGUIBuilder extends Thread {

    private Configuration solution;

    private Map map;

    private SolutionValue value;

    private double time;

    MultiKeyTreasureGUI solutionGui;

    public SolutionGUIBuilder(Map m){

        this.map = m;

        solutionGui = new MultiKeyTreasureGUI(500, 500, "SOLUTION", map.getRawMap());
        solutionGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    public void run(){

        int i = 0;

        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());
        int actualMove = solution.getMove(i);

        do{
            Casilla.avanza(casillaActual, actualMove);
            solutionGui.addToPath(casillaActual.getRow(), casillaActual.getColumn());

            i++;
            actualMove = solution.getMove(i);
        }while(solution.getMove(i) != -1);

        solutionGui.setPathLength(value.getPathLength());
        solutionGui.setKeysCollected(value.getKeys());

    }

    public void clear(){
        if(solutionGui != null)
            solutionGui.dispose();
    }

    public void setSolution(Configuration solution) {
        this.solution = new Configuration(solution);
    }

    public void setValue(SolutionValue value) {
        this.value = new SolutionValue(value);
    }
}
