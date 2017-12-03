package model.map;

/**
 * Created by avoge on 08/12/2016.
 */
public class Cell {

    protected int steps;

    protected int row;
    protected int column;


    /**
     *
     * @param row
     * @param column
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
        steps = 0;
    }

    /**
     *
     * @param c
     */
    public Cell(Cell c){
        this.row = c.getRow();
        this.column = c.getColumn();
        this.steps = c.getSteps();
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
     * @param cellActual Cell que se desea avanzar
     * @param move Movimiento que indica la direccion del avance
     */
    public static void mvForward(Cell cellActual, int move){
        switch(move){
            case Map.MOVE_UP:
                cellActual.setRow(cellActual.getRow()-1);
                break;
            case Map.MOVE_LEFT:
                cellActual.setColumn(cellActual.getColumn()-1);
                break;
            case Map.MOVE_DOWN:
                cellActual.setRow(cellActual.getRow()+1);
                break;
            case Map.MOVE_RIGHT:
                cellActual.setColumn(cellActual.getColumn()+1);
                break;
        }
    }

    /**
     * Retrocede una casilla a las coordenadas anteriores a hacer el movimiento indicado
     * @param cellActual Cell que se desea retroceder
     * @param move Movimiento a deshacer
     */
    public static void mvBackwards(Cell cellActual, int move){
        switch(move){
            case Map.MOVE_UP:
                cellActual.setRow(cellActual.getRow()+1);
                break;
            case Map.MOVE_LEFT:
                cellActual.setColumn(cellActual.getColumn()+1);
                break;
            case Map.MOVE_DOWN:
                cellActual.setRow(cellActual.getRow()-1);
                break;
            case Map.MOVE_RIGHT:
                cellActual.setColumn(cellActual.getColumn()-1);
                break;
        }
    }
}
