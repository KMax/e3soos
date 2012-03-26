package controllers;

import java.util.ArrayList;
import java.util.List;
import models.ClassificationSerializer;
import play.mvc.Controller;
import play.mvc.With;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import ru.ifmo.ailab.e3soos.facts.Schema;
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

    public static void synthesis(Classification classification) {
        if(classification != null) {
            List<String> codes = new ArrayList<String>();
            for(Schema schema : RuleRunner.synthesis(classification)) {
                codes.add(schema.toString());
            }
            renderJSON(codes);
        }
        badRequest();
    }

}