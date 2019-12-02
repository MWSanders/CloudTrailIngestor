package edu.mines.model.config;

import java.time.ZonedDateTime;
import java.util.Comparator;

/**
 * Created by Matt on 9/3/2017.
 */
public class ConfigItemCaptureTimeCompartor implements Comparator<Config> {
    @Override
    public int compare(Config o1, Config o2) {
        ZonedDateTime z1 = ZonedDateTime.parse(o1.getConfigurationItemCaptureTime());
        ZonedDateTime z2 = ZonedDateTime.parse(o2.getConfigurationItemCaptureTime());
        return z2.compareTo(z1);
    }
}
