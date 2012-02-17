package controllers;

import models.Classification;
import models.Requirements;
import models.RuleRunner;
import play.mvc.Controller;


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