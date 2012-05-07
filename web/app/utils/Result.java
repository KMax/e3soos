package utils;

import java.util.HashMap;
import java.util.Map;
import logs.Firing;

/**
 *
 * @author Maxim Kolchin
 */
public class Result {

    private Map<String, Object> data = new HashMap<String, Object>();
    private Map<String, Firing> logs;

    public Result() {
    }

    public void setLogs(final Map<String, Firing> logs) {
        this.logs = logs;
    }

    public void setData(final String key, Object data) {
        this.data.put(key, data);
    }
}
