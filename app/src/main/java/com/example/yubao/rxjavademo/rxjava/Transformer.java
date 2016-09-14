package com.example.yubao.rxjavademo.rxjava;

import com.example.yubao.rxjavademo.http.ReturnCode;
import com.example.yubao.rxjavademo.model.response.BaseRes;

import org.reactivestreams.Publisher;

import java.util.List;

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
            public Publisher<? extends T> apply(Flowable<BaseRes<T>> flowable) throws Exception {
                return flowable
                        .flatMap(new Function<BaseRes<T>, Publisher<T>>() {
                            @Override
                            public Publisher<T> apply(BaseRes<T> t) throws Exception {
                                if (t == null || (t instanceof List && ((List) t).size() == 0)) {
                                    return Flowable.error(new ResponseNullException("response's model is null or response's list size is 0"));
                                } else {
                                    if (ReturnCode._0000.equals(t.getReturnCode())) {//如果返回时"0000"表示数据请求正常
                                        return Flowable.just(t.getData());
                                    } else {
                                        //如果网络未连接不会调用flatMap,所以网络未连接不会出现ServerException错误
                                        return Flowable.error(new ServerException(t.getReturnCode(), t.getMsg()));//return the response's returnCode and msg
                                    }
                                }
                            }
                        })
                        .compose(Transformer.<T>ioMain());//线程切换
            }
        };
    }
}
