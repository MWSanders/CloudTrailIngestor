package edu.mines.model;

import com.amazonaws.auth.policy.Resource;

import java.util.Comparator;

/**
 * Created by Matt on 10/9/2015.
 */
public class ResourceCompartor implements Comparator<Resource> {

    @Override
    public int compare(Resource o1, Resource o2) {
        int list1 = hashCode(o1);
        int list2 = hashCode(o2);
        return list1 - list2;
    }

    private int hashCode(Resource resource) {
        return resource.getId().hashCode();
    }

}
