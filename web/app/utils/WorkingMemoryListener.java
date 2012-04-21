package utils;

import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;

/**
 *
 * @author Maxim Kolchin
 */
public class WorkingMemoryListener implements WorkingMemoryEventListener {


    public void objectInserted(ObjectInsertedEvent event) {
        System.out.println("Inserted: " + event.getObject());
    }

    public void objectUpdated(ObjectUpdatedEvent event) {
        System.out.println("Updated:" + event.getObject());
    }

    public void objectRetracted(ObjectRetractedEvent event) {
        System.out.println("Retracted:" + event.getOldObject());
    }
}
