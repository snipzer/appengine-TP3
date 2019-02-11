package com.snipzer.contact.service;

import com.google.appengine.api.users.*;

public class AuthenticationService {

    private static AuthenticationService INSTANCE = new AuthenticationService();

    public static AuthenticationService getINSTANCE() {
        return INSTANCE;
    }

    private UserService userService = UserServiceFactory.getUserService();

    public String getLoginUrl(final String url) {
        return userService.createLoginURL(url);
    }

    public String getLogoutUrl(final String url) {
        return userService.createLogoutURL(url);
    }

    public Boolean isUserAdmin() {
        return userService.isUserAdmin();
    }

    public User getUser() {
        return userService.getCurrentUser();
    }

    public String getUsername() {
        return getUser().getNickname();
    }
}
