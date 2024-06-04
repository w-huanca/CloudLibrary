package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.domain.Record;
import com.itheima.vo.BookStats;
import com.itheima.vo.recordStats;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RecordMapper {
    // 新增借阅记录
    Integer addRecord(Record record);


    // 根据借阅人和书名进行
    @Select("      <script>\n" +
            "            select * from record\n" +
            "            <where>\n" +
            "                <if test=\"bookname != null and bookname.trim() !=''\">\n" +
            "                    and record_bookname like concat('%',#{bookname},'%')\n" +
            "                </if>\n" +
            "                <if test=\"borrower != null and borrower.trim() !=''\">\n" +
            "                    and record_borrower like concat('%',#{borrower},'%')\n" +
            "                </if>\n" +
            "            </where>\n" +
            "            ORDER BY record_id DESC" +
            "        </script>")
    @Results(id = "recordMap",value = {
            // id字段默认为false，表示不是主键
            @Result(id = true,column = "record_id",property = "id"),
            @Result(column = "record_bookname",property = "bookname"),
            @Result(column = "record_bookisbn",property = "bookisbn"),
            @Result(column = "record_borrower",property = "borrower"),
            @Result(column = "record_borrowtime",property = "borrowTime"),
            @Result(column = "record_remandtime",property = "remandTime"),
            @Result(id = true,column = "record_status",property = "status")
    })
    Page<Record> searchRecords(Record record);

    // 如果是普通用户，查询当前用户借阅但未归还的图书
    @Select("        <script>\n" +
            "            select * from record\n" +
            "            where record_borrower = #{borrower}\n" +
            "            and record_status in('0','1')\n" +
            "            <if test=\"bookname != null and bookname.trim() !=''\">\n" +
            "                    AND record_bookname like concat('%',#{bookname},'%')\n" +
            "            </if>\n" +
            "            ORDER BY record_id desc\n" +
            "        </script>")
    @ResultMap("recordMap")
    Page<Record> selectMytBorrowed(Record record);

    // 如果是管理员，查询当前用户借阅但未归还的图书和所有待确认的图书
    @Select("<script>" +
            " select * from record\n" +
            "            where record_status in ('0','1')\n" +
            "            <if test=\"bookname != null and bookname.trim() !=''\">\n" +
            "                    AND record_bookname like concat('%',#{bookname},'%')\n" +
            "            </if>\n" +
            "            <if test=\"borrower != null and borrower.trim() !=''\">\n" +
            "                    AND record_borrower like concat('%',#{borrower},'%')\n" +
            "            </if>\n" +
            "            ORDER BY record_id desc\n" +
            "</script>")
    @ResultMap("recordMap")
    Page<Record> selectBorrowed(Record record);

    //根据id查询借阅信息
    @Select("SELECT * FROM record where record_id=#{id}")
    @ResultMap("recordMap")
    Record findById(String id);

    //借阅图书信息修改修改其借阅记录状态和如果超时修改归还时间
    Integer editRecord(Record record);

    @Select("SELECT COUNT(*) FROM record WHERE record_status = '0'")
    int countCurrentBorrowedBooks();

    @Select("SELECT COUNT(*) FROM record")
    int countTotalBorrowedBooks();

    @Select("SELECT DATE_FORMAT(record_borrowtime, '%d') AS day, COUNT(*) AS count " +
            "FROM record WHERE DATE_FORMAT(record_borrowtime, '%Y-%m') = #{month} " +
            "GROUP BY DATE_FORMAT(record_borrowtime, '%d')")
    List<recordStats> selectDailyBorrowStats(String month);

    @Select("SELECT record_bookname, COUNT(*) AS count " +
            "FROM record " +
            "GROUP BY record_bookname " +
            "ORDER BY count DESC LIMIT 10")
    @Results(id = "recordStat",value ={
            @Result(column = "record_bookname",property = "bookname")
    })
    List<BookStats> selectMostPopularBooks();

    @Select("SELECT * FROM record ORDER BY record_borrowtime DESC LIMIT 10")
    @ResultMap("recordMap")
    List<Record> selectLatestActivities();
}
