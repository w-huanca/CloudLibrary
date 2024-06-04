package com.itheima.interceptor;

import com.itheima.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-07 10:07
 */
public class ResourceInterceptor implements HandlerInterceptor {
    private List<String> ignoreUrl;

    public ResourceInterceptor(List<String> ignoreUrl) {
        this.ignoreUrl = ignoreUrl;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果用户已经登录直接放行
        String requestURI = request.getRequestURI();
        //如果没有登录，我们判断是否访问的是登录相关功能，如果是，放行。 返回的是login在路径中的位置
        if (requestURI.indexOf("login") >= 0 || requestURI.indexOf("index") >= 0){
            return true;
        }
        User user = (User)request.getSession().getAttribute("USER_SESSION");
        if (user != null){
            //判断用户如果是管理员，才放行
            if ("ADMIN".equals(user.getRole()))
                return true;
            //如果不是管理员，普通用户需要判断请求是否能访问
            else{
                for(String url:ignoreUrl){
                    if (requestURI.indexOf(url) >= 0) return  true;
                }
                //如果不是登录相关，需要跳转到登录页面,请求还没完成
                request.setAttribute("msg","没有权限，请换到管理员账号");
                request.getRequestDispatcher("/WEB-INF/pages/admin/login.jsp").forward(request,response);
                return false;
            }
        }


        //如果不是登录相关，需要跳转到登录页面,请求还没完成
        request.setAttribute("msg","您还没有登录，请先登录");
        request.getRequestDispatcher("/WEB-INF/pages/admin/login.jsp").forward(request,response);
        return false;
    }
}
