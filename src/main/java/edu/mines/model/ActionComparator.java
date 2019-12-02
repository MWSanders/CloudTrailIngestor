package edu.mines.model;

import com.amazonaws.auth.policy.Action;

import java.util.Comparator;

/**
 * Created by Matt on 8/12/2017.
 */
public class ActionComparator implements Comparator<com.amazonaws.auth.policy.Action> {
    @Override
    public int compare(Action o1, Action o2) {
        return o1.getActionName().compareTo(o2.getActionName());
    }
}
