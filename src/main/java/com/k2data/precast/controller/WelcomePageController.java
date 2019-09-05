package com.k2data.precast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * WelcomPageController
 *
 * @author tianhao
 * @date 2018/12/18 15:38
 **/
@Controller
public class WelcomePageController {
    @GetMapping("/")
    public Object index() {
        return "/main.html";
    }
}
