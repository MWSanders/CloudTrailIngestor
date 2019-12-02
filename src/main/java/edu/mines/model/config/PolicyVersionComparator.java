package edu.mines.model.config;

import com.amazonaws.services.identitymanagement.model.PolicyVersion;

import java.util.Comparator;

/**
 * Created by Matt on 9/3/2017.
 */
public class PolicyVersionComparator implements Comparator<PolicyVersion> {
    @Override
    public int compare(PolicyVersion o1, PolicyVersion o2) {
        Long version1 = Long.parseLong(o1.getVersionId().substring(1));
        Long version2 = Long.parseLong(o2.getVersionId().substring(1));
        return Long.compare(version2, version1);
    }
}
