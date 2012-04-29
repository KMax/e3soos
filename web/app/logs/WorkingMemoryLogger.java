package logs;

import java.util.HashMap;
import java.util.Map;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;

/**
 *
 * @author Maxim Kolchin
 */
public class WorkingMemoryLogger implements WorkingMemoryEventListener {

    private Map<String, Action> actions = new HashMap<String, Action>();

    public void objectInserted(ObjectInsertedEvent event) {
        if (event.getPropagationContext().getRule() != null) {
            actions.put(event.getPropagationContext().getRule().getPackageName()
                    + "[" + event.getPropagationContext().getRule().getName()
                    + "][" + String.valueOf(event.hashCode()) + "]",
                    new Action(event.getPropagationContext().getRule(),
                    Action.ActionType.INSERTED,
                    null, event.getObject()));
        }
    }

    public void objectUpdated(ObjectUpdatedEvent event) {
        if (event.getPropagationContext().getRule() != null) {
            actions.put(event.getPropagationContext().getRule().getPackageName()
                    + "[" + event.getPropagationContext().getRule().getName()
                    + "][" + String.valueOf(event.hashCode()) + "]",
                    new Action(event.getPropagationContext().getRule(),
                    Action.ActionType.UPDATED, event.getOldObject(),
                    event.getObject()));
        }
    }

    public void objectRetracted(ObjectRetractedEvent event) {
        if (event.getPropagationContext().getRule() != null) {
            actions.put(event.getPropagationContext().getRule().getPackageName()
                    + "[" + event.getPropagationContext().getRule().getName()
                    + "][" + String.valueOf(event.hashCode()) + "]",
                    new Action(event.getPropagationContext().getRule(),
                    Action.ActionType.RETRACTED,
                    event.getOldObject(), null));
        }
    }

    public Map<String, Action> getActions() {
        return actions;
    }
}
