package model;

/**
 * Representa una configuracion sobre la que se construye una solucion al problema del laberinto
 * Created by Alex on 08/04/2017.
 */
public class Configuration {

    private char[][] moves;

    public Configuration(int height, int width) {
        moves = new char[height][width];
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height; j++)
                moves[i][j] = '.';
    }

    public Configuration(Configuration x){
        moves = new char[x.getHeight()][x.getWidth()];

        for(int i = 0; i < x.getWidth(); i++)
            for(int j = 0; j < x.getHeight(); j++)
                moves[i][j] = x.getMove(i, j);
    }

    public char getMove(int i, int j) throws IndexOutOfBoundsException{
        return moves[i][j];
    }

    public void setMove(int i, int j, char move) throws IndexOutOfBoundsException{
        moves[i][j] = move;
    }

    public int getWidth(){
        return moves.length;
    }

    public int getHeight(){ return moves[0].length;}
}
