package com.openxu.chart.view.bean;

import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;

import java.util.List;

/**
 * autour : openXu
 * date : 2017/10/10 10:17
 * className : ChartBean
 * version : 1.0
 * description : 图表数据
 */
public class ChartDataBean {

    private int per;
    private String perStr;
    private String name;
    private int color;
    private int bitmapID;


    //触摸相关
    private Region region;     //扇形区域--用于判断手指触摸点是否在此范围
    //扇形相关
    private RectF arcRect;
    private float startAngle;
    private float sweepAngle;
    //tag线段
    private List<PointF> tagLinePoints;
    /*图片相关*/
    private PointF picCenterPoint;
    private PointF picPoint;
    /*文字相关*/
    private PointF prePoint;
    private PointF namePoint;

    private PointF tagTextPoint;

    @Override
    public String toString() {
        return "RoseChartBean{" +
                "per=" + per +
                ", name='" + name + '\'' +
                '}';
    }

    public ChartDataBean(int per, String name, int color, int bitmapID) {
        this.color = color;
        this.bitmapID = bitmapID;
        this.per = per;
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getBitmapID() {
        return bitmapID;
    }

    public void setBitmapID(int bitmapID) {
        this.bitmapID = bitmapID;
    }

    public PointF getPicPoint() {
        return picPoint;
    }

    public PointF getPrePoint() {
        return prePoint;
    }

    public String getPerStr() {
        return perStr;
    }

    public void setPerStr(String perStr) {
        this.perStr = perStr;
    }

    public void setPrePoint(PointF prePoint) {
        this.prePoint = prePoint;
    }

    public PointF getNamePoint() {
        return namePoint;
    }

    public void setNamePoint(PointF namePoint) {
        this.namePoint = namePoint;
    }

    public void setPicPoint(PointF picPoint) {
        this.picPoint = picPoint;
    }

    public PointF getTagTextPoint() {
        return tagTextPoint;
    }
    public void setTagTextPoint(PointF tagTextPoint) {
        this.tagTextPoint = tagTextPoint;
    }

    public List<PointF> getTagLinePoints() {
        return tagLinePoints;
    }

    public void setTagLinePoints(List<PointF> tagLinePoints) {
        this.tagLinePoints = tagLinePoints;
    }

    public PointF getPicCenterPoint() {
        return picCenterPoint;
    }

    public void setPicCenterPoint(PointF picCenterPoint) {
        this.picCenterPoint = picCenterPoint;
    }

    public RectF getArcRect() {
        return arcRect;
    }

    public void setArcRect(RectF arcRect) {
        this.arcRect = arcRect;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(float startAngle) {
        this.startAngle = startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public void setSweepAngle(float sweepAngle) {
        this.sweepAngle = sweepAngle;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public int getPer() {
        return per;
    }

    public void setPer(int per) {
        this.per = per;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
