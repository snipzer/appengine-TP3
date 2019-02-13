package com.snipzer.contact.service;

import com.google.appengine.api.urlfetch.*;
import com.snipzer.contact.util.StringUtil;

import java.net.URL;

public class PartnerBirthdateService {

    private static PartnerBirthdateService INSTANCE = new PartnerBirthdateService();
    public static PartnerBirthdateService getInstance() {
        return INSTANCE;
    }

    private static final String SERVICE_URL = "http://zenpartenaire.appspot.com/zenpartenaire";

    private PartnerBirthdateService() {
    }

    public String findBirthdate(String firstname, String lastname) {
        String birthdate = null;
        try {
            URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
            URL url = new URL(SERVICE_URL);
            HTTPRequest postRequest = new HTTPRequest(url, HTTPMethod.POST, FetchOptions.Builder.withDeadline(30));
            String payload = firstname + StringUtil.SPACE + lastname;
            postRequest.setPayload(payload.getBytes());
            HTTPResponse response = fetcher.fetch(postRequest);
            if(response.getResponseCode() != 200) {
                return null;
            }
            birthdate = new String(response.getContent()).trim();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return birthdate;
    }
}
