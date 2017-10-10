package com.openxu.chart.view.bean;


import android.graphics.Bitmap;

/**
 * autour : openXu
 * date : 2017/10/10 10:24
 * className : Bean
 * version : 1.0
 * description : 请添加类说明
 */
public class Bean {

    public Bean() {
    }

    private String count;
    private String name;
    private int color;
    private int bitmapID;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bean(String count, String name, int color, int bitmapID) {
        this.count = count;
        this.name = name;
        this.color = color;
        this.bitmapID = bitmapID;
    }

    public int getBitmapID() {
        return bitmapID;
    }

    public void setBitmapID(int bitmapID) {
        this.bitmapID = bitmapID;
    }
}
