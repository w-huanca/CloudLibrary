package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.dao.UserMapper;
import com.itheima.entity.PageResult;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-06 19:14
 */
/*
    用户接口实现类
 */
@Service
public class UserServiceImpl implements UserService {
    //注入UserMapper对象
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUserId(String id) {
        return userMapper.findByUserId(id);
    }

    @Override
    public boolean editProfile(User user) {
        if (!isValidName(user.getName())) {
            // 密码长度至少为8个字符。
            //至少包含一个小写字母。
            //至少包含一个大写字母。
            //至少包含一个数字。
            return false;
        }
        if (!isValidEmail(user.getEmail())){
            // 处理无效邮箱
            return false;
        }
        return userMapper.editProfile(user);
    }
    @Override
    public Integer selectByRegister(User user) {
        return userMapper.selectByRegister(user);
    }

    @Override
    public Integer editUser(User user) {
        return userMapper.editUser(user);
    }

    @Override
    public PageResult search(User user, Integer pageNum, Integer pageSize) {
        // 开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        // 调用dao层
        Page<User> page = userMapper.searchUser(user);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Integer selectByEditProfile(User user) {
        return userMapper.selectByEditProfile(user);
    }

    @Override
    public Integer deleteUserById(String id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public Integer addUser(User user) {
        return userMapper.addUser1(user);
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
    @Override
    public boolean register(User user) {
        if (!isValidName(user.getName())) {
            // 密码长度至少为8个字符。
            //至少包含一个小写字母。
            //至少包含一个大写字母。
            //至少包含一个数字。
            return false;
        }
        if(!isValidPassword(user.getPassword())){
            // 处理
            return false;
        }
        if (!isValidEmail(user.getEmail())){
            // 处理无效邮箱
            return false;
        }
        userMapper.addUser(user);
        return true;
    }
    private boolean isValidName(String name) {
        // 检查用户名是否符合2-15位的除特殊字符的组合
        String nameRegex = "^[a-zA-Z0-9_\\u4e00-\\u9fa5\\uF900-\\uFA2D]{2,15}$";
        return name.matches(nameRegex);
    }
    private boolean isValidEmail(String email){
        //使用正则表达式来验证邮箱格式
        String emailRegex = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        return email.matches(emailRegex);
    }
    private boolean isValidPassword(String password){
        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        return password.matches(passwordRegex);
    }
    @Override
    public Integer changePassword(Integer id, String newPassword) {
        if(!isValidPassword(newPassword)){
            // 处理
            return -1;
        }
        return userMapper.changePassword(id,newPassword);
    }

    @Override
    public PageResult userLists(User user, Integer pageNum, Integer pageSize) {
        // 分页查询
        PageHelper.startPage(pageNum,pageSize);
        Page<User> page;
        //区分用户类型，调用不同的dao
        page = userMapper.selectUser(user);
        return new PageResult(page.getTotal(),page.getResult());
    }

    public int countTotalReaders() {
        return userMapper.countTotalReaders();
    }
}
