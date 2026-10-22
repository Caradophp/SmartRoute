package com.faesa.smartRoute.util;

import com.faesa.smartRoute.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class Util {

    public static User getCurrentUser() {
        return (User) Objects.requireNonNull(SecurityContextHolder
                .getContext()
                .getAuthentication())
                .getPrincipal();
    }

}
