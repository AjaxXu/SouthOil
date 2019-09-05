package com.k2data.precast.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.k2data.precast.controller.interceptor.AuthenticationInterceptor;
import com.k2data.precast.entity.dto.*;
import com.k2data.precast.service.UserDiagramServiceI;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author tianhao
 * @date 2018/12/5
 */
@RestController
public class UserDiagramController {
    private static Logger log = LoggerFactory.getLogger(UserDiagramController.class);
    @Autowired
    UserDiagramServiceI userDiagramServiceI;

    public static ReentrantLock lock = new ReentrantLock(true);

    @PostMapping("/queryForPage")
    public ReplyInfo queryForPage(@RequestBody PageParam pageParam, HttpServletRequest request, HttpServletResponse response) {
        User user = CookieUtils.getUser(request);
        PageResult<UserDiagramDo> result = userDiagramServiceI.queryForPage(pageParam, user.getUserId());
        ReplyInfo replyInfo = new ReplyInfo(true, result);
        return replyInfo;
    }

    @PostMapping("/executeModelAnalysis")
    public ReplyInfo executeModelAnalysis(@RequestBody JSONObject body, HttpServletRequest request,
                                          HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(AuthenticationInterceptor.USER_KEY);
        String remoteAddress = request.getRemoteAddr();
        Boolean lockFlag = false;
        try {
            lockFlag = lock.tryLock();
            if (lockFlag) {
                log.info("User[{}],Ip[{}],executeModelAnalysis",user.getUserName(),remoteAddress);
                userDiagramServiceI.exeModelAnalysis(body, user.getUserId());
            }
        } catch (Exception e) {
            log.error("executeModelAnalysis has some errors,exception:", e);
        } finally {
            if (lockFlag) {
                lock.unlock();
            }
        }
        if (lockFlag) {
            return new ReplyInfo(true, "success");
        } else {
            return new ReplyInfo(false, "in use");
        }


    }

    @PostMapping("/updateModelScoreRadio")
    public ReplyInfo updateModelScoreRadio(@RequestBody JSONObject body, HttpServletRequest request) {
        Integer radio = body.containsKey("value") ? body.getInteger("value") : null;
        JSONArray specialMetrics = body.containsKey("specialMetrics") ? body.getJSONArray("specialMetrics") : null;
        User user = CookieUtils.getUser(request);
        if (null != radio) {
            userDiagramServiceI.updateModelScoreRadio(radio, user.getUserId());
            userDiagramServiceI.updateSpecialMetrecs(specialMetrics.toString(), user.getUserId());
        }
        return new ReplyInfo(true, "success");
    }

    @PostMapping("/getModelScoreRadio")
    public ReplyInfo getModelScoreRadio(@RequestBody JSONObject body, HttpServletRequest request) {
        User user = CookieUtils.getUser(request);
        Double modalScoreRadio = userDiagramServiceI.getModalScoreRadio(user.getUserId());
        return new ReplyInfo(true, modalScoreRadio);
    }


    @PostMapping("/getSpecialMetrics")
    public ReplyInfo getSpecialMetrics(@RequestBody JSONObject body, HttpServletRequest request) {
        User user = CookieUtils.getUser(request);
        String specialMetrecs = userDiagramServiceI.getSpecialMetrecs(user.getUserId());
        return new ReplyInfo(true, specialMetrecs, "success");
    }
}
