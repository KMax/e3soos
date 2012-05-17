package tags;

import controllers.Security;
import groovy.lang.Closure;
import java.io.PrintWriter;
import java.util.Map;
import models.User;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;

/**
 *
 * @author Maxim Kolchin
 */
@FastTags.Namespace("e3soos")
public class Tags extends FastTags {

    public static void _userName(Map<?,?> args, Closure body, PrintWriter out,
            ExecutableTemplate template, int fromLine) {
        User user = User.find("byEmail", Security.connected()).first();
        out.println(user.fullName);
    }

}
