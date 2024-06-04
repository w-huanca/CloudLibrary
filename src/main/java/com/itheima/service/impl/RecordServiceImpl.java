package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RecordMapper;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.RecordService;
import com.itheima.vo.BookStats;
import com.itheima.vo.recordStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-10 16:38
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired RecordMapper recordMapper;
    @Override
    public Integer addRecord(Record record) {
        return recordMapper.addRecord(record);
    }

    @Override
    public PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize) {
        //设置分页查询的参数，开始分页
        PageHelper.startPage(pageNum,pageSize);
        //如果不是管理员，则查询条件中的借阅人设置为当前登录用户
        if(!"ADMIN".equals(user.getRole())){
            record.setBorrower(user.getName());
        }
        Page<Record> page = recordMapper.searchRecords(record);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public int countCurrentBorrowedBooks() {
        return recordMapper.countCurrentBorrowedBooks();
    }

    @Override
    public int countTotalBorrowedBooks() {
        return recordMapper.countTotalBorrowedBooks();
    }

    @Override
    public List<recordStats> getDailyBorrowStats(String month) {
        return recordMapper.selectDailyBorrowStats(month);
    }

    public List<BookStats> getMostPopularBooks() {
        return recordMapper.selectMostPopularBooks();
    }

    @Override
    public List<Record> getLatestActivities() {
        return recordMapper.selectLatestActivities();
    }
}
