package com.itheima.service;

import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;

public interface BookService {
    //查询最新上架的图书 分别查询页数，和页数有多少条数据
    PageResult selectNewBooks(Integer pageNum,Integer pageSize);

    //根据id查询图书信息
    Book findById(String id);
    //借阅图书
    Integer borrowBook(Book book, User user, String returnTime, Record record);

    // 分页查询图书
    PageResult search(Book book,Integer pageNum,Integer pageSize);

    Integer addBook(Book book);

    Integer editBook(Book book);

    // 分页条件查询当前借阅信息
    PageResult searchBorrow(Record record, User user, Integer pageNum, Integer pageSize);

    // 归还图书
    boolean returnBook(String id,User user);

    //确认归还图书
    Integer returnConfirm(String id);

    Integer deleteBookId(String id);

    Integer selectByBook(Book book);

    int  countTotalBooks();
}
