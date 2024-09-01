package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.domain.Book;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import org.apache.ibatis.annotations.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-07 16:38
 */

public interface BookMapper{
    //首页新书推荐功能
    @Select("SELECT * from book order by book_uploadtime DESC")
    @Results(id = "BookMap",value = {
            //id字段为false，表示不是主键
            @Result(id = true,column = "book_id",property = "id"),
            @Result(column = "book_name",property = "name"),
            @Result(column = "book_isbn",property = "isbn"),
            @Result(column = "book_press",property = "press"),
            @Result(column = "book_author",property = "author"),
            @Result(column = "book_pagination",property = "pagination"),
            @Result(column = "book_price",property = "price"),
            @Result(column = "book_uploadTime",property = "uploadTime"),
            @Result(column = "book_stockpile",property = "stockpile"),
            @Result(column = "book_status",property = "status"),
    })
    Page<Book> selectNewBooks();

    //增删的模态框根据id查询
    @Select("SELECT * FROM book where book_id=#{id}")
    @ResultMap("BookMap")
    //根据id查询图书信息
    Book findById(String id);

    // 根据name查询图书信息
    @Select("SELECT * FROM book where book_name=#{name}")
    @ResultMap("BookMap")
    Book findByName(String name);

    //修改借阅图书信息
    Integer editBook(Book book);

    //增删改查完都要查询图书
    @Select("        <script>\n" +
            "            select * from book\n"+
            "            where book_status != 3 "+
            "                <if test=\"name != null and name.trim() != ''\">\n" +
            "                    AND book_name like concat('%',#{name},'%')\n" +
            "                </if>\n" +
            "                <if test=\"author != null and author.trim() != ''\">\n" +
            "                    AND book_author like concat('%',#{author},'%')\n" +
            "                </if>\n" +
            "                <if test=\"press != null and press.trim() != ''\">\n" +
            "                    AND book_press like concat('%',#{press},'%')\n" +
            "                </if>\n" +
            "            order by book_status\n" +
            "        </script>")
    @ResultMap("BookMap")
    Page<Book> searchBooks(Book book);

    //新增图书
    Integer addBook(Book book);

    //归还图书
    Integer returnConfirm(Book book);

    //增改中的验证ISBN唯一功能
    @Select("select count(*) from book where book_isbn = #{isbn} and book_id != #{id}")
    Integer selectByBook(Book book);
    @Delete("delete from book where book_id = #{id}")
    Integer deleteBook(String id);

    @Select("SELECT COUNT(*) FROM book")
    int countTotalBooks();


}
