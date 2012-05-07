package controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import models.ClassificationSerializer;
import models.User;
import play.mvc.Controller;
import play.mvc.With;
import ru.ifmo.ailab.e3soos.facts.Classification;
import ru.ifmo.ailab.e3soos.facts.Requirements;
import ru.ifmo.ailab.e3soos.facts.Schema;
import utils.Result;
import utils.RuleRunner;

@With(Secure.class)
public class Application extends Controller {

    private static Gson gson;

    static {
        GsonBuilder builder = new GsonBuilder().
                registerTypeAdapter(Classification.class, new ClassificationSerializer());
        gson = builder.create();
    }

    public static void dashboard() {
        User user = User.find("byEmail", Security.connected()).first();
        render(user);
    }

    public static void synthesis(Requirements requirements) {
        if(requirements != null) {
            List<String> schemes = new ArrayList<String>();
            Classification classification = RuleRunner.classify(requirements);
            for(Schema scheme : RuleRunner.synthesis(classification)) {
                schemes.add(scheme.toString());
            }
            renderJSON("{\"classification\": " + gson.toJson(classification) + ", "
                    + "\"schemes\": " + gson.toJson(schemes) + "}");
        }
        badRequest();
    }

    public static void synthesisWithLogs(Requirements requirements) {
        if(requirements != null) {
            Classification classification = RuleRunner.classify(requirements);

            Result result = RuleRunner.synthesisWithLogs(classification);
            result.setData("classification", classification);
            renderJSON(result, new ClassificationSerializer());
        }
        badRequest();
    }
}