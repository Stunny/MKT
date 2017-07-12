package model.impl;

import model.*;
import model.map.Map;
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

        aliveNodes.add(new BBNode(x, vMillor));

        while(!aliveNodes.isEmpty()){

            x = aliveNodes.poll().getConfiguration();
            children = expand(x);

            for(Configuration hijoI : children){
                if(buena(hijoI) && mejorValor(valor(hijoI), vMillor)){

                    if(solucion(hijoI)){
                        vMillor = valor(hijoI);
                        xMillor = hijoI;
                    }else{
                        aliveNodes.add(new BBNode(hijoI, valor(hijoI)));
                    }

                }
            }
        }
    }

    @Override
    public void improvedSolve(Configuration x, int k, Mark m) {

    }

    /**
     * Retorna las cuatro configuraciones hijas de la especificada
     * @param x Configuracion padre
     */
    private Configuration[] expand(Configuration x){
        Configuration goUp = new Configuration(x),
                goDown = new Configuration(x),
                goLeft = new Configuration(x),
                goRight = new Configuration(x);

        return new Configuration[]{goUp, goLeft, goDown, goRight};
    }

    /**
     * Comprueba si una configuracion es solucion factible o configuracion completable
     * @param x Configuracion
     * @return true si es factible o completable
     */
    private boolean buena(Configuration x){

        return true;
    }

    /**
     * Comprueba si una configuracion es solucion
     * @param x Configuracion
     * @return true si es solucion
     */
    private boolean solucion(Configuration x){


        return false;
    }

    /**
     * Evalua el valor de la configuracion especificada
     * @param x Configuracion
     * @return Valor de configuracion
     */
    private SolutionValue valor(Configuration x){

        return null;
    }

    /**
     * Comprueba si el primer valor de solucion es mejor que el segundo
     * @param s1 Valor de solucion a comparar
     * @param s2 Valor de solucion con el que comparar
     * @return true Si el valor s1 es mejor que s2
     */
    private boolean mejorValor(SolutionValue s1, SolutionValue s2){

        return false;
    }
}
