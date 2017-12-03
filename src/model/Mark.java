package model;

import model.map.Cell;
import model.map.Map;

/**
 * Marcage de configuracion del problema del laberinto
 * Created by Alex on 08/04/2017.
 */
public class Mark {

    private Map m;

    private Cell cellActual;


    public Mark(Map m){
        this.m = m;
        cellActual = new Cell(0, 0);
    }

    public Mark (Mark mark, Map m){
        this.m = m;
        cellActual = new Cell(mark.getCellActual());
    }

    public void mark(Configuration x, int k) {

    }

    public void unmark(Configuration x, int k) {

    }

    public Cell getCellActual(){return cellActual;}

    public Map getMap() {
        return m;
    }

    public void setMap(Map m) {
        this.m = m;
    }
}
