package com.example.demo.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查找用户
     */
    @Override
    public User getUserInfo(String username){
        User user=userMapper.findUserInfo(username);
        return user;
    }

}
