package logs;

import org.drools.definition.rule.Rule;

/**
 *
 * @author Maxim Kolchin
 */
public class Action {

    public enum ActionType {
        INSERTED, UPDATED, RETRACTED
    }

    private ActionType type;
    private String packageName;
    private String ruleName;
    private String previous;
    private String current;

    public Action(final Rule r, final ActionType t, final Object p, final Object c) {
        this.packageName = r.getPackageName();
        this.ruleName = r.getName();
        this.type = t;
        this.previous = p != null ? p.toString() : null;
        this.current = c != null ? c.toString() : null;
    }
}
