package model.impl;

import model.Configuration;
import model.Mark;
import model.SolutionValue;
import model.Solver;
import model.map.Casilla;
import model.map.Map;
import model.map.TreasureCasilla;
import model.map.WallCasilla;
import view.MultiKeyTreasureGUI;

/**
 *
 * @author Alex Vogel & Victor Garrido
 * @version 1.0 04/2017
 * @see model.Solver
 */
public class MKTBacktracking implements Solver {

    /**
     * Solve GUI
     */
    private MultiKeyTreasureGUI gui;

    /**
     * Labirynth map
     */
    private Map map;

    /**
     * Best configuration
     */
    private Configuration xMillor;

    /**
     * Best configuration value
     */
    private SolutionValue vMillor;

    /**
     * Creates a new Labirynth solver that uses Backtracking as the serch method
     * @param map Labirynth map
     */
    public MKTBacktracking(Map map, MultiKeyTreasureGUI gui){
        this.map = map;
        this.gui = gui;
        xMillor = new Configuration(map.rows()*map.columns());
        vMillor = new SolutionValue(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    /**
     * Executes the search for the puzzle solutions.
     */
    public void solve(Configuration y, int k) {

        Configuration x = new Configuration(y);

        preparaRecorridoNivel(x, k);

        while(haySucesor(x, k)){
            try {Thread.sleep(100);} catch (InterruptedException e) {}
            siguienteHermano(x, k);
            if(buena(x, k)){

                if(solucion(x, k)){
                    tratarSolucion(x);
                }else{
                    addToPath(x, k);
                    solve(x, k+1);
                    removeFromPath(x, k);
                }

            }


        }

    }

    @Override
    /**
     * Executes the search for the puzzle solutions with a better search efficiency.
     */
    public void improvedSolve(Configuration y, int k, Mark n) {

        Configuration x = new Configuration(y);
        Mark m = new Mark(n, map);

        preparaRecorridoNivel(x, k, m);

        while(haySucesor(x, k)){

            try {Thread.sleep(10);} catch (InterruptedException e) {}

            siguienteHermano(x, k);
            m.mark(x, k);

            if(m.getPathLength() < vMillor.getPathLength() && m.getCurrentKeys() < vMillor.getKeys()){
                if(buena(x, k, m)){
                    if(solucion(x, k, m)){
                        tratarSolucion(x, m);
                    }else{
                        addToPath(m);
                        improvedSolve(x, k+1, m);
                        removeFromPath(m);
                    }
                }
            }

            m.unmark(x, k);
        }

    }

    // Inicializa la posicion 'k' de la configuracion (nivel actual de busqueda) al valor anterior al primer valor
    // posible, ademas de pisar la casilla desde la que parte el nivel
    private void preparaRecorridoNivel(Configuration x, int k){
        x.setMove(k, -1);

        Casilla actual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());
        for(int i = 0; x.getMove(i) != -1; i++){
            Casilla.avanza(actual, x.getMove(i));
        }

        map.getCasilla(actual.getRow(), actual.getColumn()).step();

    }

    // Inicializa la posicion 'k' de la configuracion (nivel actual de busqueda) al valor anterior al primer valor
    // posible, ademas de pisar la casilla desde la que parte el nivel
    private void preparaRecorridoNivel(Configuration x, int k, Mark m){
        x.setMove(k, -1);

        //map.getCasilla(m.getCasillaActual().getRow(), m.getCasillaActual().getColumn()).step();
    }

    // Comprueba si quedan posibilidades hermanas por explorar el el actual nivel de busqueda
    private boolean haySucesor(Configuration x, int k){
        return x.getMove(k) < 4;
    }

    // Actualiza el valor de la configuracion en la posición 'k' a la siguiente posibilidad a explorar
    private void siguienteHermano(Configuration x, int k){
        x.setMove(k, x.getMove(k)+1);
    }

    // Comprueba si el nivel actual de busqueda, en la posibilidad establecida, hace que la configuracion actual sea una
    // solucion
    private boolean solucion(Configuration x, int k){
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());

        for(int i = 0; i <= k; i++){
            Casilla.avanza(casillaActual, x.getMove(i));
        }

        return map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof TreasureCasilla;
    }

