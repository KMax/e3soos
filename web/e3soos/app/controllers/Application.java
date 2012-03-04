package controllers;

import java.io.IOException;
import play.mvc.Controller;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import utils.RuleRunner;


public class Application extends Controller {
    
    private static final RuleRunner ruleRunner = new RuleRunner();

    public static void index(Requirements requirements) throws IOException {
        Classification classification = null;
        if(requirements != null){
            classification = ruleRunner.classify(requirements);
        }
        render(classification);
    }

}