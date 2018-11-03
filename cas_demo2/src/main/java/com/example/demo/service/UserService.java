package com.example.demo.service;

import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface UserService extends IService<User> {

    /**
     * 根据用户名查找用户
     */
    public User getUserInfo(String username);

}