    // Comprueba si el nivel actual de busqueda, en la posibilidad establecida, hace que la configuracion actual sea una
    // solucion, utilizando el marcage establecido y, por tanto, reduciendo el coste de la funcion
    private boolean solucion(Configuration x, int k, Mark m){
        return map.getCasilla(m.getCasillaActual().getRow(), m.getCasillaActual().getColumn())
                instanceof TreasureCasilla;
    }

    // Comprueba si al configuracion actual es una parte correcta de una posible solucion
    private boolean buena(Configuration x, int k) {
        int actualMove = x.getMove(0);
        int llavesActuales = 0;
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());

        for (int i = 0; actualMove != -1; i++) {
            actualMove = x.getMove(i);
            Casilla.avanza(casillaActual, actualMove);

            if (casillaActual.getColumn() < 0 || casillaActual.getColumn() > map.columns() - 1
                    || casillaActual.getRow() < 0 || casillaActual.getRow() > map.rows() - 1) {
                return false;
            }

            if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof WallCasilla)
                return false;

            
            llavesActuales += map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
        }

        // Comrpobamos que la casilla a la que se pretende ir está pisada anteriormente
        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getSteps() == 1) {
            return false;
        }

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof TreasureCasilla){
            if (llavesActuales >= map.getReqKeys()) {
                return true;
            }
            return false;
        }

        return true;
    }

    // Comprueba si al configuracion actual es una parte correcta de una posible solucion utilizando el marcage
    // establecido, y, por tanto, reduciendo el coste de la funcion
    private boolean buena(Configuration x, int k, Mark m) {

        Casilla casillaActual = m.getCasillaActual();

        if (casillaActual.getColumn() < 0 || casillaActual.getColumn() > map.columns() - 1
                || casillaActual.getRow() < 0 || casillaActual.getRow() > map.rows() - 1) {
            return false;
        }

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof WallCasilla)
            return false;

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getSteps() > 1){
            return false;
        }

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof TreasureCasilla){
            if (m.getCurrentKeys() >= map.getReqKeys()) {
                return true;
            }
            return false;
        }

        return true;
    }

    // Evalua la solucion encontrada mediante la actual configuración.
    private void tratarSolucion(Configuration x){

        System.out.println("SOLUCION");

        SolutionValue vActual = new SolutionValue(0, 0);
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());

        for(int i = 0; x.getMove(i) != -1; i++){
            vActual.setPathLength(vActual.getPathLength()+1);
            Casilla.avanza(casillaActual, x.getMove(i));
            vActual.setKeys(
                    vActual.getKeys() + map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys()
            );
        }

        if(vActual.getKeys() >= vMillor.getKeys()) return;

        if(vActual.getKeys() >= map.getReqKeys()){
            xMillor = new Configuration(x);
            vMillor = new SolutionValue(vActual);
        }

    }

    // Evalua la solucion encontrada mediante la actual configuración utilizando el marcage establecido y, pòr tanto,
    // reduciendo el coste de la funcion
    private void tratarSolucion(Configuration x, Mark m){
        System.out.println("SOLUCION");

        xMillor = new Configuration(x);
        vMillor.setKeys(m.getCurrentKeys());
        vMillor.setPathLength(m.getPathLength());
    }

    //
    private void addToPath(Configuration x, int k){
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());
        int llaves = 0;

        for(int i = 0; i <= k; i++){
            Casilla.avanza(casillaActual, x.getMove(i));
            llaves += map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
        }

        gui.addToPath(casillaActual.getRow(), casillaActual.getColumn());
        gui.setPathLength(k+1);
    }

    //
    private void addToPath(Mark m){
        gui.addToPath(m.getCasillaActual().getRow(), m.getCasillaActual().getColumn());
        gui.setKeysCollected(m.getCurrentKeys());
        gui.setPathLength(m.getPathLength());
    }

    //
    private void removeFromPath(Configuration x, int k){
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());
        int llavesActuales = 0;

        for(int i = 0; i <= k; i++){
            Casilla.avanza(casillaActual, x.getMove(i));
            llavesActuales += map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
        }
        llavesActuales -= map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();

        gui.deleteFromPath(casillaActual.getRow(), casillaActual.getColumn());
        gui.setPathLength(k+1);
        gui.setKeysCollected(llavesActuales);

    }

    //
    private void removeFromPath(Mark m){
        gui.deleteFromPath(m.getCasillaActual().getRow(), m.getCasillaActual().getColumn());
        gui.setPathLength(m.getPathLength());
        gui.setKeysCollected(m.getCurrentKeys());
    }
}
