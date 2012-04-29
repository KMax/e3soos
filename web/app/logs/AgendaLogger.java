package logs;

import java.util.HashMap;
import java.util.Map;
import org.drools.event.rule.*;

/**
 *
 * @author Maxim Kolchin
 */
public class AgendaLogger extends DefaultAgendaEventListener {

    private Map<String, Firing> firings = new HashMap<String, Firing>();

    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        firings.put(
                event.getActivation().getRule().getPackageName()
                + "[" + event.getActivation().getRule().getName() + "]["
                + String.valueOf(event.hashCode()) + "]",
                new Firing(event.getActivation().getRule(),
                event.getActivation().getObjects()));
    }

    public Map<String, Firing> getFirings() {
        return firings;
    }
}
