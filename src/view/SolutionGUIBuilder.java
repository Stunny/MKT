package view;

import model.Configuration;
import model.SolutionValue;
import model.map.Casilla;
import model.map.Map;

import javax.swing.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by Alex on 12/07/2017.
 */
public class SolutionGUIBuilder{

    private Configuration solution;

    private Map map;

    private SolutionValue value;

    private long elapsedTime;

    MultiKeyTreasureGUI solutionGui;

    private DecimalFormat formatter2D;
    private DecimalFormat formatter3D;

    public SolutionGUIBuilder(Map m){

        formatter2D = new DecimalFormat("00");
        formatter3D = new DecimalFormat("000");

        this.map = m;

        solutionGui = new MultiKeyTreasureGUI(500, 500, "SOLUTION", map.getRawMap());
        solutionGui.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        solutionGui.setLocationRelativeTo(null);

    }

    public void start(){

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
        solutionGui.setElapsedTime(formatter2D.format(TimeUnit.NANOSECONDS.toHours(elapsedTime) % 24) + ":" +
                formatter2D.format(TimeUnit.NANOSECONDS.toMinutes(elapsedTime) % 60) + ":" +
                formatter2D.format(TimeUnit.NANOSECONDS.toSeconds(elapsedTime) % 60) +"." +
                formatter3D.format(TimeUnit.NANOSECONDS.toMillis(elapsedTime) % 1000));

    }

    public synchronized void clear(){
        solutionGui.deletePath();
    }

    public void setSolution(Configuration solution) {
        this.solution = new Configuration(solution);
    }

    public void setValue(SolutionValue value) {
        this.value = new SolutionValue(value);
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }
}
