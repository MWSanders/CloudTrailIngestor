package edu.mines.model;

import com.amazonaws.auth.policy.Resource;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Matt on 10/9/2015.
 */
public class ResourceListCompartor implements Comparator<List<Resource>> {

    @Override
    public int compare(List<Resource> o1, List<Resource> o2) {
        int list1 = hashCode(o1);
        int list2 = hashCode(o2);
        return list1 - list2;
    }

    private int hashCode(List<Resource> resources) {
        final int prime = 31;
        int result = 1;
        for (Resource r : resources) {
            result = result * prime + r.getId().hashCode();
        }
        return result;
    }

}
