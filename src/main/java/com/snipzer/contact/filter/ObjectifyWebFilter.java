package com.snipzer.contact.filter;

import javax.servlet.annotation.WebFilter;
import com.googlecode.objectify.ObjectifyFilter;
import com.snipzer.contact.util.UrlUtil;

@WebFilter(urlPatterns = {UrlUtil.ALL_URL})
public class ObjectifyWebFilter extends ObjectifyFilter {}
