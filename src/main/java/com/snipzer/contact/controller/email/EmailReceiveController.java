package com.snipzer.contact.controller.email;

import com.snipzer.contact.service.EmailService;
import com.snipzer.contact.util.StringUtil;
import com.snipzer.contact.util.UrlUtil;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = UrlUtil.EMAIL_RECEIVE, value = UrlUtil.EMAIL_RECEIVE_URL)
@ServletSecurity(@HttpConstraint(rolesAllowed = {StringUtil.ADMIN}))
public class EmailReceiveController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        EmailService.getInstance().logEmail(request);
    }
}
