package com.snipzer.contact.controller;

import com.snipzer.contact.entity.Message;
import com.snipzer.contact.util.StringUtil;
import org.joda.time.DateTime;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "HelloController", value = "/api/message")
public class HelloController extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String who = request.getParameter(StringUtil.WHO);
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.getWriter().println(new Gson().toJson(sayHello(who)));
  }

  public Message sayHello(String who) {
    Message msg = new Message();
    msg.message = String.format(
            "hello %s, it's %s",
            who, DateTime.now().toString(StringUtil.HH_MM_SS));
    return msg;
  }
}
