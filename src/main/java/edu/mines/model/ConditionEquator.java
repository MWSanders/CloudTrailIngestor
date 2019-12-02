package edu.mines.model;

import com.amazonaws.auth.policy.Condition;
import org.apache.commons.collections4.Equator;

public class ConditionEquator implements Equator<Condition> {

    @Override
    public boolean equate(Condition s1, Condition s2) {
        if (s1 == null && s2 == null)
            return true;
        if (s1 == null || s2 == null)
            return false;
        if (s1.getConditionKey().equals(s2.getConditionKey()) && s1.getType().equals(s2.getType()))
            if (s1.getValues().containsAll(s2.getValues()))
                return true;
        return false;
    }

    @Override
    public int hash(Condition condition) {
        int result = condition.getType() != null ? condition.getType().hashCode() : 0;
        result = 31 * result + (condition.getConditionKey() != null ? condition.getConditionKey().hashCode() : 0);
        result = 31 * result + (condition.getValues() != null ? condition.getValues().hashCode() : 0);
        return result;
    }

}