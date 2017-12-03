import model.Configuration;
import model.Mark;
import model.Solver;

import model.impl.backtracking.GolfBT;
import model.map.Map;


/**
 * Created by Alex on 07/04/2017.
 */
public class Main {

    public static void main(String[] args) {

        Map m = new Map();


        Configuration x = new Configuration(m.rows(), m.columns());
        Mark mark = new Mark(new Map(m));

        Solver solver = new GolfBT(m);
        solver.solve(x, 0, mark);

    }

}
