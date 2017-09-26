package com.lizi.fast_fragment.utils;

import android.util.Log;

import com.lizi.fast_fragment.BuildConfig;

/**
 * Created by lizi on 2016/11/10.
 */

public class L {

    private static final String TAG = "TangYuan";
    public static boolean DEBUG = BuildConfig.DEBUG;

    public static void d(String msg) {
        if (DEBUG){
            Log.d(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (DEBUG){
            Log.e(TAG, msg);
        }
    }
}
