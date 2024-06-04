package com.itheima.controller;

import com.itheima.entity.Result;
import com.itheima.service.BookService;
import com.itheima.service.RecordService;
import com.itheima.service.ReportService;
import com.itheima.service.UserService;
import com.itheima.vo.recordStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024-05-08 0:07
 * @Version: 1.0
 */
@Controller
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private RecordService recordService;

    @RequestMapping("/toRecordStatistics")
    public String RecordStatistics(){
        return "dashboard";
    }
    @GetMapping("/stats")
    @ResponseBody
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBooks", bookService.countTotalBooks());
        stats.put("totalReaders", userService.countTotalReaders());
        stats.put("currentBorrowedBooks", recordService.countCurrentBorrowedBooks());
        stats.put("totalBorrowedBooks", recordService.countTotalBorrowedBooks());
        stats.put("mostPopularBooks", recordService.getMostPopularBooks());
        stats.put("latestActivities", recordService.getLatestActivities());
        return stats;
    }
    @GetMapping("/dailyStats")
    @ResponseBody
    public List<recordStats> getDailyBorrowStats(@RequestParam("month") String month) {
        return recordService.getDailyBorrowStats(month);
    }
}

