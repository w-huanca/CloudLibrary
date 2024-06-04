package com.itheima.service;

import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.vo.BookStats;
import com.itheima.vo.recordStats;

import java.util.List;

public interface RecordService {
    // 新增借阅记录
    Integer addRecord(Record record);

    // 查询借阅记录
    PageResult searchRecords(Record record, User user, Integer pageNum, Integer pageSize);

    int countCurrentBorrowedBooks();

    int countTotalBorrowedBooks();

    List<recordStats> getDailyBorrowStats(String month);

    List<BookStats> getMostPopularBooks();

    List<Record> getLatestActivities();
}
