package logs;

import java.util.ArrayList;
import java.util.List;
import org.drools.definition.rule.Rule;

/**
 *
 * @author Maxim Kolchin
 */
public class Firing {

    private String packageName;
    private String ruleName;
    private List<String> facts = new ArrayList<String>();

    public Firing(final Rule r, final List<Object> fs) {
        this.packageName = r.getPackageName();
        this.ruleName = r.getName();
        for(Object o : fs) {
            this.facts.add(o.toString());
        }
    }
}
