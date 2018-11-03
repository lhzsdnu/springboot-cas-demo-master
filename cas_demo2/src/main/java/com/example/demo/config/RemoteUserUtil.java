package com.example.demo.config;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.util.AssertionHolder;
import org.jasig.cas.client.validation.Assertion;

import javax.servlet.http.HttpServletRequest;

public class RemoteUserUtil {

    public static Boolean hasLogin() {
        return AssertionHolder.getAssertion() != null;
    }
    
    /**
     * 从cas中获取用户名
     */
    public static String getAccountNameFromCas(HttpServletRequest request) {
        Assertion assertion = (Assertion) request.getSession().getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION);
        AttributePrincipal principal = assertion.getPrincipal();
        return principal.getName();
    }


}