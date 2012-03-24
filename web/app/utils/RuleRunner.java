package utils;

import java.util.ArrayList;
import java.util.List;
import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.command.CommandFactory;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;

public abstract class RuleRunner {

	private static KnowledgeBase kbClassification;
    private static KnowledgeBase kbSelection;

    private static final KnowledgeAgentConfiguration kAgentConf;

    static {
        kAgentConf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        kAgentConf.setProperty("drools.agent.scanDirectories", "false");

        kbClassification = createKnowledgeBase(ResourceFactory.
                newClassPathResource("classification.xml",
                RuleRunner.class));
        kbSelection = createKnowledgeBase(ResourceFactory.
                newClassPathResource("selection.xml", RuleRunner.class));
    }

    private static KnowledgeBase createKnowledgeBase(Resource changeSet) {
        KnowledgeAgent kAgent = KnowledgeAgentFactory.
                newKnowledgeAgent("Agent", kAgentConf);
        kAgent.monitorResourceChangeEvents(false);
        kAgent.applyChangeSet(changeSet);
        KnowledgeBase base = kAgent.getKnowledgeBase();
        kAgent.dispose();
        return base;
    }

	public static Classification classify(final Requirements reqs) {
            Classification c = new Classification();
            StatelessKnowledgeSession session = kbClassification.
                    newStatelessKnowledgeSession();
            List<Object> facts = new ArrayList<Object>();
            facts.add(reqs);
            facts.add(c);
            session.execute(CommandFactory.newInsertElements(facts));
            return c;
	}

    public static void synthesis(final Classification classification) {
        StatefulKnowledgeSession session = kbSelection.newStatefulKnowledgeSession();
        session.execute(CommandFactory.newInsert(classification));
        session.dispose();
    }
}
