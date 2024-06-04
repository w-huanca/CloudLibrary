package com.itheima.domain;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-10 15:53
 */
public class Record {
    private Integer id;
    private String bookname;
    private String bookisbn;
    private String borrower;
    private String borrowTime;
    private String remandTime;

    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBookisbn() {
        return bookisbn;
    }

    public void setBookisbn(String bookisbn) {
        this.bookisbn = bookisbn;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getRemandTime() {
        return remandTime;
    }

    public void setRemandTime(String remandTime) {
        this.remandTime = remandTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", bookname='" + bookname + '\'' +
                ", bookisbn='" + bookisbn + '\'' +
                ", borrower='" + borrower + '\'' +
                ", borrowTime='" + borrowTime + '\'' +
                ", remandTime='" + remandTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
