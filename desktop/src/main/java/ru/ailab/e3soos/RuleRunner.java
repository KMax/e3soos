package ru.ailab.e3soos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.command.Command;
import org.drools.command.CommandFactory;
import org.drools.event.rule.DebugAgendaEventListener;
import org.drools.event.rule.DebugWorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatelessKnowledgeSession;

import ru.ailab.e3soos.model.Classification;
import models.Requirements;

public class RuleRunner {

	private KnowledgeBuilder builder;
	private KnowledgeBase knowledgeBase;
	
	public RuleRunner() {
		builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("ru/ailab/e3soos/rules/rules.drl", App.class), 
        		ResourceType.DRL);
        if(builder.hasErrors()){
        	System.err.println(builder.getErrors());
        }
        knowledgeBase = KnowledgeBaseFactory.newKnowledgeBase();
        knowledgeBase.addKnowledgePackages(builder.getKnowledgePackages());
	}
	
	public Classification classify(final Requirements reqs){
		Classification c = new Classification();
		StatelessKnowledgeSession session = knowledgeBase.newStatelessKnowledgeSession();
		session.addEventListener(new DebugAgendaEventListener());
        session.addEventListener(new DebugWorkingMemoryEventListener());
        List<Object> facts = new ArrayList<Object>();
        facts.add(reqs);
        facts.add(c);
        session.execute(CommandFactory.newInsertElements(facts));
        return c;
	}
}
