package com.example.demo.controller;


import com.example.demo.service.UserService;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/")
    public String getUserInfo(HttpServletRequest request, Model model) {
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        AttributePrincipal principal = assertion.getPrincipal();
        String accountName = principal.getName();
        System.out.println("当前登录的用户是"+accountName);
        model.addAttribute("username",accountName);
        return "index";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:https://leannian:8443/logout?service=https://leannain:8443/cas/login";
    }

}

