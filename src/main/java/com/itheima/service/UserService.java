package com.itheima.service;

import com.itheima.domain.User;
import com.itheima.entity.PageResult;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-06 19:11
 */
public interface UserService {

    PageResult userLists(User user,Integer pageNum, Integer pageSize);

    //登录
    User login(User user);
    //注册
    boolean register(User user);
    Integer selectByRegister(User user);
    //修改用户密码
    Integer changePassword(Integer id, String newPassword);
    //修改个人信息
    boolean editProfile(User user);

    User findByUserId(String id);

    Integer editUser(User user);

    PageResult search(User user, Integer pageNum, Integer pageSize);

    Integer deleteUserById(String id);

    Integer addUser(User user);

    Integer selectByEditProfile(User user);

    int countTotalReaders();
}
