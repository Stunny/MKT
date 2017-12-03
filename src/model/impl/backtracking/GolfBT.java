package model.impl.backtracking;

import model.Configuration;
import model.Mark;
import model.Solver;
import model.map.*;


/**
 * Clase que implementa un solucionador del laberinto mediante el algoritmo de Backtracking
 * @author Alex Vogel
 * @version 2.0 07/2017
 * @see model.Solver
 */
public class GolfBT implements Solver {

    /**
     * Labirynth map
     */
    private Map map;


    /**
     * Creates a new Labirynth solver that uses Backtracking as the serch method
     * @param map Labirynth map
     */
    public GolfBT(Map map){
        this.map = map;
    }


    @Override
    /**
     * Executes the search for the puzzle solutions with a better search efficiency.
     */
    public void solve(Configuration y, int currentBall, Mark n) {

        Configuration x = new Configuration(y);
        Mark m = new Mark(n, map);

        BallCell ball = (BallCell) map.getBallCells().get(currentBall);

        int ballFirstHit = -1;

        while(hasChildNodes(ballFirstHit)){

            nextSibling(x, ballFirstHit);

            if(solutionCanBeCompleted(x, ballFirstHit, m)){
                if(isSolution(x, currentBall, m)){
                    manageSolution(x, m);
                }else{
                    solve(x, currentBall+1, m);
                }
            }

        }
    }


    /**
     * Comprueba si quedan posibilidades hermanas por explorar el el actual nivel de busqueda
    */
    private boolean hasChildNodes(int ballFirstHit){
        return ballFirstHit < 4;
    }

    /** Actualiza el valor de la configuracion en la posición 'k' a la siguiente posibilidad a explorar
    */
    private void nextSibling(Configuration x, int ballFirstHit){
        ballFirstHit ++;

        //TODO: colocar el camino de la pelota actual con el golpe especificado
    }


    /** Comprueba si el nivel actual de busqueda, en la posibilidad establecida, hace que la configuracion actual sea una
     * isSolution, utilizando el marcage establecido y, por tanto, reduciendo el coste de la funcion
    */
    private boolean isSolution(Configuration x, int k, Mark m){
        return true;
    }


    /** Comprueba si al configuracion actual es una parte correcta de una posible isSolution utilizando el marcage
    // establecido, y, por tanto, reduciendo el coste de la funcion
    */
    private boolean solutionCanBeCompleted(Configuration x, int k, Mark m) {


        return true;
    }


    /** Evalua la isSolution encontrada mediante la actual configuración utilizando el marcage establecido y, pòr tanto,
    // reduciendo el coste de la funcion
    */
    private void manageSolution(Configuration x, Mark m){


    }


}
