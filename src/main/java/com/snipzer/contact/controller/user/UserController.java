package com.snipzer.contact.controller.user;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.snipzer.contact.dao.UserDaoObjectify;
import com.snipzer.contact.entity.User;
import com.snipzer.contact.util.UrlUtil;
import com.snipzer.contact.util.CacheUtil;
import com.google.gson.Gson;
import com.snipzer.contact.util.StringUtil;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = UrlUtil.USER, value = UrlUtil.USER_URL)
public class UserController extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    List<User> contacts = (List<User>) CacheUtil.getInstance().get(StringUtil.CONTACTS_CACHE_KEY);
    if (contacts == null) {
      contacts = UserDaoObjectify.getInstance().getAll();
      CacheUtil.getInstance().put(StringUtil.CONTACTS_CACHE_KEY, contacts, Expiration.byDeltaSeconds(240),
              MemcacheService.SetPolicy. ADD_ONLY_IF_NOT_PRESENT);
    }
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.getWriter().println(new Gson().toJsonTree(contacts).getAsJsonArray());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    CacheUtil.getInstance().delete(StringUtil.CONTACTS_CACHE_KEY);
    User user = new Gson().fromJson(request.getReader(), User.class);
    user.id(UserDaoObjectify.getInstance().save(user));
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.setStatus(201);
    response.getWriter().println(new Gson().toJson(user));
  }
}
