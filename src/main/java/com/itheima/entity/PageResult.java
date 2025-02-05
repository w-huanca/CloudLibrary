package com.itheima.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: lenovo
 * @time: 2024-03-08 10:22
 */
public class PageResult implements Serializable {
    private long total; //总数
    private List rows; //返回的数据集合

    public PageResult(long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
