package com.example.yubao.rxjavademo.rxjava;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Retrofit2  线程切换，flatMap检测Response model是否为null
 * Created by yubaokang on 2016/9/12.
 */
public class RetrofitTransformer {
    public static <T> FlowableTransformer<T, T> applySchedulers() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<? extends T> apply(Flowable<T> flowable) throws Exception {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<T, Publisher<T>>() {//检测Response model是否为null
                            @Override
                            public Publisher<T> apply(T t) throws Exception {
                                if (t != null) {
                                    Flowable.just(t);
                                }
                                return Flowable.error(new NullPointerException("response model is null"));
                            }
                        });
            }
        };
    }
}
