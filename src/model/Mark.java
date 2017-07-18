package model;

import model.map.Casilla;
import model.map.Map;

/**
 * Marcage de configuracion del problema del laberinto
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

    public Mark (Mark mark, Map m){
        this.m = m;
        casillaActual = new Casilla(mark.getCasillaActual());
        currentKeys = mark.getCurrentKeys();
        pathLength = mark.getPathLength();
    }

    public void mark(Configuration x, int k) {
        Casilla.avanza(casillaActual, x.getMove(k));
        pathLength++;

        if (casillaActual.getRow() >= 0 && casillaActual.getColumn() >= 0
                && casillaActual.getRow() < m.rows() && casillaActual.getColumn() < m.columns()){
            currentKeys += m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
            m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).step();
        }
    }

    public void unmark(Configuration x, int k) {
        if(casillaActual.getRow() >= 0 && casillaActual.getColumn() >= 0
                && casillaActual.getRow() < m.rows() && casillaActual.getColumn() < m.columns()){
            currentKeys -= m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
            m.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).unStep();
        }
        Casilla.retrocede(casillaActual, x.getMove(k));
        pathLength--;
    }

    public Casilla getCasillaActual(){return casillaActual;}

    public int getCurrentKeys(){return currentKeys;}

    public int getPathLength(){return pathLength;}

    public Map getMap() {
        return m;
    }

    public void setMap(Map m) {
        this.m = m;
    }
}
