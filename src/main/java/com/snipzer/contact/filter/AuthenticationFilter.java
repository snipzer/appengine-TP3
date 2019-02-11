package com.snipzer.contact.filter;

import com.snipzer.contact.service.AuthenticationService;
import com.snipzer.contact.util.StringUtil;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebFilter(urlPatterns = {"api/v0/users/*"})
public class AuthenticationFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AuthenticationFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest)request);
        HttpServletResponse res = ((HttpServletResponse)response);
        LOG.log(Level.INFO, StringUtil.FILTRE_D_AUTHENTIFICATION);
        String pathInfo = req.getPathInfo();
        if(pathInfo != null) {
            String[] pathParts = pathInfo.split(StringUtil.SLASH);
            //only admin can delete
            if (StringUtil.DELETE.equals(req.getMethod()) && !AuthenticationService.getInstance().isAdmin()) {
                res.setStatus(403);
                return;
            }
            if(AuthenticationService.getInstance().getUser() != null) {
                res.setHeader(StringUtil.USERNAME, AuthenticationService.getInstance().getUsername());
                res.setHeader(StringUtil.LOGOUT, AuthenticationService.getInstance().getLogoutURL(StringUtil.CLEAR));
            } else {
                //only authent users can edit
                res.setHeader(StringUtil.LOCATION, AuthenticationService.getInstance().getLoginURL(StringUtil.EDIT + pathParts[1]));
                res.setHeader(StringUtil.LOGOUT, AuthenticationService.getInstance().getLogoutURL(StringUtil.CLEAR));
                res.setStatus(401);
                return;
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}