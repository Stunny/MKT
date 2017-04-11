package model;

import model.impl.MKTBacktracking;
import model.map.Casilla;
import model.map.Map;

/**
 * Created by Alex on 08/04/2017.
 */
public class Mark {

    private Map m;

    private Casilla casillaActual;

    private int currentKeys;

    private int pathLength;

    public Mark(Map m){
        this.m = m;
        pathLength = 0;
        currentKeys = 0;

        casillaActual = new Casilla(m.getINIT_ROW(), m.getINIT_COLUMN());
    }

    public void mark(Configuration x, int k){
        Casilla.avanza(casillaActual, x.getMove(k));
        pathLength++;
        currentKeys += m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
        m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).step();
    }

    public void unmark(Configuration x, int k){
        pathLength--;
        currentKeys -= m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
        m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).unStep();
        Casilla.retrocede(casillaActual, x.getMove(k));

    }

    public Casilla getCasillaActual(){return casillaActual;}

    public int getCurrentKeys(){return currentKeys;}

    public int getPathLength(){return pathLength;}

}
