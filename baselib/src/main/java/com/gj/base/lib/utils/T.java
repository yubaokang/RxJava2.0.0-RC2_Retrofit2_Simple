package com.gj.base.lib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 打印工具类
 *
 * @author wanglei
 */
public class T {

    /**
     * ToastMake 显示message
     *
     * @param activity
     * @param message
     */
    public static void show(Context activity, CharSequence message) {
        if (activity != null) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(Context activity, int message) {
        if (activity != null) {
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
        }
    }

}
