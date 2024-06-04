package com.itheima.vo;

/**
 * @Author: Insight
 * @Description: TODO
 * @Date: 2024-05-21 11:01
 * @Version: 1.0
 */
public class recordStats {
    private String day;
    private int count;

    @Override
    public String toString() {
        return "recordStats{" +
                "day='" + day + '\'' +
                ", count=" + count +
                '}';
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
