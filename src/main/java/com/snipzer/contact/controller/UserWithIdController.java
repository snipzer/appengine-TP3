package com.snipzer.contact.controller;

import com.snipzer.contact.dao.UserDaoObjectify;
import com.snipzer.contact.entity.User;
import com.snipzer.contact.service.PhotoService;
import com.snipzer.contact.util.CacheUtil;
import com.google.gson.Gson;
import com.snipzer.contact.util.StringUtil;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserWithIdController", value = "/api/v0/users/*")
public class UserWithIdController extends HttpServlet {

  private Long getId(HttpServletRequest request) {
    String pathInfo = request.getPathInfo();
    String[] pathParts = pathInfo.split("/");
    if(pathParts.length == 0) {
        return null;
    }
    return Long.valueOf(pathParts[1]);
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Long id = getId(request);
    if(id == null) {
        response.setStatus(404);
        return;
    }
    User user = UserDaoObjectify.getInstance().get(id);
    PhotoService.getInstance().prepareUploadURL(user);
    PhotoService.getInstance().prepareDownloadURL(user);
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.getWriter().println(new Gson().toJson(user));
  }

  @Override
  public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    CacheUtil.getINSTANCE().delete(StringUtil.CONTACTS_CACHE_KEY);
    Long id = getId(request);
    if(id == null) {
        response.setStatus(404);
        return;
    }
    User user = new Gson().fromJson(request.getReader(), User.class);
    UserDaoObjectify.getInstance().save(user);
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.getWriter().println(new Gson().toJson(user));
  }

  @Override
  public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    CacheUtil.getINSTANCE().delete(StringUtil.CONTACTS_CACHE_KEY);
    Long id = getId(request);
    if(id == null) {
        response.setStatus(404);
        return;
    }
    UserDaoObjectify.getInstance().delete(id);
  }
}

