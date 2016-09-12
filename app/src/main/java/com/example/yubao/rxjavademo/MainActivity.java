package com.example.yubao.rxjavademo;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.yubao.rxjavademo.http.RetrofitModule;
import com.example.yubao.rxjavademo.model.response.WeiXinDataListRes;
import com.example.yubao.rxjavademo.rxjava.RetrofitTransformer;
import com.example.yubao.rxjavademo.rxjava.ThreadTransformer;
import com.gj.base.lib.utils.L;
import com.gj.base.lib.utils.T;

import org.reactivestreams.Publisher;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.ResourceSubscriber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    public void test1() {
        //Observable
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("aaa");
                        e.onNext("bAbb");
                        e.onNext("ccc");
                        e.onNext("ddAd");
                        e.onNext("ddd");
                        e.onNext("ddad");
                        e.onNext("ddAd");
                        e.onNext("ddAAAAd");
                        e.onNext("ddAaad");
                        e.onNext("ddAd");
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.contains("a");
                    }
                })
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.just("contains a：" + s);
                    }
                })
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String s) throws Exception {
                        L.i("----------->>>>>---" + SystemClock.currentThreadTimeMillis() + "--" + s);
                    }
                });
        //Flowable
        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        e.onNext("aaa");
                        e.onNext("bAbb");
                        e.onNext("ccc");
                        e.onNext("ddAd");
                        e.onNext("ddd");
                        e.onNext("ddad");
                        e.onNext("ddAd");
                        e.onNext("ddAAAAd");
                        e.onNext("ddAaad");
                        e.onNext("ddAd");
                        e.onComplete();
                    }
                }, FlowableEmitter.BackpressureMode.DROP)
                .compose(ThreadTransformer.<String>applySchedulers())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return s.contains("a");
                    }
                })
                .flatMap(new Function<String, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(String s) throws Exception {
                        return Flowable.just("contains a：" + s);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        L.i("test1--t>--" + SystemClock.currentThreadTimeMillis() + "--" + s);
                    }
                });
        Flowable
                .zip(Flowable.just("11"), Flowable.just("212"), new BiFunction<String, String, String>() {
                    @Override
                    public String apply(String s, String s2) throws Exception {
                        return s + s2;
                    }
                })
                .compose(ThreadTransformer.<String>applySchedulers())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return false;
                    }
                })
                .subscribe(new ResourceSubscriber<String>() {
                    @Override
                    public void onNext(String s) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    Disposable test2_Disposable;
    Disposable test2_Disposable2;

    public void test2() {
        //count down by Observable
        test2_Disposable = Observable.interval(1, TimeUnit.SECONDS)
                .take(60)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        L.i("test2_Observable--" + aLong);
                    }
                });
        //count down by Flowable
        test2_Disposable2 = Flowable.interval(1, TimeUnit.SECONDS)
                .take(60)
                .compose(ThreadTransformer.<Long>applySchedulers())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        L.i("test2_Flowable--" + aLong);
                        T.show(MainActivity.this, aLong + "");
                    }
                });
    }

    private ResourceSubscriber<String> resourceSubscriber;

    public void test3() {
        resourceSubscriber = new ResourceSubscriber<String>() {
            @Override
            public void onNext(String s) {
                L.i("-----test3> onNext-" + s);
            }

            @Override
            public void onError(Throwable t) {
                L.i("-----test3> onError");
            }

            @Override
            public void onComplete() {
                L.i("-----test3> onComplete");
            }
        };
        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        e.onNext("aaaaa");
                        e.onNext("bbbbb");
                        e.onNext("ccccc");
                        e.onNext("ddddd");
                        e.onComplete();
                    }
                }, FlowableEmitter.BackpressureMode.DROP)
                .compose(ThreadTransformer.<String>applySchedulers())
                .subscribe(resourceSubscriber);
    }

    private ResourceSubscriber<WeiXinDataListRes> retrofit2;

    /**
     * Retrofit2 & how to use compose(FlowableTransformer r)
     */
    public void test_Retrofit2() {
        retrofit2 = new ResourceSubscriber<WeiXinDataListRes>() {
            @Override
            public void onNext(WeiXinDataListRes weiXinDataListRes) {
                L.i("---->test_Retrofit2--onNext--" + weiXinDataListRes.toString());
            }

            @Override
            public void onError(Throwable t) {
                if (t instanceof NullPointerException) {
                    L.i("---->test_Retrofit2--Throwable--NullPointerException");
                } else {
                    L.i("---->test_Retrofit2--Throwable--" + t.toString());
                }
            }

            @Override
            public void onComplete() {
                L.i("---->test_Retrofit2--onComplete--");
            }
        };
        RetrofitModule.getService().getWeiXin("1")
                .compose(ThreadTransformer.<WeiXinDataListRes>applySchedulers())
                .compose(RetrofitTransformer.<WeiXinDataListRes>applySchedulers())
                .subscribe(retrofit2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (resourceSubscriber != null) {
            resourceSubscriber.dispose();
        }
        if (test2_Disposable2 != null) {
            test2_Disposable2.dispose();
        }
        if (retrofit2 != null) {
            retrofit2.dispose();
        }
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                test1();
                break;
            case R.id.btn2:
                test2();
                break;
            case R.id.btn3:
                test3();
                break;
            case R.id.btn4:
                test_Retrofit2();
                break;
            case R.id.btn5:
                break;
            case R.id.btn6:
                break;
            case R.id.btn7:
                break;
            case R.id.btn8:
                break;
            case R.id.btn9:
                break;
            case R.id.btn10:
                break;
            default:
                break;
        }
    }
}
