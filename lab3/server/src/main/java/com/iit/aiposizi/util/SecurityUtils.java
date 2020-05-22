package com.iit.aiposizi.util;

import com.iit.aiposizi.security.UserDetailsImpl;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.userdetails.UserDetails;

import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@UtilityClass
public class SecurityUtils {

    public static UserDetailsImpl getCurrentUser() {
        var principal = getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetailsImpl) principal;
        }
        return null;
    }

}
