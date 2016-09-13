package com.example.yubao.rxjavademo.rxjava;

import com.example.yubao.rxjavademo.model.response.BaseRes;

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
public class Transformer {

    public static <T> FlowableTransformer<T, T> ioMain() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<? extends T> apply(Flowable<T> flowable) throws Exception {
                return flowable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//    public static <T> FlowableTransformer<T, T> retrofit() {
//        return new FlowableTransformer<T, T>() {
//            @Override
//            public Publisher<? extends T> apply(Flowable<T> flowable) throws Exception {
//                return flowable
//                        .flatMap(new Function<T, Publisher<T>>() {//检测Response model是否为null
//                            @Override
//                            public Publisher<T> apply(T t) throws Exception {
//                                if (t != null) {
//                                    return Flowable.just(t);
//                                }
//                                return Flowable.error(new NullPointerException("response model is null"));
//                            }
//                        })
//                        .compose(Transformer.<T>ioMain());//线程切换
//            }
//        };
//    }

    public static <T> FlowableTransformer<BaseRes<T>, T> retrofit() {
        return new FlowableTransformer<BaseRes<T>, T>() {
            @Override
            public Publisher<? extends T> apply(Flowable<BaseRes<T>> baseResFlowable) throws Exception {
                return baseResFlowable
                        .flatMap(new Function<BaseRes<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(BaseRes<T> t) throws Exception {
                                if (t == null) {
                                    return Flowable.error(new NullPointerException("response model is null"));
                                } else {
                                    if ("0000".equals(t.getReturnCode())) {//如果返回时"0000"表示数据请求正常
                                        return Flowable.just(t.getData());
                                    } else {
                                        return Flowable.error(new ServerException("服务器异常"));
                                    }
                                }
                            }
                        })
                        .compose(Transformer.<T>ioMain());//线程切换
            }
        };
    }
}
