package com.tuyenmonkey.showmetheway.helper;

import android.util.Log;

import com.tuyenmonkey.showmetheway.app.AppConfig;

/**
 * Created by tuyen on 1/2/2016.
 */
public class LogUtils {

    public static void d(String tag, String message) {
        if (AppConfig.IS_DEBUG) {
            Log.d(tag, message);
        }
    }

    public static void e(String tag, String message) {
        if (AppConfig.IS_ERROR) {
            Log.e(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (AppConfig.IS_INFO) {
            Log.i(tag, message);
        }
    }

    public static void v(String tag, String message) {
        if (AppConfig.IS_VERB) {
            Log.v(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (AppConfig.IS_WARN) {
            Log.w(tag, message);
        }
    }
}
