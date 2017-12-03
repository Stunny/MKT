package model.map;

/**
 * Created by avoge on 17/12/2016.
 */
public class BallCell extends Cell {

    private int numOfHits;

    public BallCell(int row, int column, int numHits){
        super(row, column);
        this.numOfHits = numHits;
    }

    public int getNumOfHits() {
        return numOfHits;
    }

}
