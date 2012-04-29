package utils;

import java.util.List;
import java.util.Map;
import logs.Firing;

/**
 *
 * @author Maxim Kolchin
 */
public class Result {

    private List<String> data;
    private Map<String, Firing> logs;

    public Result(final List<String> d, final Map<String, Firing> ls) {
        this.data = d;
        this.logs = ls;
    }
}
