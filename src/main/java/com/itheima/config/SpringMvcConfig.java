package com.itheima.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.List;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-06 13:10
 */
@Configuration
@ComponentScan("com.itheima.controller")
@PropertySource("classpath:ignoreUrl.properties")
@EnableWebMvc
public class SpringMvcConfig implements WebMvcConfigurer {
    @Value("#{'${ignoreUrl}'.split(',')}")
    private List<String> ignoreUrl;

    //开启静态资源的访问
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/admin/",".jsp");
    }

    //    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new ResourceInterceptor(ignoreUrl))
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**","/js/**","/img/**");
//    }
}
