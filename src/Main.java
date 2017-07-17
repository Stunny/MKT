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
        MultiKeyTreasureGUI progressGUI = new MultiKeyTreasureGUI(500, 500, "MKT", m.getRawMap());

        System.out.println("CASILLA INICIAL: "+m.getINIT_COLUMN()+","+m.getINIT_ROW());

        progressGUI.setPathLength(0);
        progressGUI.setKeysCollected(0);

        Configuration x = new Configuration(m.rows()*m.columns());
        Mark mark = new Mark(new Map(m));

        switch(args[1]){
            case "BTSM":
                Solver solver = new MKTBacktracking(m, progressGUI);
                progressGUI.startChronometer();
                solver.solve(x, 0);
                progressGUI.stopChronometer();
                break;
            case "BTAM":
                Solver solver2 = new MKTBacktracking(m, progressGUI);
                progressGUI.startChronometer();
                solver2.improvedSolve(x, 0, mark);
                progressGUI.stopChronometer();
                break;
            case "BBSM":
                Solver solver3 = new MKTBranchAndBound(m, progressGUI);
                progressGUI.startChronometer();
                solver3.solve(x, 0);
                progressGUI.stopChronometer();
                break;
            case "BBAM":
                Solver solver4 = new MKTBranchAndBound(m, progressGUI);
                progressGUI.startChronometer();
                solver4.improvedSolve(x, 0, mark);
                progressGUI.stopChronometer();
                break;
            default: System.exit(0);
        }
    }

}
