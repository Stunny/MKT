package model.impl;

import model.*;
import model.map.Casilla;
import model.map.Map;
import model.map.TreasureCasilla;
import model.map.WallCasilla;
import view.MultiKeyTreasureGUI;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Alex on 08/04/2017.
 */
public class MKTBranchAndBound implements Solver {

    /**
     * Solve GUI
     */
    private MultiKeyTreasureGUI gui;

    /**
     * Labirynth map
     */
    private Map map;

    /**
     * Best configuration value
     */
    private SolutionValue vMillor;

    /**
     * Best configuration
     */
    private Configuration xMillor;

    public MKTBranchAndBound(Map m, MultiKeyTreasureGUI gui){
        this.map = m;
        this.gui = gui;
        xMillor = new Configuration(map.rows()*map.columns());
        vMillor = new SolutionValue(map.rows()*map.columns(), 0);
    }


    @Override
    public void solve(Configuration y, int k) {

        Configuration x = new Configuration(y);
        Configuration[] children;

        PriorityQueue<BBNode> aliveNodes =
                new PriorityQueue<>(map.rows() * map.columns(), new Comparator<BBNode>() {
            @Override
            public int compare(BBNode o1, BBNode o2) {
                if(o1.getValue().getKeys()/o1.getValue().getPathLength() > o2.getValue().getKeys()/o2.getValue().getPathLength()){
                    return 1;
                }
                if(o1.getValue().getKeys()/o1.getValue().getPathLength() < o2.getValue().getKeys()/o2.getValue().getPathLength()){
                    return -1;
                }
                return 0;
            }
        });

        aliveNodes.add(new BBNode(x, 0, vMillor));

        BBNode actualNode;

        while(!aliveNodes.isEmpty()){

            actualNode = aliveNodes.poll();
            children = expand(actualNode);

            for(Configuration child : children){
                if(buena(child) && mejorValor(valor(child), vMillor)){

                    if(solucion(child)){
                        System.out.println("SOLUCION");
                        vMillor = valor(child);
                        xMillor = child;
                    }else{
                        aliveNodes.add(new BBNode(child, actualNode.getK()+1, valor(child)));
                    }

                }
                clearMap();
            }
        }
    }

    /**
     * Retorna las cuatro configuraciones hijas de la especificada
     * @param node Configuracion padre
     */
    private Configuration[] expand(BBNode node){

        int k = node.getK();
        Configuration parent = node.getConfiguration();

        Configuration goUp = new Configuration(parent),
                goDown = new Configuration(parent),
                goLeft = new Configuration(parent),
                goRight = new Configuration(parent);

        goUp.setMove(k, Map.MOVE_UP);
        goLeft.setMove(k, Map.MOVE_LEFT);
        goDown.setMove(k, Map.MOVE_DOWN);
        goRight.setMove(k, Map.MOVE_RIGHT);

        return new Configuration[]{goUp, goLeft, goDown, goRight};
    }

    /**
     * Comprueba si una configuracion es solucion factible o configuracion completable
     * @param x Configuracion
     * @return true si es factible o completable
     */
    private boolean buena(Configuration x){
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

            map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).step();
            if(map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getSteps() > 1){
                return false;
            }

            llavesActuales += map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();
        }

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof TreasureCasilla){
            if (llavesActuales >= map.getReqKeys()) {
                return true;
            }
            return false;
        }

        return true;
    }

    /**
     * Reinicia los pasos de todas las casillas del mapa a 0
     */
    private void clearMap(){
        for(int j = 0; j < map.rows(); j++)
            for(int k = 0; k < map.columns(); j++)
                map.getCasilla(j, k).setSteps(0);
    }

    /**
     * Comprueba si una configuracion es solucion
     * @param x Configuracion
     * @return true si es solucion
     */
    private boolean solucion(Configuration x){
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());

        for(int i = 0; x.getMove(i) != -1; i++){
            Casilla.avanza(casillaActual, x.getMove(i));
        }

        return map.getCasilla(casillaActual.getRow(), casillaActual.getColumn())
                instanceof TreasureCasilla;
    }

    /**
     * Evalua el valor de la configuracion especificada
     * @param x Configuracion
     * @return Valor de configuracion
     */
    private SolutionValue valor(Configuration x){
        SolutionValue value = new SolutionValue(0, 0);
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());

        for(int i = 0; x.getMove(i) != -1; i++){
            value.setPathLength(value.getPathLength()+1);
            Casilla.avanza(casillaActual, x.getMove(i));
            value.setKeys(
                    value.getKeys() + map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys()
            );
        }

        return value;
    }

    /**
     * Comprueba si el primer valor de solucion es mejor que el segundo
     * @param s1 Valor de solucion a comparar
     * @param s2 Valor de solucion con el que comparar
     * @return true Si el valor s1 es mejor que s2
     */
    private boolean mejorValor(SolutionValue s1, SolutionValue s2){
        return (s1.getKeys()/s1.getPathLength()) > (s2.getKeys()/s2.getPathLength());
    }


    @Override
    public void improvedSolve(Configuration x, int k, Mark m) {

    }
}
