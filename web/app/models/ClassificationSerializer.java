package models;

import com.google.gson.*;
import java.lang.reflect.Type;
import ru.ifmo.ailab.e3soos.facts.Classification;

/**
 *
 * @author Maxim Kolchin
 */
public class ClassificationSerializer implements JsonSerializer<Classification> {

    public JsonElement serialize(Classification t, Type type, JsonSerializationContext jsc) {
        JsonObject element = new Gson().toJsonTree(t, type).getAsJsonObject();
        element.addProperty("r", t.getR());
        return element;
    }

}
