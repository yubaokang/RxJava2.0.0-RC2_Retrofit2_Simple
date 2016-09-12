package com.example.yubao.rxjavademo.rxjava;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Retrofit2  线程切换，flatMap检测Response model是否为null
 * Created by yubaokang on 2016/9/12.
 */
public class ThreadTransformer {
    public static <T> FlowableTransformer<T, T> applySchedulers() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<? extends T> apply(Flowable<T> flowable) throws Exception {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
