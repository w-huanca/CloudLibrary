package com.itheima.controller;

import com.itheima.domain.Book;
import com.itheima.domain.Record;
import com.itheima.domain.User;
import com.itheima.entity.PageResult;
import com.itheima.entity.Result;
import com.itheima.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-08 10:55
 */
@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    // 根据上架时间查询最新上架的图书
    @RequestMapping("/selectNewBooks")
    public ModelAndView selectNewBooks(){
        int pageNum = 1;
        int pageSize = 10;
        PageResult pageResult = bookService.selectNewBooks(pageNum, pageSize);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("pageResult",pageResult);
        modelAndView.setViewName("books_new");
        return modelAndView;
    }

    // 点击编辑或删除查一次，弹窗
    @RequestMapping(value = "/findById")
    @ResponseBody
    public Result<Book> findBookById(String id) {
        try {
            Book book = bookService.findById(id);
            if (book != null) {
                return new Result<Book>(true, "查询图书成功!", book);
            }
            return new Result<>(false, "查询图书失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result<>(false, "查询图书失败!");

        }
    }
    //借阅图书
    @ResponseBody
    @RequestMapping(value = "/borrowBook")
    public Result borrowBook(Book book,HttpServletRequest request,HttpSession session) {
        // 获取当前登录的用户姓名
        User user = (User) session.getAttribute("USER_SESSION");
        Record record = new Record();

        String returnTime = request.getParameter("returnTime");
        try {
            // 根据图书的id和用户进行图书借阅
            Integer integer = bookService.borrowBook(book,user,returnTime,record);
            if (integer > 0) {
                return new Result(true, "借阅图书成功!");
            }
            return new Result(false, "借阅图书失败!请联系管理员查看库存量");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "借阅图书失败!");
        }
    }
    //更新查一遍 搜索查一遍
    @RequestMapping("/search")
    public ModelAndView search(Book book, Integer pageNum, Integer pageSize, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if(pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize=10;
        //调用service
        PageResult pageResult = bookService.search(book, pageNum, pageSize);
        //页面
        modelAndView.setViewName("books");
        //数据
        //搜索框信息回显
        modelAndView.addObject("search",book);
        //分页数据信息
        modelAndView.addObject("pageResult",pageResult);
        // 当前页码数
        modelAndView.addObject("pageNum",pageNum);
        // 分页请求再次请求的地址
        modelAndView.addObject("gourl",request.getRequestURL());
        return modelAndView;
    }

    //新增图书 页面表单提交的图书信息、将新增的结果和向页面传递信息封装到Result对象中返回
    @ResponseBody
    @RequestMapping("/addBook")
    public Result addBook(Book book){
        try {
            Integer num = bookService.addBook(book);
            if (num != 1){
                return new Result(false,"新增图书失败!");
            }
            return new Result(true,"新增图书成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"新增图书失败!");
        }
    }

    @ResponseBody
    @RequestMapping("/editBook")
    public Result editBook(Book book){
        try {
            Integer i = bookService.editBook(book);
            if (i != 1) return new Result(false,"编辑失败!");
            return new Result(true,"编辑成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"编辑失败!");
        }
    }

    @ResponseBody
    @RequestMapping ("/deleteBook")
    public Result deleteBook(String id){
        try {
            Integer count = bookService.deleteBookId(id);
            if (count != 1) return new Result(false,"删除失败");
            return new Result(true,"删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }
    // 查询当前借阅
    @RequestMapping("/searchBorrowed")
    public ModelAndView searchBorrowed(Record record, Integer pageNum, Integer pageSize, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        if(pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize=10;
        User user = (User) request.getSession().getAttribute("USER_SESSION");
        //调用service
        PageResult pageResult = bookService.searchBorrow(record,user,pageNum, pageSize);
        //页面
        modelAndView.setViewName("book_borrowed");
        //数据
        //搜索框信息回显
        modelAndView.addObject("search",record);
        //分页数据信息
        modelAndView.addObject("pageResult",pageResult);
        // 当前页码数
        modelAndView.addObject("pageNum",pageNum);
        // 分页请求再次请求的地址
        modelAndView.addObject("gourl",request.getRequestURL());
        return modelAndView;
    }

    @ResponseBody
    @RequestMapping("/returnBook")
    public Result returnBook(String id,HttpSession session){
        //获取当前登录的用户信息
        User user = (User)session.getAttribute("USER_SESSION");
        try {
            boolean flag = bookService.returnBook(id, user);
            if (!flag){
                return new Result(false,"还书失败！");
            }
            return new Result(true,"还书确认中，请先到行政中心还书！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"还书失败！");
        }
    }

    //确认是否归还
    @ResponseBody
    @RequestMapping("/returnConfirm")
    public Result returnConfirm(String id){
        try {
            Integer count = bookService.returnConfirm(id);
            if(count != 1) return new Result(false,"确认失败！");
            return new Result(true,"确认成功！");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"确认失败！");
        }
    }

    @ResponseBody
    @PostMapping("/test1")
    public Result test1(@RequestBody Book book){
        Integer i = bookService.selectByBook(book);
        if (i > 0){
            return new Result(true,"输入的isbn码已被占用");
        }else {
            return new Result(false,"未被占用");
        }
    }
}
