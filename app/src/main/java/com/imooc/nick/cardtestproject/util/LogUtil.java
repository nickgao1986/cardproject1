package com.imooc.nick.cardtestproject.util;

import android.util.Log;

/**
 * Created by gyj on 2018/4/19.
 */

public class LogUtil {

    public static void d(String tag, String content) {
        Log.d(tag,content);
    }

    public static void i(String tag, String content) {
        Log.d(tag,content);
    }

    public static void v(String tag, String content) {
        Log.d(tag,content);
    }


    public static void w(String tag, String content) {
        Log.d(tag,content);
    }

    public static void w(String content) {
        Log.d("TAG",content);
    }
    public static void d(String content) {
        Log.d("TAG",content);
    }

    public static void i(String content) {
        Log.d("TAG",content);
    }

    public static void e(String tag, String content) {
        Log.e(tag,content);
    }
    public static void e(String tag, String content, Exception e) {
        Log.e(tag,content+e);
    }
}
