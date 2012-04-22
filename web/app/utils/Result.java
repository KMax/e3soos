package utils;

import java.util.Map;

/**
 *
 * @author Maxim Kolchin
 */
public class Result<T> {

    private T data;

    public void setData(final T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
