package com.gov.iti.presentation;

import com.gov.iti.business.entities.User;

public class AdminValidator {
    public static final String ADMIN_USERNAME = "john_doe";

    public static boolean IS_ADMIN(User user) {
        String username = user.getUsername();
        return username.equals(ADMIN_USERNAME);
    }
}
