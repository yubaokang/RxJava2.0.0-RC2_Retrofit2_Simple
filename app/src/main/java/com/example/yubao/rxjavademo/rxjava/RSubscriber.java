package com.example.yubao.rxjavademo.rxjava;

import android.content.Context;

import com.gj.base.lib.utils.NetworkStateUtils;

import io.reactivex.subscribers.ResourceSubscriber;

/**
 * 配合Retrofit使用 网络请求使用的RxJava观察者
 * Created by yubaokang on 2016/9/13.
 */
public abstract class RSubscriber<T> extends ResourceSubscriber<T> {

    private Context context;

    protected RSubscriber(Context context) {
        this.context = context;
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    @Override
    public void onError(Throwable t) {
        /**
         *如果网络未连接不会调用flatMap 所以网络未连接不会出现ServerException错误 {@link Transformer#retrofit()}.
         */
        if (!NetworkStateUtils.getInstance(context).isConnection()) {
            _onNetWorkError();//network unConnected
        } else {
            if (t instanceof ResponseNullException) {
                _onEmpty();
            } else if (t instanceof ServerException) {
                _onServerError(t.getMessage());
            } else {
                _onNetWorkError();//UnknownHostException 1：服务器地址错误；2：网络未连接
            }
        }
    }

    @Override
    public void onComplete() {
        _onComplete();
    }

    public abstract void _onNext(T o);//onNext()

    public abstract void _onEmpty();//返回的response为空，或者List数组size为0

    public abstract void _onNetWorkError();//网络未连接

    public abstract void _onServerError(String returnCode);//接口调用操作出现异常，比如注册失败（已注册,短信验证码出错,and so on）

    public abstract void _onComplete();//onComplete()

}
