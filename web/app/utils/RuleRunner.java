package utils;

import java.util.ArrayList;
import java.util.List;
import org.drools.KnowledgeBase;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.command.CommandFactory;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.Resource;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;

public class RuleRunner {

	private KnowledgeBase kBase;
	
	public RuleRunner() {
            
	}
        
        private static KnowledgeBase readKnowledgeBase() {
            KnowledgeAgentConfiguration kconf = KnowledgeAgentFactory.
                    newKnowledgeAgentConfiguration();
            kconf.setProperty("drools.agent.scanDirectories", "false");
            kconf.setProperty("drools.dialect.mvel.strict", "false");
            
            KnowledgeAgent kAgent = KnowledgeAgentFactory.
                    newKnowledgeAgent("Agent", kconf);
            Resource changeSet = ResourceFactory.
                    newClassPathResource("ChangeSet.xml", RuleRunner.class);
            
            kAgent.applyChangeSet(changeSet);
            KnowledgeBase base = kAgent.getKnowledgeBase();
            kAgent.dispose();
            return base;
        }
	
	public Classification classify(final Requirements reqs) {
            Classification c = new Classification();
            kBase = readKnowledgeBase();
            StatelessKnowledgeSession session = kBase.
                    newStatelessKnowledgeSession();
            session.addEventListener(new DebugAgendaEventListener());
            session.addEventListener(new DebugWorkingMemoryEventListener());
            List<Object> facts = new ArrayList<Object>();
            facts.add(reqs);
            facts.add(c);
            session.execute(CommandFactory.newInsertElements(facts));
            return c;
	}
}
