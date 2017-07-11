import model.Configuration;
import model.Mark;
import model.Solver;
import model.impl.MKTBacktracking;
import model.impl.MKTBranchAndBound;
import model.map.Map;
import view.MultiKeyTreasureGUI;

/**
 * Created by Alex on 07/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        Map m = new Map(args[0]);
        MultiKeyTreasureGUI gui = new MultiKeyTreasureGUI(500, 500, "MKT", m.getRawMap());
        System.out.println(m.getINIT_COLUMN()+","+m.getINIT_ROW());

        gui.setPathLength(0);
        gui.setKeysCollected(0);

        Configuration x = new Configuration(m.rows()*m.columns());
        Mark mark = new Mark(m);

        switch(args[1]){
            case "BTSM":
                Solver solver = new MKTBacktracking(m, gui);
                gui.startChronometer();
                solver.solve(x, 0);
                gui.stopChronometer();
                break;
            case "BTAM":
                Solver solver2 = new MKTBacktracking(m, gui);
                gui.startChronometer();
                solver2.improvedSolve(x, 0, mark);
                gui.stopChronometer();
                break;
            case "BBSM":
                Solver solver3 = new MKTBranchAndBound(m, gui);
                gui.startChronometer();
                solver3.solve(x, 0);
                gui.stopChronometer();
                break;
            case "BBAM":
                Solver solver4 = new MKTBranchAndBound(m, gui);
                gui.startChronometer();
                solver4.improvedSolve(x, 0, mark);
                gui.stopChronometer();
                break;
            default: System.exit(0);
        }
    }

}
