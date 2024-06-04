package com.itheima.vo;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024-05-21 13:06
 * @Version: 1.0
 */
public class BookStats {
    private String bookname;
    private int count;

    @Override
    public String toString() {
        return "BookStats{" +
                "bookname='" + bookname + '\'' +
                ", count=" + count +
                '}';
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
