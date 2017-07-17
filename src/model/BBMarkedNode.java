package model;

import model.map.Map;

/**
 * Nodo de branch and bound que incluye marcage para la configuracion que almacena
 * Created by avoge on 17/07/2017.
 */
public class BBMarkedNode extends BBNode {

    private Mark mark;

    public BBMarkedNode(Configuration configuration, int k, SolutionValue value, Mark m) {
        super(configuration, k, value);
        this.mark = m;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }
}
