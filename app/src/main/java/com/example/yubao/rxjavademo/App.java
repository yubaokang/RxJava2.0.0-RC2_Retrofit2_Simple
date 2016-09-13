package com.example.yubao.rxjavademo;

import android.app.Application;
import android.content.Context;

import com.example.yubao.rxjavademo.http.RetrofitModule;

/**
 * Created by yubaokang on 2016/9/12.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        RetrofitModule.getService();
    }

    public static Context getContext() {
        return context;
    }
}
