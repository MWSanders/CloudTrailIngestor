package edu.mines.model;

import com.amazonaws.auth.policy.Resource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Matt on 10/9/2015.
 */
public class ResourceListComparable implements Comparable<List<String>> {
    private final List<String> list1;

    public static List<String> convertResourceList(List<Resource> list1) {
        List<String> results = new ArrayList<>();
        for (Resource r : list1) {
            results.add(r.getId());
        }
        return results;
    }

    public ResourceListComparable(List<String> list1) {
        this.list1 = list1;
    }

    public ResourceListComparable(Set<String> list1) {
        this.list1 = new ArrayList<>();
        this.list1.addAll(list1);
    }

    public List<String> getList1() {
        return list1;
    }

    public int compare(List<String> o1) {
        return this.compareTo(o1);
    }

    @Override
    public String toString() {
        Collections.sort(list1);
        //String result = list1.stream().map(Resource::getId).collect(Collectors.joining(", "));
        //. is not allow for mongo keys so replace with /
        String result = list1.stream().map(resource -> resource.replace(".", "/")).collect(Collectors.joining(", "));
        return result;
    }

    @Override
    public boolean equals(Object o1) {
        if (o1 == this) {
            return true;
        }
        if (!(o1 instanceof ResourceListComparable)) {
            return false;
        }
        ResourceListComparable other = (ResourceListComparable) o1;
        return 0 == (this.compareTo(other.getList1()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        for (String r : list1) {
            result = result * prime + r.hashCode();
        }
        return result;
    }

    @Override
    public int compareTo(List<String> o) {
        return this.hashCode() - new ResourceListComparable(o).hashCode();
    }
}
