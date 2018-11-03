package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * cas参数自动配置类
 */
@Component
public class SpringCasAutoconfig {

    static final String separator = ",";

    @Value("${spring.cas.validate-filters}")
    private String validateFilters;
    @Value("${spring.cas.sign-out-filters}")
    private String signOutFilters;
    @Value("${spring.cas.auth-filters}")
    private String authFilters;
    @Value("${spring.cas.assertion-filters}")
    private String assertionFilters;
    @Value("${spring.cas.request-wrapper-filters}")
    private String requestWrapperFilters;
    @Value("${spring.cas.cas-server-url-prefix}")
    private String casServerUrlPrefix;
    @Value("${spring.cas.cas-server-login-url}")
    private String casServerLoginUrl;
    @Value("${spring.cas.server-name}")
    private String serverName;
    @Value("${spring.cas.use-session}")
    private boolean useSession = true;
    @Value("${spring.cas.redirect-after-validation}")
    private boolean redirectAfterValidation = true;

    public List<String> getValidateFilters() {
        return Arrays.asList(validateFilters.split(separator));
    }

    public void setValidateFilters(String validateFilters) {
        this.validateFilters = validateFilters;
    }

    public List<String> getSignOutFilters() {
        return Arrays.asList(signOutFilters.split(separator));
    }

    public void setSignOutFilters(String signOutFilters) {
        this.signOutFilters = signOutFilters;
    }

    public List<String> getAuthFilters() {
        return Arrays.asList(authFilters.split(separator));
    }

    public void setAuthFilters(String authFilters) {
        this.authFilters = authFilters;
    }

    public List<String> getAssertionFilters() {
        return Arrays.asList(assertionFilters.split(separator));
    }

    public void setAssertionFilters(String assertionFilters) {
        this.assertionFilters = assertionFilters;
    }

    public List<String> getRequestWrapperFilters() {
        return Arrays.asList(requestWrapperFilters.split(separator));
    }

    public void setRequestWrapperFilters(String requestWrapperFilters) {
        this.requestWrapperFilters = requestWrapperFilters;
    }

    public String getCasServerUrlPrefix() {
        return casServerUrlPrefix;
    }

    public void setCasServerUrlPrefix(String casServerUrlPrefix) {
        this.casServerUrlPrefix = casServerUrlPrefix;
    }

    public String getCasServerLoginUrl() {
        return casServerLoginUrl;
    }

    public void setCasServerLoginUrl(String casServerLoginUrl) {
        this.casServerLoginUrl = casServerLoginUrl;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public boolean isRedirectAfterValidation() {
        return redirectAfterValidation;
    }

    public void setRedirectAfterValidation(boolean redirectAfterValidation) {
        this.redirectAfterValidation = redirectAfterValidation;
    }

    public boolean isUseSession() {
        return useSession;
    }

    public void setUseSession(boolean useSession) {
        this.useSession = useSession;
    }

}

