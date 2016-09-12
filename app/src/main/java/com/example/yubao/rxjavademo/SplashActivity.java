package com.example.yubao.rxjavademo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class SplashActivity extends AppCompatActivity {

    private ResourceSubscriber<Long> resourceSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        resourceSubscriber = new ResourceSubscriber<Long>() {
            @Override
            public void onNext(Long aLong) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(Throwable t) {
            }

            @Override
            public void onComplete() {
            }
        };
        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resourceSubscriber);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        resourceSubscriber.dispose();
    }

    @Override
    public void onBackPressed() {
        //do nothing
    }
}
