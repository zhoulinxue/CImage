package org.zhx.common.image.utils;

import java.util.Date;

import org.zhx.common.image.CImage;

public class CLog {
    private final static String TAG = "CImage";

    public static void error(String error) {
        System.out.println(new Date().toString() + "|" + error);
    }


    public static void e(String msg) {
        if (CImage.isDebug)
            android.util.Log.e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (CImage.isDebug)
            android.util.Log.e(TAG + "_" + tag, msg);
    }

    public static void d(String tag, String msg) {
        if (CImage.isDebug)
            android.util.Log.d(TAG + "_" + tag, msg);
    }


}
