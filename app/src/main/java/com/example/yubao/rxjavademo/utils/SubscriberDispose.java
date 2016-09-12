package com.example.yubao.rxjavademo.utils;

import io.reactivex.disposables.Disposable;

/**
 * Created by yubao on 2016/9/12.
 */

public class SubscriberDispose {

    public static SubscriberDispose subscriberDispose = new SubscriberDispose();

    public SubscriberDispose dispose(Disposable disposable) {
        if (disposable != null) {
            disposable.dispose();
        }
        return subscriberDispose;
    }
}
