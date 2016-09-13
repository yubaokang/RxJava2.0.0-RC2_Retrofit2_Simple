package com.example.yubao.rxjavademo.rxjava;

import io.reactivex.disposables.Disposable;

/**
 * Created by yubao on 2016/9/12.
 */
public class SubscriberDispose {

    public static Builder builder() {
        return new SubscriberDispose.Builder();
    }

    public static class Builder {
        public Builder dispose(Disposable disposable) {
            if (disposable != null) {
                disposable.dispose();
            }
            return this;
        }
    }
}
