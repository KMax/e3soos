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
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.rule.QueryResultsRow;
import org.drools.runtime.rule.impl.NativeQueryResults;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import ru.ifmo.ailab.e3soos.facts.Schema;

public abstract class RuleRunner {

	private static KnowledgeBase kbClassification;
    private static KnowledgeBase kbSynthesis;

    private static final KnowledgeAgentConfiguration kAgentConf;

    static {
        kAgentConf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        kAgentConf.setProperty("drools.agent.scanDirectories", "false");

        kbClassification = createKnowledgeBase("classification",
                new Resource[] {
                    ResourceFactory.newClassPathResource("e3soos.classification.xml", RuleRunner.class)
                });
        kbSynthesis = createKnowledgeBase("synthesis",
                new Resource[] {
                    ResourceFactory.newClassPathResource("e3soos.basic.xml", RuleRunner.class),
                    ResourceFactory.newClassPathResource("e3soos.fast.xml", RuleRunner.class),
                    ResourceFactory.newClassPathResource("e3soos.generation.xml", RuleRunner.class),
                    ResourceFactory.newClassPathResource("e3soos.wideangular.xml", RuleRunner.class)
                });
    }

    private static KnowledgeBase createKnowledgeBase(String name, Resource[] changeSets) {
        KnowledgeAgent kAgent = KnowledgeAgentFactory.
                newKnowledgeAgent(name, kAgentConf);
        for(Resource changeSet : changeSets) {
            kAgent.applyChangeSet(changeSet);
        }
        KnowledgeBase base = kAgent.getKnowledgeBase();
        kAgent.dispose();
        return base;
    }

	public static Classification classify(final Requirements reqs) {
            Classification c = new Classification();
            StatelessKnowledgeSession session = kbClassification.
                    newStatelessKnowledgeSession();
            //session.addEventListener(new DebugAgendaEventListener());
            //session.addEventListener(new DebugWorkingMemoryEventListener());
            List<Object> facts = new ArrayList<Object>();
            facts.add(reqs);
            facts.add(c);
            session.execute(CommandFactory.newInsertElements(facts));
            return c;
	}

    public static List<Schema> synthesis(final Classification classification) {
        List<Schema> schemas = new ArrayList<Schema>();
        StatelessKnowledgeSession session = kbSynthesis.newStatelessKnowledgeSession();
        //session.addEventListener(new DebugAgendaEventListener());
        //session.addEventListener(new DebugWorkingMemoryEventListener());
        List cmds = new ArrayList();
        cmds.add(CommandFactory.newInsert(classification));
        cmds.add(CommandFactory.newFireAllRules());
        cmds.add(CommandFactory.newQuery("getElements", "get the all elements"));
        cmds.add(CommandFactory.newQuery("getSchemas", "get the all schemas"));

        ExecutionResults results = (ExecutionResults) session.execute(
                CommandFactory.newBatchExecution(cmds));

        NativeQueryResults elements = (NativeQueryResults) results.getValue("getElements");
//        System.out.println("Elements:");
//        for(QueryResultsRow row : elements){
//            System.out.println(row.get("element").toString());
//        }
        NativeQueryResults schemasResults = (NativeQueryResults) results.getValue("getSchemas");
        for(QueryResultsRow row : schemasResults) {
            schemas.add((Schema)row.get("schema"));
        }
        return schemas;
    }
}
