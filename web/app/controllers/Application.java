package controllers;

import play.mvc.Controller;
import play.mvc.With;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import utils.RuleRunner;

@With(Secure.class)
public class Application extends Controller {
    
    private static final RuleRunner ruleRunner = new RuleRunner();
    
    public static void index(Requirements requirements) {
        Classification classification = null;
        if(requirements != null) {
            classification = ruleRunner.classify(requirements);
        }
        render(classification);
    }

}