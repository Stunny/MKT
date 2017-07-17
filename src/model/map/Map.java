package model.map;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by avoge on 17/12/2016.
 */
public class Map {

    public static final int MOVE_UP = 0;
    public static final int MOVE_LEFT = 1;
    public static final int MOVE_DOWN = 2;
    public static final int MOVE_RIGHT = 3;

    private int INIT_ROW;
    private int INIT_COLUMN;

    private int reqKeys;

    private Casilla[][] laberinto;

    private String[][] rawMap;

    public Map(String fileRoute) {

        File mapFile = new File(fileRoute);

        Scanner fileScanner;

        try {
            fileScanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {
            System.err.print("Map file not found");
            return;
        }

        reqKeys = fileScanner.nextInt();

        int filas = 1, columnas;
        String line;

        fileScanner.nextLine();
        line = fileScanner.nextLine();
        columnas = line.length();

        while(fileScanner.hasNextLine()){
            line = fileScanner.nextLine();
            filas++;
        }

        laberinto = new Casilla[filas][columnas];
        rawMap = new String[filas][columnas];
        fileScanner.close();

        try {
            fileScanner = new Scanner(mapFile);
        } catch (FileNotFoundException e) {return;}

        fileScanner.nextLine();

        char aux;
        int keysIn;

        for(int i = 0; i < laberinto.length; i++){
            line = fileScanner.nextLine();

            for(int j = 0; j < columnas; j++){

                aux = line.charAt(j);
                rawMap[i][j] = Character.toString(aux);
                switch (aux){
                    case 'E':
                        laberinto[i][j] = new EntryCasilla(i, j);
                        INIT_ROW = i;
                        INIT_COLUMN = j;
                        break;
                    case 'T':
                        laberinto[i][j] = new TreasureCasilla(i, j);
                        break;
                    case '-':
                        laberinto[i][j] = new WallCasilla(i, j);
                        break;
                    default:
                        keysIn = Character.getNumericValue(aux);
                        laberinto[i][j] = new Casilla(keysIn, i, j);
                }

            }
        }
    }

    public Map(Map map){
        this.rawMap = map.getRawMap();
        this.INIT_ROW = map.getINIT_ROW();
        this.INIT_COLUMN = map.getINIT_COLUMN();
        this.reqKeys = map.getReqKeys();

        laberinto = new Casilla[map.rows()][map.columns()];
        for(int i = 0; i < map.rows(); i++)
            for(int j = 0; j < map.columns(); j++)
                laberinto[i][j] = new Casilla(map.getCasilla(i, j));
    }

    public Casilla getCasilla(int row, int column){

        Casilla aux = null;
        try{
            aux = laberinto[row][column];
        }catch (IndexOutOfBoundsException e){
            System.err.println("Tas salio del mapa");
        }

        return aux;
    }

    public int rows(){
        return laberinto.length;
    }

    public  int columns(){
        return laberinto[0].length;
    }

    public int getReqKeys() {
        return reqKeys;
    }

    public void setReqKeys(int reqKeys) {
        this.reqKeys = reqKeys;
    }

    public int getINIT_ROW() {
        return INIT_ROW;
    }

    public int getINIT_COLUMN() {
        return INIT_COLUMN;
    }

    /**
     * Retorna la representacion del mapa del laberinto en caracteres.
     * @return Matriz de Strings en la que cada posicion es una casilla
     */
    public String[][] getRawMap(){
        return rawMap;
    }
}
