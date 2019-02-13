package com.snipzer.contact.controller.email;

import com.google.gson.Gson;
import com.snipzer.contact.entity.Email;
import com.snipzer.contact.service.EmailService;
import com.snipzer.contact.util.UrlUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = UrlUtil.EMAIL_SEND, value = UrlUtil.EMAIL_SEND_URL)
public class EmailSendController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Email email = new Gson().fromJson(request.getReader(), Email.class);
        EmailService.getInstance().sendEmail(email);
    }
}
