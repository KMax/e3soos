package ru.ifmo.ailab.e3soos.facts;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public abstract class ElementFactory {

    private static final Pattern FORMAT = Pattern.compile(
            "^(\\w{1})(\\d{1})(\\w{1})(\\d{1})(\\w{1})$");

    public static Element newElement(final String pattern) {
        Matcher matcher = FORMAT.matcher(pattern);
        if(matcher.matches()) {
            Element element = new Element();
            element.setElementType(ElementType.parse(matcher.group(1)));
            element.setFirstSurfaceZone(Integer.parseInt(matcher.group(2)));
            element.setFirstSurfaceType(SurfaceType.parse(matcher.group(3)));
            element.setSecondSurfaceZone(Integer.parseInt(matcher.group(4)));
            element.setSecondSurfaceType(SurfaceType.parse(matcher.group(5)));
            return element;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
