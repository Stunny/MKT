package model;

/**
 * Created by Alex on 08/04/2017.
 */
public class Configuration {

    private int[] moves;

    public Configuration(int maxMoves) {
        moves = new int[maxMoves];
        for(int i = 0; i < maxMoves; i++) moves[i] = -1;
    }

    public Configuration(Configuration x){
        moves = new int[x.getLength()];

        for(int i = 0; i < x.getLength(); i++)
            moves[i] = x.getMove(i);
    }

    public int getMove(int k) throws IndexOutOfBoundsException{
        return moves[k];
    }

    public void setMove(int k, int move) throws IndexOutOfBoundsException{
        moves[k] = move;
    }

    public int getLength(){
        return moves.length;
    }
}
