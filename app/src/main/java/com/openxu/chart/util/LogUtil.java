package com.openxu.chart.util;

import android.util.Log;

/**
 * 通用的log工具，为以后拓展日志的功能预留
 */
public class LogUtil {

    /**
     * 全局的静态变量，用来判断是不是测试版本
     */
    public static final boolean IS_RELEASE = false;

    public static void d(String tag, CharSequence msg) {
        if(!IS_RELEASE)
            Log.d(tag, msg.toString());
    }
    public static void v(String tag, Object msg) {
        if(!IS_RELEASE)
            Log.v(tag, msg.toString());
    }
    public static void i(String tag, Object msg) {
        if(!IS_RELEASE)
            Log.i(tag, msg.toString());
    }
    public static void w(String tag, Object msg) {
        if(!IS_RELEASE)
            Log.w(tag, msg.toString());
    }
    public static void e(String tag, Object msg) {
        if(!IS_RELEASE&&msg!=null)
            Log.e(tag, msg.toString());
    }
    public static void d(Object tag, CharSequence msg) {
        if(!IS_RELEASE)
            Log.d(tag.getClass().getSimpleName(), msg.toString());
    }
    public static void v(Object tag, Object msg) {
        if(!IS_RELEASE)
            Log.v(tag.getClass().getSimpleName(), msg.toString());
    }
    public static void i(Object tag, Object msg) {
        if(!IS_RELEASE)
            Log.i(tag.getClass().getSimpleName(), msg.toString());
    }
    public static void w(Object tag, Object msg) {
        if(!IS_RELEASE)
            Log.w(tag.getClass().getSimpleName(), msg.toString());
    }
    public static void e(Object tag, Object msg) {
        if(!IS_RELEASE)
            Log.e(tag.getClass().getSimpleName(), msg.toString());
    }
    public static void logE(String tag, String content) {
        int p = 2000;
        long length = content.length();
        if (length < p || length == p)
            e(tag, content);
        else {
            while (content.length() > p) {
                String logContent = content.substring(0, p);
                content = content.replace(logContent, "");
                e(tag, logContent);
            }
            e(tag, content);
        }
    }

}
