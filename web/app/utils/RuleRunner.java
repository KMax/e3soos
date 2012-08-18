package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logs.AgendaLogger;
import org.drools.KnowledgeBaseFactory;
import org.drools.agent.KnowledgeAgent;
import org.drools.agent.KnowledgeAgentConfiguration;
import org.drools.agent.KnowledgeAgentFactory;
import org.drools.builder.KnowledgeBuilderConfiguration;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.command.CommandFactory;
import org.drools.event.knowledgeagent.AfterResourceProcessedEvent;
import org.drools.event.knowledgeagent.KnowledgeAgentEventListener;
import org.drools.event.rule.DefaultKnowledgeAgentEventListener;
import org.drools.io.ResourceChangeScannerConfiguration;
import org.drools.io.ResourceFactory;
import org.drools.io.impl.UrlResource;
import org.drools.runtime.ExecutionResults;
import org.drools.runtime.StatelessKnowledgeSession;
import org.drools.runtime.rule.QueryResultsRow;
import org.drools.runtime.rule.impl.NativeQueryResults;
import play.Logger;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import ru.ifmo.ailab.e3soos.facts.Schema;

public abstract class RuleRunner {

    private static KnowledgeAgent kAgentClassification;
    private static KnowledgeAgent kAgentSynthesis;
    private static KnowledgeAgentConfiguration kAgentConf;
    private static KnowledgeBuilderConfiguration kBuilderConf;
    private static ResourceChangeScannerConfiguration rScannerConf;
    private static Map<String, Date> dates = new HashMap();

    /**
     * @see #dispose()
     */
    public static void init() {

        KnowledgeAgentEventListener listener = new DefaultKnowledgeAgentEventListener() {
            @Override
            public void afterResourceProcessed(AfterResourceProcessedEvent event) {
                UrlResource pkg = (UrlResource) event.getResource();
                try {
                    dates.put(pkg.getURL().getPath().split("/")[4], new Date(pkg.getLastModified()));
                } catch (IOException ex) {
                    Logger.fatal(ex, ex.getLocalizedMessage());
                }
            }
        };

        //Create a knowledge agent configuration
        kAgentConf = KnowledgeAgentFactory.newKnowledgeAgentConfiguration();
        kAgentConf.setProperty("drools.agent.scanDirectories", "false");

        kBuilderConf = KnowledgeBuilderFactory.newKnowledgeBuilderConfiguration();
        kBuilderConf.setProperty("drools.dialect.mvel.strict", "false");

        //Create the resource change scanner configuration
        rScannerConf = ResourceFactory.getResourceChangeScannerService().newResourceChangeScannerConfiguration();
        rScannerConf.setProperty("drools.resource.scanner.interval", "300");

        //Create a classification knowledge agent
        kAgentClassification = KnowledgeAgentFactory.newKnowledgeAgent("classification", kAgentConf);
        kAgentClassification.addEventListener(listener);
        kAgentClassification.applyChangeSet(
                ResourceFactory.newClassPathResource("e3soos.classification.xml", RuleRunner.class));

        //Create a synthesis knowledge agent
        kAgentSynthesis = KnowledgeAgentFactory.newKnowledgeAgent("synthesis",
                KnowledgeBaseFactory.newKnowledgeBase(), kAgentConf, kBuilderConf);
        kAgentSynthesis.addEventListener(listener);
        kAgentSynthesis.applyChangeSet(
                ResourceFactory.newClassPathResource("e3soos.basic.xml", RuleRunner.class));
        kAgentSynthesis.applyChangeSet(
                ResourceFactory.newClassPathResource("e3soos.fast.xml", RuleRunner.class));
        kAgentSynthesis.applyChangeSet(
                ResourceFactory.newClassPathResource("e3soos.generation.xml", RuleRunner.class));
        kAgentSynthesis.applyChangeSet(
                ResourceFactory.newClassPathResource("e3soos.wideangular.xml", RuleRunner.class));
        kAgentSynthesis.applyChangeSet(
                ResourceFactory.newClassPathResource("e3soos.corrective.xml", RuleRunner.class));

        //Set the resource change scanner configuration
        ResourceFactory.getResourceChangeScannerService().configure(rScannerConf);
        //Start the resource change scanner
        ResourceFactory.getResourceChangeNotifierService().start();
        ResourceFactory.getResourceChangeScannerService().start();
    }

    public static Map<String, Date> getDates() {
        return dates;
    }

    public static Classification classify(final Requirements reqs) {
        Classification c = new Classification();
        StatelessKnowledgeSession session = kAgentClassification.newStatelessKnowledgeSession();
        List<Object> facts = new ArrayList<Object>();
        facts.add(reqs);
        facts.add(c);
        session.execute(CommandFactory.newInsertElements(facts));
        return c;
    }

    public static List<Schema> synthesis(final Classification classification) {
        StatelessKnowledgeSession session = kAgentSynthesis.newStatelessKnowledgeSession();
        List cmds = new ArrayList();
        cmds.add(CommandFactory.newInsert(classification));
        cmds.add(CommandFactory.newFireAllRules());
        cmds.add(CommandFactory.newQuery("getSchemas", "get the all schemas"));

        ExecutionResults results = (ExecutionResults) session.execute(
                CommandFactory.newBatchExecution(cmds));

        NativeQueryResults schemasResults = (NativeQueryResults) results.getValue("getSchemas");
        List<Schema> schemas = new ArrayList<Schema>();
        for (QueryResultsRow row : schemasResults) {
            schemas.add((Schema) row.get("schema"));
        }
        return schemas;
    }

    public static Result synthesisWithLogs(final Classification classification) {
        StatelessKnowledgeSession session = kAgentSynthesis.newStatelessKnowledgeSession();
        AgendaLogger alogger = new AgendaLogger();
        session.addEventListener(alogger);

        List cmds = new ArrayList();
        cmds.add(CommandFactory.newInsert(classification));
        cmds.add(CommandFactory.newFireAllRules());
        cmds.add(CommandFactory.newQuery("getSchemas", "get the all schemas"));

        ExecutionResults results = (ExecutionResults) session.execute(
                CommandFactory.newBatchExecution(cmds));

        NativeQueryResults schemasResults = (NativeQueryResults) results.getValue("getSchemas");
        ArrayList<String> schemas = new ArrayList<String>();
        for (QueryResultsRow row : schemasResults) {
            schemas.add(((Schema) row.get("schema")).toString());
        }
        Result result = new Result();
        result.setLogs(alogger.getFirings());
        result.setData("schemes", schemas);
        return result;
    }

    /**
     * @see #init()
     */
    public static void dispose() {
        //Stop the resource change scanner
        ResourceFactory.getResourceChangeScannerService().stop();
        ResourceFactory.getResourceChangeNotifierService().stop();

        //Dispose the knowledge base agents
        kAgentClassification.dispose();
        kAgentSynthesis.dispose();
    }
}
