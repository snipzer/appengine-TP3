package com.zenika.zencontact.resource;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.zenika.zencontact.domain.User;
import com.zenika.zencontact.persistence.UserRepository;
import com.zenika.zencontact.persistence.memcache.CacheService;
import com.zenika.zencontact.persistence.objectify.UserDaoObjectify;
import com.google.gson.Gson;
import com.zenika.zencontact.util.StringUtil;

import java.io.IOException;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "UserResource", value = "/api/v0/users")
public class UserResource extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    List<User> contacts = (List<User>) CacheService.getINSTANCE().get(StringUtil.CONTACTS_CACHE_KEY);
    if (contacts == null) {
      contacts = UserDaoObjectify.getInstance().getAll();
      CacheService.getINSTANCE().put(StringUtil.CONTACTS_CACHE_KEY, contacts, Expiration.byDeltaSeconds(240),
              MemcacheService.SetPolicy. ADD_ONLY_IF_NOT_PRESENT);
    }
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.getWriter().println(new Gson().toJsonTree(contacts).getAsJsonArray());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    CacheService.getINSTANCE().delete(StringUtil.CONTACTS_CACHE_KEY);
    User user = new Gson().fromJson(request.getReader(), User.class);
    user.id(UserDaoObjectify.getInstance().save(user));
    response.setContentType(StringUtil.APPLICATION_JSON_CHARSET_UTF_8);
    response.setStatus(201);
    response.getWriter().println(new Gson().toJson(user));
  }
}
