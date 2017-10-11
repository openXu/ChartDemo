package com.openxu.chart;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.openxu.chart.view.bean.Bean;
import com.openxu.chart.view.chart.PerChart;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PerChart perChart;
    private List<Object> countList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        perChart = (PerChart) findViewById(R.id.perChart);

        countList = new ArrayList<>();
        countList.add(new Bean("50", "消费购物",Color.parseColor("#78a543"), R.mipmap.value_shop_select));
        countList.add(new Bean("50", "消费购物",Color.parseColor("#7a1543"), R.mipmap.value_sign_normal));
        countList.add(new Bean("50", "消费购物",Color.parseColor("#78aaa3"), R.mipmap.value_share_select));
        countList.add(new Bean("50", "消费购物",Color.parseColor("#1f3543"), R.mipmap.value_shop_select));
        countList.add(new Bean("50", "消费购物",Color.parseColor("#123123"), R.mipmap.value_sign_normal));
        countList.add(new Bean("50", "消费购物",Color.parseColor("#7abc33"), R.mipmap.value_share_select));
        countList.add(new Bean("50", "消费购物",Color.parseColor("#1b4593"), R.mipmap.value_sign_normal));
        /*设置控件*/
        perChart.setDebug(true);
        /*请求到数据后，需要设置数据，并设置loading为false*/
        perChart.setLoading(false);
        perChart.setData(Bean.class, "count", "name", "color", "bitmapID", countList);
    }
}
