import model.Configuration;
import model.Mark;
import model.Solver;
import model.impl.MKTBacktracking;
import model.map.Map;
import view.MultiKeyTreasureGUI;

/**
 * Created by Alex on 07/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        Map m = new Map("map.txt");
        MultiKeyTreasureGUI gui = new MultiKeyTreasureGUI(500, 500, "MKT", m.getRawMap());
        System.out.println(m.getINIT_COLUMN()+","+m.getINIT_ROW());

        gui.setPathLength(0);
        gui.setKeysCollected(0);


        Solver solver = new MKTBacktracking(m, gui);
        Configuration x = new Configuration(m.rows()*m.columns());
        Mark mark = new Mark(m);

        gui.startChronometer();
        //solver.solve(x, 0);
        solver.improvedSolve(x, 0, mark);
        gui.stopChronometer();
    }

}
