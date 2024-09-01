package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.service.UserService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-06 13:45
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    //前往主页
    @RequestMapping("/toMainPage")
    public String toMainPage() {
        return "main";
    }
    //前往注册页
    @RequestMapping("/toLoginPage")
    public String toLoginPage() {
        return "login";
    }
    //通过id找到用户
    @ResponseBody
    @RequestMapping("/findByUserId")
    public Result<User> findByUserId(String id) {
        try {
            User user = userService.findByUserId(id);
            if (user != null) {
                return new Result<User>(true, "查询用户成功!", user);
            }
            return new Result<>(false, "查询用户失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, "查询用户失败!");

        }
    }
    //读者管理
    @RequestMapping("/search")
    public ModelAndView search(User user, Integer pageNum, Integer pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;
        //调用service
        User user1 = (User) request.getSession().getAttribute("USER_SESSION");
        user.setId(user1.getId());
        PageResult pageResult = userService.search(user, pageNum, pageSize);
        //页面
        modelAndView.setViewName("userList");
        //数据
        //搜索框信息回显
        modelAndView.addObject("search", user);
        //分页数据信息
        modelAndView.addObject("pageResult", pageResult);
        // 当前页码数
        modelAndView.addObject("pageNum", pageNum);
        // 分页请求再次请求的地址
        modelAndView.addObject("gourl", request.getRequestURL());
        return modelAndView;
    }
    //编辑用户
    @ResponseBody
    @RequestMapping("/editUser")
    public Result editUser(User user) {
        try {
            Integer i = userService.editUser(user);
            if (i != 1) return new Result(false, "编辑失败!");
            return new Result(true, "编辑成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "编辑失败!");
        }
    }
    //管理员读者管理的删除用户功能
    @ResponseBody
    @RequestMapping("/deleteUserById")
    public Result deleteUserById(String id) {
        try {
            Integer flag = userService.deleteUserById(id);
            if (flag > 0) {
                return new Result(true, "删除用户成功！");
            }
            return new Result(false, "删除用户失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除用户失败！");
        }
    }

    //新增用户 页面表单提交的用户信息、将新增的结果和向页面传递信息封装到Result对象中返回
    @ResponseBody
    @RequestMapping("/addUser")
    public Result addUser(User user) {
        try {
            Integer num = userService.addUser(user);
            if (num != 1) {
                return new Result(false, "新增用户失败!");
            }
            return new Result(true, "新增用户成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增用户失败!");
        }
    }

    //登录功能
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request) {
        User dbUser = userService.login(user);
        try {
            if (dbUser != null) {
                request.getSession().setAttribute("USER_SESSION", dbUser);
                return "redirect:/user/toMainPage";
            } else {
                //登录失败
                request.setAttribute("msg", "用户名或者密码错误");
                return "forward:/user/toLoginPage";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/user/toLoginPage";
        }
    }
    // 用户注册模块
    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }
    @PostMapping("/register")
    public String register(User user, HttpServletRequest request) {
        if (StringUtils.hasLength(user.getName()) || StringUtils.hasLength(user.getPassword()) || StringUtils.hasLength(user.getEmail())) {
            Integer i = userService.selectByRegister(user);
            if (i == 0) {
                boolean flag = userService.register(user);
                if (flag) {
                    request.setAttribute("msg", "注册成功，请登录！");
                    System.out.println("注册成功！");
//                    request.getSession().invalidate();    //销毁session
                    return "forward:/";
                } else {
                    //注册失败
                    request.setAttribute("msg", "注册失败，请按提示信息完成注册");
                    return "register";
                }
            } else {
                request.setAttribute("msg", "用户名或邮箱已存在！");
                System.out.println("用户名已存在！");
                return "register";
            }
        } else {
            request.setAttribute("msg", "请完善信息！");
            System.out.println("请完善信息！");
            return "register";
        }
    }
    //读者管理模块
    @RequestMapping("/userList")
    public ModelAndView userList(Integer pageNum, Integer pageSize, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        //调用service
        PageResult pageResult = userService.userLists(user, pageNum, pageSize);
        //页面
        modelAndView.setViewName("userList");
        //数据
        //分页数据信息
        modelAndView.addObject("pageResult", pageResult);
        // 当前页码数
        modelAndView.addObject("pageNum", pageNum);
        // 分页请求再次请求的地址
        modelAndView.addObject("gourl", request.getRequestURL());
        return modelAndView;
    }
    @ResponseBody
    @PostMapping("/test1")
    public Result test1(@RequestBody User user){
        Integer i = userService.selectByEditProfile(user);
        System.out.println(user);
        if (i > 0){
            return new Result(true,"用户名或邮箱已被占用");
        }else {
            return new Result(false,"未被占用");
        }
    }
    @RequestMapping("/userInfo")
    public String userInfo(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        request.setAttribute("UserInformation", user);
        return "profile";
    }

    @PostMapping("/editProfile")
    public String editProfile(User user, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (!StringUtils.hasLength(user.getName()) || !StringUtils.hasLength(user.getEmail())) {
            request.setAttribute("result", new Result(false, "请完善信息！"));
            return "profile";
        }
        User user1 = (User) request.getSession().getAttribute("USER_SESSION");
        user.setId(user1.getId());
        user.setPassword(user1.getPassword());
        Integer i = userService.selectByEditProfile(user);
        try {
            if(i == 0){
                boolean flag = userService.editProfile(user);
                if (flag) {
                    request.setAttribute("UserInformation", user);
                    request.getSession().setAttribute("USER_SESSION", user);
                    //修改密码成功后注销登录
                    PrintWriter writer = response.getWriter();
                    writer.print("<script>window.parent.location.href='/cloudLibrary/admin/main.jsp';</script>");
                    writer.flush();
                    writer.close();
                    return null;
                } else {
                    request.setAttribute("result", new Result(false, "修改个人信息失败！"));
                    return "profile";
                }
            }else {
                request.setAttribute("result", new Result(false, "用户名或邮箱已存在！"));
                System.out.println("用户或邮箱存在！");
                return "forward:/user/userInfo";
            }
        } catch (IOException e) {
            request.setAttribute("result", new Result(false, "修改个人信息失败！"));
            return "profile";
        }
    }
    //修改密码模块
    @GetMapping("/tochangePassword")
    public String tochangePassword() {
        return "cPassword";
    }
    @PostMapping("/cpwd")
    public String changePassword(@RequestParam("old_pwd") String oldPassword,
                                    @RequestParam("new_pwd") String newPassword,
                                    @RequestParam("re_pwd") String rePassword,
                                    HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        if (!StringUtils.hasLength(oldPassword) || !StringUtils.hasLength(newPassword) || !StringUtils.hasLength(rePassword)) {
            request.setAttribute("result", new Result(false, "缺失必要参数"));
            return "cPassword";
        }
        if (!user.getPassword().equals(oldPassword)) {
            request.setAttribute("result", new Result(false, "原密码不正确"));
            return "cPassword";
        }
        if (!newPassword.equals(rePassword)) {
            request.setAttribute("result", new Result(false, "新密码不正确"));
            return "cPassword";
        }
        try {
            Integer i = userService.changePassword(user.getId(), newPassword);
            if (i > 0) {
                // 修改密码成功后注销登录并重定向到登录页面，同时传递消息
                PrintWriter writer = response.getWriter();
                writer.print("<script>window.parent.location.href='/cloudLibrary/admin/login.jsp?msg=" + URLEncoder.encode("修改密码成功！,自行注销切换账户", "UTF-8") + "';</script>");
                writer.flush();
                writer.close();
                return null;
            } else {
                request.setAttribute("result", new Result(false, "修改密码失败,密码长度要为8位,至少包含大小写字母数字的组合！"));
                return "cPassword";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("result", new Result(false, "修改密码失败,请联系管理员"));
            return "cPassword";
        }
    }

    @RequestMapping("/logOut")
    public String logOut(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            //销毁session
            session.invalidate();
            return "forward:/user/toLoginPage";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("msg", "系统错误");
            return "forward:/user/toLoginPage";
        }
    }
}
