package model.impl;

import model.*;
import model.map.*;
import model.utils.SolutionGUIBuilder;
import view.MultiKeyTreasureGUI;

import java.util.PriorityQueue;

/**
 * Created by Alex on 08/04/2017.
 */
public class MKTBranchAndBound implements Solver {

    /**
     * Solve GUI
     */
    private MultiKeyTreasureGUI progressGUI;

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
        this.progressGUI = gui;

        xMillor = new Configuration(map.rows()*map.columns());
        vMillor = new SolutionValue(map.rows()*map.columns(), map.rows()*map.columns());
    }


    @Override
    public void solve(Configuration y, int k) {

        SolutionGUIBuilder bestSolutionGUIbuilder = new SolutionGUIBuilder(map);
        bestSolutionGUIbuilder.setValue(vMillor);
        bestSolutionGUIbuilder.setSolution(xMillor);

        Configuration x = new Configuration(y);
        Configuration[] children;

        PriorityQueue<BBNode> aliveNodes =
                new PriorityQueue<>(map.rows() * map.columns(), (o1, o2) -> {
                    if(o1.getValue().getKeys()/o1.getValue().getPathLength() > o2.getValue().getKeys()/o2.getValue().getPathLength()){
                        return 1;
                    }
                    if(o1.getValue().getKeys()/o1.getValue().getPathLength() < o2.getValue().getKeys()/o2.getValue().getPathLength()){
                        return -1;
                    }
                    return 0;
                });

        aliveNodes.add(new BBNode(x, 0, vMillor));

        BBNode actualNode;

        while(!aliveNodes.isEmpty()){

            actualNode = aliveNodes.poll();
            children = expand(actualNode);

            for(Configuration child : children){

                if(buena(child)){
                    if(solucion(child) && mejorCamino(valor(child), vMillor)){
                        System.out.println("SOLUCION");
                        vMillor = valor(child);
                        xMillor = child;

                        if(bestSolutionGUIbuilder.isAlive()) bestSolutionGUIbuilder.interrupt();
                        bestSolutionGUIbuilder.clear();
                        bestSolutionGUIbuilder = new SolutionGUIBuilder(map);

                        bestSolutionGUIbuilder.setSolution(xMillor);
                        bestSolutionGUIbuilder.setValue(vMillor);
                        bestSolutionGUIbuilder.start();

                    }else if(mejorCamino(valor(child), vMillor)){
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
        int actualMove;
        int llavesActuales = 0, i = 0;
        Casilla casillaActual = new Casilla(map.getINIT_ROW(), map.getINIT_COLUMN());

        actualMove = x.getMove(i);

        clearMap();


        do {
            Casilla.avanza(casillaActual, actualMove);
            if (casillaActual.getColumn() < 0 || casillaActual.getColumn() > map.columns() - 1
                    || casillaActual.getRow() < 0 || casillaActual.getRow() > map.rows() - 1) {
                return false;
            }

            if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof WallCasilla)
                return false;

            if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof EntryCasilla)
                return false;


            map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).step();
            if(map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getSteps() > 1){
                return false;
            }

            llavesActuales += map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()).getqKeys();

            i++;
            actualMove = x.getMove(i);
        } while(actualMove != -1);

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
            for(int k = 0; k < map.columns(); k++)
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
     * PBMSC
     * Comprueba si el primer valor de solucion es mejor que el segundo
     * @param s1 Valor de solucion a comparar
     * @param s2 Valor de solucion con el que comparar
     * @return true Si el valor s1 es mejor que s2
     */
    private boolean mejorCamino(SolutionValue s1, SolutionValue s2){
        return (s1.getKeys() <= s2.getKeys() && s1.getPathLength() < s2.getPathLength());
    }

    //**************************************************************************************************************//

    @Override
    public void improvedSolve(Configuration y, int k, Mark m) {
        SolutionGUIBuilder bestSolutionGUIbuilder = new SolutionGUIBuilder(map);
        bestSolutionGUIbuilder.setValue(vMillor);
        bestSolutionGUIbuilder.setSolution(xMillor);

        Configuration x = new Configuration(y);
        BBMarkedNode[] children;

        PriorityQueue<BBMarkedNode> aliveNodes =
                new PriorityQueue<>(map.rows() * map.columns(), (o1, o2) -> {
                    if(o1.getValue().getKeys()/o1.getValue().getPathLength() > o2.getValue().getKeys()/o2.getValue().getPathLength()){
                        return 1;
                    }
                    if(o1.getValue().getKeys()/o1.getValue().getPathLength() < o2.getValue().getKeys()/o2.getValue().getPathLength()){
                        return -1;
                    }
                    return 0;
                });

        aliveNodes.add(new BBMarkedNode(x, 0, vMillor, m));

        BBMarkedNode actualNode;

        Configuration childX;
        Mark childM;

        while(!aliveNodes.isEmpty()){

            actualNode = aliveNodes.poll();
            children = expand(actualNode);

            for(BBMarkedNode child : children){
                //try{Thread.sleep(10);}catch(InterruptedException e){}

                childX = child.getConfiguration();
                childM = child.getMark();

                if(buena(childX, childM)){
                    System.out.println("buena");

                    if(solucion(childM) && mejorCamino(valor(childM), vMillor)){
                        System.out.println("SOLUCION");
                        vMillor = valor(childM);
                        xMillor = childX;

                        if(bestSolutionGUIbuilder.isAlive()) bestSolutionGUIbuilder.interrupt();
                        bestSolutionGUIbuilder.clear();
                        bestSolutionGUIbuilder = new SolutionGUIBuilder(map);

                        bestSolutionGUIbuilder.setSolution(xMillor);
                        bestSolutionGUIbuilder.setValue(vMillor);
                        bestSolutionGUIbuilder.start();

                    }else if(mejorCamino(valor(childM), vMillor)){
                        aliveNodes.add(new BBMarkedNode(childX, child.getK()+1, valor(childM), childM));
                    }

                }

                clearMap();
            }
        }
    }

    private BBMarkedNode[] expand(BBMarkedNode node){

        int k = node.getK();
        Configuration parentX = node.getConfiguration();
        Mark parentM = node.getMark();

        Configuration goUp = new Configuration(parentX),
                goDown = new Configuration(parentX),
                goLeft = new Configuration(parentX),
                goRight = new Configuration(parentX);

        goUp.setMove(k, Map.MOVE_UP);
        Mark upMark = new Mark(parentM, map);
        upMark.mark(goUp, k);
        SolutionValue upValue = new SolutionValue(k, upMark.getCurrentKeys());

        goLeft.setMove(k, Map.MOVE_LEFT);
        Mark leftMark = new Mark(parentM, map);
        leftMark.mark(goLeft, k);
        SolutionValue leftValue = new SolutionValue(k, leftMark.getCurrentKeys());

        goDown.setMove(k, Map.MOVE_DOWN);
        Mark downMark = new Mark(parentM, map);
        downMark.mark(goDown, k);
        SolutionValue downvalue = new SolutionValue(k, downMark.getCurrentKeys());

        goRight.setMove(k, Map.MOVE_RIGHT);
        Mark rightMark = new Mark(parentM, map);
        rightMark.mark(goRight, k);
        SolutionValue rightValue = new SolutionValue(k, rightMark.getCurrentKeys());

        return new BBMarkedNode[]{
                new BBMarkedNode(goUp, k, upValue, upMark),
                new BBMarkedNode(goLeft, k, leftValue, leftMark),
                new BBMarkedNode(goDown, k, downvalue, downMark),
                new BBMarkedNode(goRight, k, rightValue, rightMark)
        };
    }

    private boolean buena(Configuration x, Mark m){

        Casilla casillaActual = m.getCasillaActual();
        int actualMove = x.getMove(m.getPathLength()-1);

        clearMap();

        Casilla.avanza(casillaActual, actualMove);

        if (casillaActual.getColumn() < 0 || casillaActual.getColumn() > map.columns() - 1
                || casillaActual.getRow() < 0 || casillaActual.getRow() > map.rows() - 1) {
            return false;
        }

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof WallCasilla)
            return false;

        if (map.getCasilla(casillaActual.getRow(), casillaActual.getColumn()) instanceof EntryCasilla)
            return false;


        m.getCasillaActual().step();
        if(m.getCasillaActual().getSteps() > 1){
            m.getCasillaActual().unStep();
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

    private boolean solucion(Mark m){
        return map.getCasilla(m.getCasillaActual().getRow(), m.getCasillaActual().getColumn())
                instanceof TreasureCasilla;
    }

    private SolutionValue valor(Mark m){
        return new SolutionValue(m.getPathLength(), m.getCurrentKeys());
    }
}
