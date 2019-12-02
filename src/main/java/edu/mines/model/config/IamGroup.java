package edu.mines.model.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Matt on 9/5/2017.
 */
public class IamGroup extends Config {
    @JsonProperty("configuration")
    private GroupConfiguration configuration;

    public GroupConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(GroupConfiguration configuration) {
        this.configuration = configuration;
    }
}
