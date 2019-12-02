package edu.mines.model;

import com.amazonaws.auth.policy.Resource;
import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by Matt on 4/24/2016.
 */
@Embedded
public class MyResource extends Resource {
    private String resource;

    public MyResource(String resource) {
        super(resource);
    }

    public MyResource() {
        super("");
    }
}
