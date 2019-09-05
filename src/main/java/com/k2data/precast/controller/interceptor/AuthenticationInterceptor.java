package com.k2data.precast.controller.interceptor;

import com.k2data.precast.entity.dto.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户认证拦截器
 *
 * @author tianhao
 * @date 2018/12/14 14:36
 **/
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    public static final String USER_KEY = "user";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_KEY);

        if(null == user){
            response.sendRedirect("/login.html");
            return false;
        } else{
            return true;
        }
    }
}
