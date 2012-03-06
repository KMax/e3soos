package controllers;

import play.mvc.Controller;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import utils.RuleRunner;


public class Application extends Controller {
    
    private static final RuleRunner ruleRunner = new RuleRunner();

    public static void index(Requirements requirements) {
        Classification classification = null;
        if(requirements != null) {
            System.out.println(requirements.getSpectralRange());
            classification = ruleRunner.classify(requirements);
        }
        render(classification);
    }

}