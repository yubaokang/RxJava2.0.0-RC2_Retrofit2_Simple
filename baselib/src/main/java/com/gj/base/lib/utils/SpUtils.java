package com.gj.base.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hank on 2016/1/13/14:14:35
 */
public class SpUtils {

    private static final String FILE_NAME = "goujia_craftsman";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static SpUtils spUtils;

    private SpUtils(Context context) {
        sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public synchronized static SpUtils getInstance(Context context) {
        if (spUtils == null) {
            spUtils = new SpUtils(context);
        }
        return spUtils;
    }

    public void setStringParam(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringParam(String key) {
        return sp.getString(key, "");
    }

    public void setBooleanParam(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public Boolean getBooleanParam(String key) {
        return sp.getBoolean(key, false);
    }

    public void saveObject2Json(String key, Object object) {
        String json = GsonUtils.getInstance().toJson(object);
        setStringParam(key, json);
    }

    public void remove(String key) {
        editor.remove(key).apply();
    }

    public <D> D getObject(String key, Class<D> d) {
        String json = getStringParam(key);
        if (!json.isEmpty()) {
            return GsonUtils.getInstance().getObject(json, d);
        }
        return null;
    }
}
