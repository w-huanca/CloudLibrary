package com.itheima.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.BookMapper;
import com.itheima.dao.RecordMapper;
import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.service.BookService;
import com.itheima.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-08 10:31
 */
@Service
public class BookServiceImpl implements BookService {
    //注入BookMapper对象
    @Autowired
    private BookMapper bookMapper;   //Mybaits的配置文件会自动导入这个包，Spring这个配置文件却找不到这个文件
    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private RecordService recordService;
    /**
     * @description:
     * @param pageNum 当前页码
     * @param pageSize  每页显示数量
     * @return: com.itheima.entity.PageResult
     * @author: lenovo
     * @time: 2024-03-08 10:35
     */
    @Override
    public PageResult selectNewBooks(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        Page<Book> page = bookMapper.selectNewBooks();
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Book findById(String id) {
        return bookMapper.findById(id);
    }

    @Override
    public Integer borrowBook(Book book, User user, String returnTime, Record record) {
        // 根据id查询出需要借阅的完整的图书信息
        Book findbook = bookMapper.findById(book.getId().toString());
        // 设置所借阅的图书库存量-1
        book.setStockpile(findbook.getStockpile()-1);
        // 当库存量为0时图书状态为待归还
        if (book.getStockpile() == 0) {
            book.setStatus("1");
        }
        bookMapper.editBook(book);
        record.setBookname(book.getName());
        record.setBookisbn(book.getIsbn());
        // 设置当天为借阅时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        record.setBorrowTime(dateFormat.format(new Date()));
//        // 设置归还时间
        record.setRemandTime(returnTime.toString());
        // 设置借阅人为当前登录用户
        record.setBorrower(user.getName());
        return recordMapper.addRecord(record);
    }
//    private Record setRecord(Book book){
//        Record record = new Record();
//        record.setBookname(book.getName());
//        record.setBookisbn(book.getIsbn());
////        record.setBorrower(book.getBorrower());
////        record.setBorrowTime(book.getBorrowTime());
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        //设置图书归还确认时间为当天图书归还时间
//        record.setRemandTime(dateFormat.format(new Date()));
//        return record;
//    }

    @Override
    public PageResult search(Book book, Integer pageNum, Integer pageSize) {
        // 开启分页查询
        PageHelper.startPage(pageNum,pageSize);
        // 调用dao层
        Page<Book> page = bookMapper.searchBooks(book);
        return new PageResult(page.getTotal(),page.getResult());
    }
    // 新增图书 book页面提交的新增图书信息
    @Override
    public Integer addBook(Book book) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 设置新增图书的上架时间
        book.setUploadTime(dateFormat.format(new Date()));
        return bookMapper.addBook(book);
    }

    @Override
    public Integer editBook(Book book,Record record) {
        return bookMapper.editBook(book);
    }

    @Override
    public Integer deleteBookId(String id) {
        return bookMapper.deleteBook(id);
    }

    @Override
    public PageResult searchBorrow(Record record, User user, Integer pageNum, Integer pageSize) {
        // 分页查询
        PageHelper.startPage(pageNum,pageSize);
        Page<Record> page;
        //区分用户类型，调用不同的dao
        if("ADMIN".equals(user.getRole())){
            page = recordMapper.selectBorrowed(record);
        }else{
            // 设置借阅人
            record.setBorrower(user.getName());
            page = recordMapper.selectMytBorrowed(record);
        }
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public boolean returnBook(String id, User user) {
        Record recordUser = recordMapper.findById(id);
        boolean isUser = recordUser.getBorrower().equals(user.getName());
        boolean isAdmin = "ADMIN".equals(user.getRole());
        //调用dao，修改图书信息
        if(isUser || isAdmin){
            recordUser.setStatus("1");
            recordMapper.editRecord(recordUser);
        }
        return isUser || isAdmin;
    }

    @Override
    public Integer returnConfirm(String id) {
        // 根据记录id查询借阅的完整信息
        Record record = recordMapper.findById(id);
//        Record record = this.setRecord(book);   // 将book实体同属性数值赋值给record实体
        record.setStatus("2");
        // 设置当天为归还时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        record.setRemandTime(dateFormat.format(new Date()));
        Integer count = recordMapper.editRecord(record);
        // 如果归还确认成功，则修改库存量
        if (count > 0) {
            Book book = bookMapper.findByName(record.getBookname());
            book.setStockpile(book.getStockpile()+1);
            book.setStatus("0");
            return bookMapper.editBook(book);
        } else {
            return 0;
        }
    }

    @Override
    public Integer selectByBook(Book book) {
        return bookMapper.selectByBook(book);
    }

    @Override
    public int countTotalBooks() {
        return bookMapper.countTotalBooks();
    }
}
