package controllers;

import models.ClassificationSerializer;
import play.mvc.Controller;
import play.mvc.With;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import utils.RuleRunner;

@With(Secure.class)
public class Application extends Controller {
    
    public static void dashboard() {
        render();
    }

    public static void classify(Requirements requirements) {
        if(requirements != null) {
            Classification classification = RuleRunner.classify(requirements);
            renderJSON(classification, new ClassificationSerializer());
        }
        badRequest();
    }

}