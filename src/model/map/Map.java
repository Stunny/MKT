package model.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by avoge on 17/12/2016.
 */
public class Map {

    public static final int MOVE_UP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_RIGHT = 3;

    private Cell[][] field;

    private List ballCells;

    private String[][] rawMap;

    public Map() {

        File mapFile = new File("map.txt");

        Scanner fileScanner;

        try {
            fileScanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            System.err.print("Map file not found");
            return;
        }

        int filas = fileScanner.nextInt(), columnas;
        String line;

        fileScanner.nextLine();
        line = fileScanner.nextLine();
        columnas = line.length();

        while(fileScanner.hasNextLine()){
            line = fileScanner.nextLine();
            filas++;
        }

        field = new Cell[filas][columnas];
        rawMap = new String[filas][columnas];
        ballCells = new ArrayList();
        fileScanner.close();

        try {
            fileScanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {return;}

        fileScanner.nextLine();

        char aux;
        int numHits;

        for(int i = 0; i < field.length && fileScanner.hasNextLine(); i++){
            line = fileScanner.nextLine();
            System.out.println(line);
            for(int j = 0; j < columnas; j++){

                aux = line.charAt(j);
                rawMap[i][j] = Character.toString(aux);
                switch (aux){
                    case '.':
                        field[i][j] = new Cell(i, j);
                        break;
                    case 'H':
                        field[i][j] = new HoleCell(i, j);
                        break;
                    case 'X':
                        field[i][j] = new WaterCell(i, j);
                        break;
                    default:
                        numHits = Character.getNumericValue(aux);
                        field[i][j] = new BallCell(i, j, numHits);
                        ballCells.add(field[i][j]);
                }

            }
        }
    }

    public Map(Map map){
        this.rawMap = map.getRawMap();

        field = new Cell[map.rows()][map.columns()];
        for(int i = 0; i < map.rows(); i++)
            for(int j = 0; j < map.columns(); j++){

            field[i][j] = new Cell(i, j);
            field[i][j].setSteps(map.getCell(i, j).getSteps());

            }
    }

    public Cell getCell(int row, int column){

        Cell aux = null;
        try{
            aux = field[row][column];
        }catch (IndexOutOfBoundsException e){
            System.err.println("Tas salio del mapa");
        }

        return aux;
    }

    public List getBallCells() {
        return ballCells;
    }

    public int rows(){
        return field.length;
    }

    public  int columns(){
        return field[0].length;
    }


    /**
     * Retorna la representacion del mapa del field en caracteres.
     * @return Matriz de Strings en la que cada posicion es una casilla
     */
    public String[][] getRawMap(){
        return rawMap;
    }
}
