package com.snipzer.contact.service;

import com.google.appengine.api.users.*;

public class AuthenticationService {

    private static AuthenticationService INSTANCE = new AuthenticationService();

    public static AuthenticationService getInstance() {
        return INSTANCE;
    }

    private UserService userService = UserServiceFactory.getUserService();

    public String getLoginURL(final String url) {
        return userService.createLoginURL(url);
    }

    public String getLogoutURL(final String url) {
        return userService.createLogoutURL(url);
    }

    public Boolean isAdmin() {
        return userService.isUserAdmin();
    }

    public User getUser() {
        return userService.getCurrentUser();
    }

    public String getUsername() {
        return getUser().getNickname();
    }
}
