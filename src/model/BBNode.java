package model;

/**
 * Created by avoge on 12/07/2017.
 */
public class BBNode {

    private Configuration configuration;

    private SolutionValue value;

    private int k;

    public BBNode(Configuration configuration, int k, SolutionValue value) {
        this.configuration = configuration;
        this.value = value;
        this.k = k;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public SolutionValue getValue() {
        return value;
    }

    public void setValue(SolutionValue value) {
        this.value = value;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }
}
