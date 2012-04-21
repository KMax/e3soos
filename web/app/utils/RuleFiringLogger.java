package utils;

import java.util.HashMap;
import java.util.Map;
import org.drools.definition.rule.Rule;
import org.drools.event.rule.*;
import org.drools.runtime.rule.Activation;

/**
 *
 * @author Maxim Kolchin
 */
public class RuleFiringLogger extends DefaultAgendaEventListener {

    @Override
    public void afterActivationFired(AfterActivationFiredEvent event) {
        Activation activation = event.getActivation();
        Rule rule = activation.getRule();
        System.out.println("Rule [" + rule.getPackageName() + "]"
                + "[" + rule.getName() +"]");
        System.out.println("Objects:");
        for(Object obj : activation.getObjects()) {
            System.out.println(obj);
        }
    }
}
