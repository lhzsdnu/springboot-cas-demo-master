package com.example.demo.config;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

/**
 * cas配置类
 */
@Configuration
public class CasConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SpringCasAutoconfig autoconfig;

    private static boolean casEnabled = true;

    public CasConfig() {
        System.out.println("@@@@@@@@@测试是否使用此类");
    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //指定密码加密所使用的加密器为passwordEncoder()
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        //对于这些静态文件，忽略拦截
        web.ignoring().antMatchers("/login","/global/**","/css/**", "/img/**", "/js/**", "/plugins/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()//配置安全策略
                .antMatchers("/").permitAll()//所有请求都不需要验证
                .and()
                .logout()
                .permitAll()//定义logout不需要验证
                .and()
                .formLogin();


        // 关闭csrf防护
        http.csrf().disable();

        //解决跨域问题
        http.headers().frameOptions().disable();
    }

    /**
     * 用于实现单点登出功能
     */
    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> listener = new ServletListenerRegistrationBean<>();
        listener.setEnabled(casEnabled);
        listener.setListener(new SingleSignOutHttpSessionListener());
        listener.setOrder(1);
        return listener;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean logOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        LogoutFilter logoutFilter = new LogoutFilter(autoconfig.getCasServerUrlPrefix() + "/logout?service=" + autoconfig.getServerName(), new SecurityContextLogoutHandler());
        filterRegistration.setFilter(logoutFilter);
        filterRegistration.setEnabled(casEnabled);
        if (autoconfig.getSignOutFilters().size() > 0) {
            filterRegistration.setUrlPatterns(autoconfig.getSignOutFilters());
        } else {
            filterRegistration.addUrlPatterns("/logout");
        }
        filterRegistration.addInitParameter("casServerUrlPrefix", autoconfig.getCasServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", autoconfig.getServerName());
        filterRegistration.setOrder(2);
        return filterRegistration;
    }

    /**
     * 该过滤器用于实现单点登出功能，单点退出配置，一定要放在其他filter之前
     */
    @Bean
    public FilterRegistrationBean singleSignOutFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new SingleSignOutFilter());
        filterRegistration.setEnabled(casEnabled);
        if (autoconfig.getSignOutFilters().size() > 0) {
            filterRegistration.setUrlPatterns(autoconfig.getSignOutFilters());
        } else {
            filterRegistration.addUrlPatterns("/*");
        }
        filterRegistration.addInitParameter("casServerUrlPrefix", autoconfig.getCasServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", autoconfig.getServerName());
        filterRegistration.setOrder(3);
        return filterRegistration;
    }

    /**
     * 该过滤器负责用户的认证工作
     */
    @Bean
    public FilterRegistrationBean authenticationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AuthenticationFilter());
        filterRegistration.setEnabled(casEnabled);
        if (autoconfig.getAuthFilters().size() > 0) {
            filterRegistration.setUrlPatterns(autoconfig.getAuthFilters());
        } else {
            filterRegistration.addUrlPatterns("/*");
        }
        //casServerLoginUrl:cas服务的登陆url
        filterRegistration.addInitParameter("casServerLoginUrl", autoconfig.getCasServerLoginUrl());
        //本项目登录ip+port
        filterRegistration.addInitParameter("serverName", autoconfig.getServerName());
        filterRegistration.addInitParameter("useSession", autoconfig.isUseSession() ? "true" : "false");
        filterRegistration.addInitParameter("redirectAfterValidation", autoconfig.isRedirectAfterValidation() ? "true" : "false");
        filterRegistration.setOrder(4);
        return filterRegistration;
    }

    /**
     * 该过滤器负责对Ticket的校验工作
     */
    @Bean
    public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        Cas20ProxyReceivingTicketValidationFilter cas20ProxyReceivingTicketValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();
        cas20ProxyReceivingTicketValidationFilter.setServerName(autoconfig.getServerName());
        filterRegistration.setFilter(cas20ProxyReceivingTicketValidationFilter);
        filterRegistration.setEnabled(casEnabled);
        if (autoconfig.getValidateFilters().size() > 0) {
            filterRegistration.setUrlPatterns(autoconfig.getValidateFilters());
        } else {
            filterRegistration.addUrlPatterns("/*");
        }
        filterRegistration.addInitParameter("casServerUrlPrefix", autoconfig.getCasServerUrlPrefix());
        filterRegistration.addInitParameter("serverName", autoconfig.getServerName());
        filterRegistration.setOrder(5);
        System.out.println("5555555");
        return filterRegistration;
    }


    /**
     * 该过滤器对HttpServletRequest请求包装， 可通过HttpServletRequest的getRemoteUser()方法获得登录用户的登录名
     */
    @Bean
    public FilterRegistrationBean httpServletRequestWrapperFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new HttpServletRequestWrapperFilter());
        filterRegistration.setEnabled(true);
        if (autoconfig.getRequestWrapperFilters().size() > 0) {
            filterRegistration.setUrlPatterns(autoconfig.getRequestWrapperFilters());
        } else {
            filterRegistration.addUrlPatterns("/*");
        }
        filterRegistration.setOrder(6);
        return filterRegistration;
    }

    /**
     * 该过滤器使得可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
     * 比如AssertionHolder.getAssertion().getPrincipal().getName()。
     * 这个类把Assertion信息放在ThreadLocal变量中，这样应用程序不在web层也能够获取到当前登录信息
     */
    @Bean
    public FilterRegistrationBean assertionThreadLocalFilter() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new AssertionThreadLocalFilter());
        filterRegistration.setEnabled(true);
        if (autoconfig.getAssertionFilters().size() > 0) {
            filterRegistration.setUrlPatterns(autoconfig.getAssertionFilters());
        } else {
            filterRegistration.addUrlPatterns("/*");
        }
        filterRegistration.setOrder(7);
        return filterRegistration;
    }


}