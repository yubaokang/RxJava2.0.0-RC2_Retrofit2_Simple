package com.gj.base.lib.utils;

/**
 * Created by erhu on 2016/1/19.
 */
public class StringUtils {
    public static String getLoation(String msg) {
        if (StringUtils.isNotBlank(msg)) {
            int conut = msg.indexOf('.');
            return msg.substring(conut + 1, msg.length());
        } else {
            return "";
        }
    }

    /**
     * 字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }

    /**
     * 字符串是否为非空
     *
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return (str != null && str.length() != 0);
    }

    /**
     * 字符串是否为空格串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * 字符串是否非空格串
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return (str != null && str.trim().length() != 0);
    }

    /**
     * 将null转换为空串,如果参数为非null，则直接返回
     *
     * @param str
     * @return
     */
    public static String nullToEmpty(String str) {
        return (str == null ? "" : str);
    }

    /**
     * 将null转换为字符串NULL,如果参数为非null，则直接返回
     *
     * @param str
     * @return
     */
    public static String nullToString(String str) {
        return (str == null ? "NULL" : str);
    }

    public static String getIndexString(String str) {
        if (str.length() <= 5) {
            return str;
        }
        return str.substring(5);
    }
}
