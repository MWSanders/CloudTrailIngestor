package edu.mines.model;

import com.amazonaws.auth.policy.Principal;
import org.apache.commons.collections4.Equator;

public class PrincipalEquator implements Equator<Principal> {

    @Override
    public boolean equate(Principal s1, Principal s2) {
        if (s1 == null && s2 == null)
            return true;
        if (s1 == null || s2 == null)
            return false;

        if (s1.getId() != null ? !s1.getId().equals(s2.getId()) : s2.getId() != null) return false;
        return s1.getProvider() != null ? s1.getProvider().equals(s2.getProvider()) : s2.getProvider() == null;
    }

    @Override
    public int hash(Principal principal) {
        int result = principal.getId() != null ? principal.getId().hashCode() : 0;
        result = 31 * result + (principal.getProvider() != null ? principal.getProvider().hashCode() : 0);
        return result;
    }

}