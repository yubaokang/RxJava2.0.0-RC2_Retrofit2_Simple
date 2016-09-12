package com.example.yubao.rxjavademo;

import android.app.Application;

import com.example.yubao.rxjavademo.http.RetrofitModule;

/**
 * Created by yubaokang on 2016/9/12.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitModule.getService();
    }
}
