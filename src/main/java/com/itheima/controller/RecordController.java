package com.itheima.controller;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-10 17:55
 */
@Controller
@RequestMapping("/record")
public class RecordController {

    @Autowired private RecordService recordService;

    @RequestMapping("/searchRecords")
    private ModelAndView searchRecords(Record record, HttpServletRequest request, Integer pageNum, Integer pageSize){
//        System.out.println(record);
        if (pageNum == null) pageNum = 1;
        if(pageSize == null) pageSize = 10;
        User user = (User)request.getSession().getAttribute("USER_SESSION");
        PageResult pageResult = recordService.searchRecords(record, user, pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("record");
        modelAndView.addObject("pageResult",pageResult);
        modelAndView.addObject("search",record);
        modelAndView.addObject("pageNum",pageNum);
        modelAndView.addObject("gourl",request.getRequestURI());
        return modelAndView;
    }
}
