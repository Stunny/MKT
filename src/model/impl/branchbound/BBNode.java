package model.impl.branchbound;

import model.Configuration;
import model.SolutionValue;

/**
 * Nodo de branch and bound.
 * Created by avoge on 12/07/2017.
 */
public class BBNode {

    /**
     * Configuracion del nodo de busqueda
     */
    private Configuration configuration;

    /**
     * Valor de la configuracion del nodo de busqueda
     */
    private SolutionValue value;

    /**
     * Nivel de busqueda del nodo
     */
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
