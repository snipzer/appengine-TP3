package com.snipzer.contact.util;

public class UrlUtil {
    public static final String USER = "UserController";
    public static final String USER_URL = "/api/v0/users";

    public static final String USERID = "UserWithIdController";
    public static final String USERID_URL = "/api/v0/users/*";

    public static final String PHOTO = "PhotoController";
    public static final String PHOTO_URL = "/api/v0/photo/*";

    public static final String HELLO = "HelloController";
    public static final String HELLO_URL = "/api/message";

    public static final String EMAIL_RECEIVE = "EmailReceiveController";
    public static final String EMAIL_RECEIVE_URL = "/_ah/mail/*";

    public static final String EMAIL_SEND = "EmailSendController";
    public static final String EMAIL_SEND_URL = "api/v0/email";

    public static final String ALL_URL = "/*";
}
