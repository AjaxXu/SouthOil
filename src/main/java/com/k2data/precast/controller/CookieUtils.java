package com.k2data.precast.controller;

import com.k2data.precast.controller.interceptor.AuthenticationInterceptor;
import com.k2data.precast.entity.dto.User;

import javax.servlet.http.HttpServletRequest;

/**
 * UserUtils
 *
 * @author tianhao
 * @date 2018/12/18 10:09
 **/
public class CookieUtils {
    public static User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(AuthenticationInterceptor.USER_KEY);
    }
}
