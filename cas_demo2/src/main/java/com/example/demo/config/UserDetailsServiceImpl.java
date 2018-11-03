package com.example.demo.config;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证类
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserService userService;

    @Override

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("当前的用户名是：" + username);
        com.example.demo.entity.User user = userService.getUserInfo(username);

        List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_SELLER"));
        return new User(username, user.getPassword(), grantedAuths);

    }

}
