package controllers;

import models.RuleRunner;
import play.mvc.Controller;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;


public class Application extends Controller {

    public static void index(Requirements requirements) {
        Classification classification = null;
        if(requirements != null){
            RuleRunner runner = new RuleRunner();
            classification = runner.classify(requirements);
        }
        render(classification);
    }

}