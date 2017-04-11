package model;

/**
 * Created by Alex on 08/04/2017.
 */
public class SolutionValue {

    private int pathLength;

    private int keys;

    public SolutionValue(SolutionValue s){
        this.pathLength = s.getPathLength();
        this.keys = s.getKeys();
    }

    public SolutionValue(int pathLength, int keys) {
        this.pathLength = pathLength;
        this.keys = keys;
    }

    public int getPathLength() {
        return pathLength;
    }

    public void setPathLength(int pathLength) {
        this.pathLength = pathLength;
    }

    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }
}
