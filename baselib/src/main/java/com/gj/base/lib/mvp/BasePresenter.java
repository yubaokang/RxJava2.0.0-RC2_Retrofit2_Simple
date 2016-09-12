package com.gj.base.lib.mvp;

public interface BasePresenter {
    void start();

    void unsubscribe();//RxJava 取消订阅
}
