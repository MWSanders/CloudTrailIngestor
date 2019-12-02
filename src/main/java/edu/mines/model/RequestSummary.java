package edu.mines.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Matt on 5/9/2016.
 */
public class RequestSummary {

    Set<String> normalizedResourceNames = new HashSet<>();
    Long uniqueRequests;


    public Set<String> getNormalizedResourceNames() {
        return normalizedResourceNames;
    }

    public void setNormalizedResourceNames(Set<String> normalizedResourceNames) {
        this.normalizedResourceNames = normalizedResourceNames;
    }

    public Long getUniqueRequests() {
        return uniqueRequests;
    }

    public void setUniqueRequests(Long uniqueRequests) {
        this.uniqueRequests = uniqueRequests;
    }
}
