package com.example.yubao.rxjavademo.rxjava;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 配合Retrofit 网络请求使用的RxJava观察者
 * Created by yubaokang on 2016/9/13.
 */
public abstract class RSubscriber<T> extends ResourceSubscriber<T> {
    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        if (t instanceof NullPointerException) {
            _onEmpty();
        } else if (t instanceof ServerException) {
            _onNetWorkError();
        } else {//网络错误
            _onNetWorkError();
        }
    }

    @Override
    public void onComplete() {
        _onComplete();
    }

    public abstract void _onNext(T o);

    public abstract void _onEmpty();

    public abstract void _onNetWorkError();

    public abstract void _onComplete();

}
