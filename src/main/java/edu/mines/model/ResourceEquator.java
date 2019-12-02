package edu.mines.model;

import com.amazonaws.auth.policy.Resource;
import org.apache.commons.collections4.Equator;

public class ResourceEquator implements Equator<Resource> {

    @Override
    //TODO regex match on resource name
    public boolean equate(Resource resource, Resource t1) {
        return resource.getId().equals(t1.getId());
    }

    @Override
    public int hash(Resource resource) {
        return resource.getId().hashCode();
    }
}