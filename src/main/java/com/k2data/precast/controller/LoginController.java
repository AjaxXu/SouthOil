package com.k2data.precast.controller;


import com.alibaba.fastjson.JSONObject;
import com.k2data.precast.controller.interceptor.AuthenticationInterceptor;
import com.k2data.precast.entity.dto.ReplyInfo;
import com.k2data.precast.entity.dto.User;
import com.k2data.precast.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * LoginController
 *
 * @author tianhao
 * @date 2018/12/14 10:11
 **/
@Controller()
@RequestMapping("/login")
public class LoginController {

    public static final Logger log = LoggerFactory.getLogger(LoginController.class);
    public static final int PARAM_NOT_FOUND = 701;
    public static final int USERNAME_NOT_FOUND = 702;
    public static final int USER_NOT_FOUND = 703;
    public static final int PASSWORD_ERROR = 704;

    @Autowired
    IUserService userService;

    @PostMapping("/userLogin")
    @ResponseBody
    public ReplyInfo login(@RequestBody JSONObject body, HttpServletRequest request, HttpServletResponse response) {
        Integer errorCode = null;
        try {
            if (null == body) {
                errorCode = PARAM_NOT_FOUND;
                return new ReplyInfo(false, errorCode);
            }

            String loginName = body.containsKey("loginName") ? body.getString("loginName") : null;
            String password = body.containsKey("password") ? body.getString("password") : null;

            if (!StringUtils.isNotBlank(loginName)) {
                errorCode = USERNAME_NOT_FOUND;
                return new ReplyInfo(false, errorCode);
            }

            User user = userService.getUserByLoginName(loginName);
            if (null == user) {
                errorCode = USER_NOT_FOUND;
                return new ReplyInfo(false, errorCode);
            }

            Boolean passwordAuthentication = password == null ? null == user.getPassword()
                    : password.equals(user.getPassword());
            if (passwordAuthentication) {
                loginSuccess(user, request);
                return new ReplyInfo(true, user);
            } else {
                errorCode = PASSWORD_ERROR;
                return new ReplyInfo(false, errorCode);
            }
        } catch (Exception e) {
            log.error("login error ,", e);
        }
        return new ReplyInfo(false, "unknown error!");
    }

    /**
     * 登陆检测
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping("/loginCheck")
    public ReplyInfo loginCheck(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(AuthenticationInterceptor.USER_KEY);
        boolean loginFlag = false;
        if (null != user) {
            loginFlag = true;
        }
        return new ReplyInfo(loginFlag, "loginCheck complete.");
    }

    /**
     * 登出
     *
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/logOut")
    public ReplyInfo logout(HttpServletRequest request) {
        request.getSession().removeAttribute(AuthenticationInterceptor.USER_KEY);
        return new ReplyInfo(true);
    }



    public void loginSuccess(User user, HttpServletRequest request) {
        request.getSession().setAttribute(AuthenticationInterceptor.USER_KEY, user);
    }

}
