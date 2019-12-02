package edu.mines.model;

/**
 * Created by Matt on 10/9/2015.
 */
public class MetricListKey {
    private final String key;

    public MetricListKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key.toString().replace(".", "/");
    }

    public static String makeKey(String key) {
        return key.toString().replace(".", "/");
    }
}
