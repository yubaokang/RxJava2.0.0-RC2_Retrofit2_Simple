package com.gj.base.lib.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by hank on 2016/1/13/15:15:11
 * GSON解析工具
 */
public class GsonUtils {

    private static GsonUtils gsonUtils;
    private Gson gson;

    private GsonUtils() {
        gson = new Gson();
    }

    public static GsonUtils getInstance() {
        if (gsonUtils == null) {
            gsonUtils = new GsonUtils();
        }
        return gsonUtils;
    }

    public String toJson(Object object) {
        return gson.toJson(object);
    }

    public <D> D getObject(String json, Class<D> d) {

        return gson.fromJson(json, d);
    }

    public Map<String, String> getMapString(String json) {
        return gson.fromJson(json, new TypeToken<Map<String, String>>() {
        }.getType());
    }

    public Map<String, String> getMapString(Object object) {
        return gson.fromJson(toJson(object), new TypeToken<Map<String, String>>() {
        }.getType());
    }


}
