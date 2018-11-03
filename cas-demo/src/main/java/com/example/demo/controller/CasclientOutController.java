package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class CasclientOutController {

    @RequestMapping("/logoutDefault")
    public String logout1(HttpSession session) {
        // 直接退出，走默认退出方式
        return "redirect:https://leannain.com:8443/cas/logout";
    }

    @RequestMapping("/logoutCustom")
    public String logout2(HttpSession session) {
        // 退出登录后，跳转到退出成功的页面，不走默认页面
        return "redirect:https://leannain.com:8443/cas/logout?service=http://leannain.com:8081/logout/success";
    }

    @RequestMapping("/logout/success")
    @ResponseBody
    public String logout3(HttpSession session) {
        session.invalidate();
        return "系统2注销成功";
    }

}