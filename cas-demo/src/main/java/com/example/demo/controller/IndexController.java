package com.example.demo.controller;

import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
public class IndexController {

    @RequestMapping(value = {"/home", "/index"})
    public String index(HttpServletRequest request) {

        //获取cas给我们传递回来的对象，这个对象在Session中,session的 key是 CONST_CAS_ASSERTION
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        Principal principal = assertion.getPrincipal();
        String loginName = principal.getName();
        System.out.printf("登录用户名:%s\r\n", loginName);

        return "/index.html";
    }
}