package model.map;

/**
 * Created by avoge on 08/12/2016.
 */
public class Casilla {

    protected int qKeys;
    protected int steps;

    protected int row;
    protected int column;

    /**
     * Construye un objeto casilla de mapa
     * @param qKeys Llaves que contiene
     * @param row Fila en la que esta situada
     * @param column Columna en la que esta situada
     */
    public Casilla(int qKeys, int row, int column) {
        this.qKeys = qKeys;
        this.row = row;
        this.column = column;
        steps = 0;
    }

    /**
     *
     * @param row
     * @param column
     */
    public Casilla(int row, int column) {
        this.row = row;
        this.column = column;
        steps = 0;
    }

    /**
     *
     * @param c
     */
    public Casilla(Casilla c){
        this.qKeys = c.getqKeys();
        this.row = c.getRow();
        this.column = c.getColumn();
        this.steps = c.getSteps();
    }

    /**
     *
     * @return
     */
    public int getqKeys() {
        return qKeys;
    }

    /**
     *
     * @return
     */
    public int getRow() {
        return row;
    }

    /**
     *
     * @return
     */
    public int getColumn() {
        return column;
    }

    /**
     *
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     *
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     *
     */
    public void step(){
        steps++;
    }

    /**
     *
     */
    public void unStep(){
        steps = steps != 0 ? steps-1 : steps;
    }

    public void setSteps(int steps){this.steps = steps;}

    /**
     *
     * @return
     */
    public int getSteps(){return steps;}

    /**
     * Avanza las coordenadas de cierta casilla segun el movimiento especificado
     * @param casillaActual Casilla que se desea avanzar
     * @param move Movimiento que indica la direccion del avance
     */
    public static void avanza(Casilla casillaActual, int move){
        switch(move){
            case Map.MOVE_UP:
                casillaActual.setRow(casillaActual.getRow()-1);
                break;
            case Map.MOVE_LEFT:
                casillaActual.setColumn(casillaActual.getColumn()-1);
                break;
            case Map.MOVE_DOWN:
                casillaActual.setRow(casillaActual.getRow()+1);
                break;
            case Map.MOVE_RIGHT:
                casillaActual.setColumn(casillaActual.getColumn()+1);
                break;
        }
    }

    /**
     * Retrocede una casilla a las coordenadas anteriores a hacer el movimiento indicado
     * @param casillaActual Casilla que se desea retroceder
     * @param move Movimiento a deshacer
     */
    public static void retrocede(Casilla casillaActual, int move){
        switch(move){
            case Map.MOVE_UP:
                casillaActual.setRow(casillaActual.getRow()+1);
                break;
            case Map.MOVE_LEFT:
                casillaActual.setColumn(casillaActual.getColumn()+1);
                break;
            case Map.MOVE_DOWN:
                casillaActual.setRow(casillaActual.getRow()-1);
                break;
            case Map.MOVE_RIGHT:
                casillaActual.setColumn(casillaActual.getColumn()-1);
                break;
        }
    }
}
