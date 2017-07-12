package model;

/**
 * Created by avoge on 12/07/2017.
 */
public class BBNode {

    private Configuration configuration;

    private SolutionValue value;

    public BBNode(Configuration configuration, SolutionValue value) {
        this.configuration = configuration;
        this.value = value;
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
}
