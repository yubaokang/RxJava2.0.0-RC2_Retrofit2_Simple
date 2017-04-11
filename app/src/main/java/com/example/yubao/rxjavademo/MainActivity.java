package com.example.yubao.rxjavademo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.yubao.rxjavademo.http.ProgressRequestBody;
import com.example.yubao.rxjavademo.http.ProgressResponseBody;
import com.example.yubao.rxjavademo.http.RetrofitModule;
import com.example.yubao.rxjavademo.model.response.WheelDataList;
import com.example.yubao.rxjavademo.rxjava.RSubscriber;
import com.example.yubao.rxjavademo.rxjava.Transformer;
import com.example.yubao.rxjavademo.utils.SubscriberDispose;
import com.gj.base.lib.utils.GsonUtils;
import com.gj.base.lib.utils.L;
import com.gj.base.lib.utils.T;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableOnSubscribe;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.flowable.FlowableUsing;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.schedulers.Timed;
import io.reactivex.subjects.Subject;
import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.tv_show)
    TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public void test1() {
        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        L.i("rx_call__create__" + Thread.currentThread().getName());
                        e.onNext("aaa");
                        e.onNext("bAabb");
                        e.onNext("ccAaAc");
                        e.onNext("ddAaAd");
                        e.onNext("ddAaAd");
                        e.onNext("ddad");
                        e.onNext("ddaAd");
                        e.onNext("ddaAAAAAd");
                        e.onNext("ddAaad");
                        e.onNext("ddaAd");
                        e.onComplete();
                    }
                }, BackpressureStrategy.DROP).compose(Transformer.<String>ioMain())
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        L.i("rx_call__filter__" + Thread.currentThread().getName());
                        return s.contains("a");
                    }
                })
                .flatMap(new Function<String, Publisher<String>>() {
                    @Override
                    public Publisher<String> apply(String s) throws Exception {
                        L.i("rx_call__flatMap__" + Thread.currentThread().getName());
                        return Flowable.just("contains a：" + s);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        L.i("rx_call__subscribe__" + Thread.currentThread().getName());
                        L.i("test1--t>--" + SystemClock.currentThreadTimeMillis() + "--" + s);
                        showText(s);
                    }
                });
    }

    Disposable test2_Disposable2;

    public void test2() {
        test2_Disposable2 = Flowable.interval(1, TimeUnit.SECONDS)//interval default thread is Schedulers.computation()
                .take(60)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        L.i("test2_Flowable--" + aLong);
                        L.i("rx_call__subscribe__" + Thread.currentThread().getName());
                        T.show(MainActivity.this, aLong + "");
                        showText(aLong + "");
                    }
                });
    }

    private ResourceSubscriber<String> resourceSubscriber;

    public void test3() {
        resourceSubscriber = Flowable.
                create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        L.i("rx_call__create__" + Thread.currentThread().getName());
                        e.onNext("aaaaa");
                        e.onNext("bbbbb");
                        e.onNext("ccccc");
                        e.onNext("ddddd");
                        e.onComplete();
                    }
                }, BackpressureStrategy.DROP)
                .compose(Transformer.<String>ioMain())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        L.i("-----test3> accept-" + s);
                    }
                })
                .subscribeWith(new ResourceSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        L.i("rx_call__subscribe__" + Thread.currentThread().getName());
                        L.i("-----test3> onNext-" + s);
                        showText(s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        L.i("-----test3> onError");
                    }

                    @Override
                    public void onComplete() {
                        L.i("-----test3> onComplete");
                    }
                });
    }

    private ResourceSubscriber<List<WheelDataList>> retrofit2;

    /**
     * Retrofit2 & how to use compose(FlowableTransformer r)
     */
    public void test_Retrofit2() {
        retrofit2 = RetrofitModule.getService().getWheelList("1")
                .compose(Transformer.<List<WheelDataList>>retrofit())
                .doOnNext(new Consumer<List<WheelDataList>>() {
                    @Override
                    public void accept(List<WheelDataList> wheelDataLists) throws Exception {
                        L.i("rx_call__doOnNext__" + Thread.currentThread().getName());
                    }
                })
                .subscribeWith(new RSubscriber<List<WheelDataList>>(this) {
                    @Override
                    public void _onNext(List<WheelDataList> o) {
                        L.i("rx_call__subscribe__" + Thread.currentThread().getName());
                        L.i("---->test_Retrofit2--onNext--" + o.toString());
                        showText("返回List数组 size=" + o.size());
                    }

                    @Override
                    public void _onEmpty() {
                        L.i("---->test_Retrofit2--_onEmpty--");
                    }

                    @Override
                    public void _onNetWorkError() {
                        L.i("---->test_Retrofit2--_onNetWorkError--");
                    }

                    @Override
                    public void _onServerError(String returnCode, String msg) {
                        L.i("_onServerError" + returnCode + ":" + msg);
                    }

                    @Override
                    public void _onComplete() {
                        L.i("---->test_Retrofit2--onComplete--");
                    }
                });
    }

    public void test_zip() {
        Flowable
                .zip(Flowable.timer(3, TimeUnit.SECONDS), Flowable.just("212"), Flowable.just("aaaaa"), new Function3<Long, String, String, String>() {
                    @Override
                    public String apply(@NonNull Long aLong, @NonNull String s, @NonNull String s2) throws Exception {
                        L.i("rx_call__zip__    " + s + "   -----" + Thread.currentThread().getName());
                        return s + s2;
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        L.i("rx_call__filter__" + Thread.currentThread().getName());
                        return s.contains("12");
                    }
                })
                .compose(Transformer.<String>ioMain())
                .subscribe(new ResourceSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        L.i("rx_call__subscribe__" + Thread.currentThread().getName());
                        showText(s);
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void test_downLoadApk() {
        RetrofitModule
                .getDownload(new ProgressResponseBody.ProgressListener() {
                    @Override
                    public void update(final long bytesRead, final long contentLength, final boolean done) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                L.i("-------下载进度-->" + bytesRead + "/" + contentLength + "/" + done + "--线程--" + Thread.currentThread().getName());
                                notification((int) (bytesRead * 100 / contentLength));
                            }
                        });
                    }
                })
                .downLoadApk()
                .compose(Transformer.<ResponseBody>ioMain())
                .subscribeWith(new DefaultSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        T.show(MainActivity.this, "下载完成" + responseBody.contentLength());
                        L.i("--------------->下载完成" + responseBody.contentLength());
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void test_upload() {
        File file = new File("/storage/emulated/0/DCIM/Screenshots/IMG_20160604_192104.jpg");
        final ProgressRequestBody requestBody = new ProgressRequestBody(file, new ProgressRequestBody.ProgressListener() {
            @Override
            public void onProgress(long hasWrittenLen, long totalLen, boolean hasFinish) {
                L.i("---->上传进度" + 100 * hasWrittenLen / totalLen + "--thread->" + Thread.currentThread().getName());
            }
        });
        RetrofitModule.getUpload().upload(requestBody)
                .compose(Transformer.<ResponseBody>ioMain())
                .subscribeWith(new DefaultSubscriber<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        L.i("---->onNext" + GsonUtils.getInstance().toJson(responseBody));
                    }

                    @Override
                    public void onError(Throwable t) {
                        L.i("---->onError" + t.toString());
                    }

                    @Override
                    public void onComplete() {
                        L.i("---->onComplete");
                    }
                });
    }

    void test_isEmpty() {
        List<String> list = new ArrayList<>();
        list.add("aaaaa");
        list.add("");
        list.add("bbbbb");
        list.add("");
        Flowable.fromIterable(list)
                .takeWhile(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        L.i("===========>1111" + s);
                        return !TextUtils.isEmpty(s);
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        L.i("===========>2222" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        L.i("===========>3333");
                    }

                    @Override
                    public void onComplete() {
                        L.i("===========>4444");
                    }
                });
    }

    void test_9() {
        Flowable.range(1, 10)
                .groupBy(new Function<Integer, String>() {
                    @Override
                    public String apply(@NonNull Integer integer) throws Exception {
                        L.i("-------->>>>1111111111");
                        return integer % 2 == 1 ? "奇数组" : "偶数组";
                    }
                })
//                .flatMap(new Function<GroupedFlowable<String, Integer>, Publisher<?>>() {
//                    @Override
//                    public Publisher<?> apply(@NonNull GroupedFlowable<String, Integer> stringIntegerGroupedFlowable) throws Exception {
//                        L.i("----->>" + "aaaaaaa");
//                        return null;
//                    }
//                })
                .subscribe(new Consumer<GroupedFlowable<String, Integer>>() {
                    @Override
                    public void accept(@NonNull final GroupedFlowable<String, Integer> stringIntegerGroupedFlowable) throws Exception {
                        if (stringIntegerGroupedFlowable.getKey().equalsIgnoreCase("奇数组"))
                            stringIntegerGroupedFlowable.subscribe(new Consumer<Integer>() {
                                @Override
                                public void accept(@NonNull Integer integer) throws Exception {
                                    L.i("-----------groupBy-->>" + stringIntegerGroupedFlowable.getKey() + ":" + integer);
                                }
                            });
                    }
                });

        // 扫描，对Observable发射的每一项数据应用一个函数，然后按顺序依次发射这些值
//                .scan(new BiFunction<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
//                        L.i("---------->>>" + integer + "---" + integer2);
//                        return integer + integer2;
//                    }
//                })


        Flowable.defer(new Callable<Publisher<String>>() {
            @Override
            public Publisher<String> call() throws Exception {
                return Flowable.just("hahahahah");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                L.i("------------>>>>" + s);
            }
        });

        Subject.just("aaaaaa")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        L.i("---------->>>>" + s);
                    }
                });

        Flowable<String> flowable = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                e.onNext("hahahah");
                e.onComplete();
            }
        }, BackpressureStrategy.DROP);
        Subscriber<String> s = new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                L.i("--------test_9---1>");
                s.request(1);
            }

            @Override
            public void onNext(String s) {
                L.i("--------test_9---2>" + s);
            }

            @Override
            public void onError(Throwable t) {
                L.i("--------test_9---3>");
            }

            @Override
            public void onComplete() {
                L.i("--------test_9---4>");
            }
        };
        ResourceSubscriber<String> subscriber = new ResourceSubscriber<String>() {
            @Override
            public void onNext(String s) {
                L.i("--------test_9---5>" + s);
            }

            @Override
            public void onError(Throwable t) {
                L.i("--------test_9---6>" + "onError");
            }

            @Override
            public void onComplete() {
                L.i("--------test_9---7>" + "onComplete");
            }
        };
        Flowable.just("1", "2", "3").subscribeWith(s);
//        flowable.subscribe(s);
//        flowable.subscribe(subscriber);
    }

    public void test10() {
//        Flowable.interval(1, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(@NonNull Long aLong) throws Exception {
//                        L.i("-------test10>>>" + aLong);
//                    }
//                });
        Flowable.range(2, 100)
                .timeInterval(TimeUnit.MILLISECONDS)
//                .flatMap(new Function<Timed<Integer>, Publisher<Integer>>() {
//                    @Override
//                    public Publisher<Integer> apply(@NonNull Timed<Integer> integerTimed) throws Exception {
//                        L.i("-------test10>>>" + integerTimed.toString());
//                        return null;
//                    }
//                });
                .subscribe(new Consumer<Timed<Integer>>() {
                    @Override
                    public void accept(@NonNull Timed<Integer> integerTimed) throws Exception {
                        L.i("-------test10>>>" + integerTimed.toString());
                    }
                });
    }

    public void test11() {
        Flowable<Long> fs = Flowable.timer(2, TimeUnit.SECONDS);

        Flowable<String> fs1 = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                e.onNext("22222");
            }
        }, BackpressureStrategy.DROP);

        Flowable.combineLatest(fs, fs1, fs, new Function3<Long, String, Long, String>() {
            @Override
            public String apply(@NonNull Long aLong, @NonNull String s, @NonNull Long aLong2) throws Exception {
                L.i("--------test11--->" + aLong + "---" + s);
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                L.i("--------test11--->" + s);
            }
        });
    }

    public void test12() {
        Flowable<Integer> fs = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                e.onNext(11111111);
            }
        }, BackpressureStrategy.DROP);

        Flowable<String> fs1 = Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                e.onNext("22222");
            }
        }, BackpressureStrategy.DROP);

        Flowable.merge(Flowable.range(1, 20), fs1)
                .subscribe(new Consumer<Serializable>() {
                    @Override
                    public void accept(@NonNull Serializable serializable) throws Exception {
                        L.i("-------test12>>" + serializable);
                    }
                });
    }

    public void test13() {
        Flowable.just("saaa")
                .startWith("startWith--先发射一组数据")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        L.i("-----test13>>" + s);
                    }
                });
    }

    public void test14() {
        final int[] count = {0};
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<String> e) throws Exception {
                if (count[0] < 90) {
                    L.i("---------test14>>>" + count[0]);
                    e.onError(new Throwable("发送错误"));
                } else {
                    L.i("---------test14>>>" + count[0]);
                    e.onNext("发射成功");
                    e.onComplete();
                }
                count[0]++;
            }
        }, BackpressureStrategy.DROP)
                .retry(89)//指定重试次数
                .subscribe(new ResourceSubscriber<String>() {
                    @Override
                    public void onNext(String s) {
                        L.i("---------test14>>>" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        //指定的重试次数，如果最后一次重试错误，只打印最后一次重试的错误
                        L.i("---------test14>>>" + t.getMessage());
                        /**
                         * 打印如下
                         * ---------test14>>>0
                         * ---------test14>>>1
                         * ---------test14>>>2
                         * ...
                         * ---------test14>>>87
                         * ---------test14>>>88
                         * ---------test14>>>89
                         * --------test14>>>发送错误
                         */
                    }

                    @Override
                    public void onComplete() {
                        L.i("---------test14>>>" + "onComplete");
                    }
                });
    }

    public void test15() {
        Flowable.just("延迟发射数据")
                .delay(3, TimeUnit.SECONDS)
                .timeout(2500, TimeUnit.MILLISECONDS)//添加超时机制，指定的2.5秒没有发射数据，会发射错误通知
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(String s) {
                        L.i("-----test15>>" + s);
                    }

                    @Override
                    public void onError(Throwable t) {
                        //超时会调用onError
                        L.i("-----test15>>" + "onError");
                    }

                    @Override
                    public void onComplete() {
                        L.i("-----test15>>" + "onComplete");
                    }
                });
    }

    public void test16() {
//        Flowable.interval(1, TimeUnit.SECONDS)
//                .timestamp()
//                .subscribe(new Consumer<Timed<Long>>() {
//                    @Override
//                    public void accept(@NonNull Timed<Long> longTimed) throws Exception {
//                        L.i("------>>test16--为每个发射的数据添加时间戳" + longTimed.toString());
//                    }
//                });
//        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
//                .delay(1, TimeUnit.SECONDS)
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(@NonNull Integer integer) throws Exception {
//                        L.i("------>>test16--为每个发射的数据添加时间戳" + integer);
//                    }
//                });
        final List<Flowable<Integer>> flowables = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            flowables.add(Flowable.just(i));
        }
        Flowable.intervalRange(0, 11, 0, 200, TimeUnit.MILLISECONDS)
                .flatMap(new Function<Long, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(@NonNull Long aLong) throws Exception {
                        return flowables.get(aLong.intValue());
                    }
                })
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        L.i("------>>test16>>" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        L.i("------>>test16>>" + "onError");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void test17() {
//        Flowable.using(new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "test17----using";
//            }
//        }, new Function<String, Publisher<String>>() {
//            @Override
//            public Publisher<String> apply(@NonNull String s) throws Exception {
//                return Flowable.just("hahahahahahahaa");
//            }
//        }, new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                L.i("---------test17>>" + s);
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(@NonNull String s) throws Exception {
//                L.i("---------test17>>" + s);
//            }
//        });
        FlowableUsing.just("test17----using")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        L.i("---------test17>>" + s);
                    }
                });
    }

    public void test18() {
        Flowable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer > 1;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        L.i("-------test18--all操作符>>" + aBoolean);
                    }
                });

        Flowable.just(1, 2, 3, 4, 5)
                .contains(1)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        L.i("-------test18--contains操作符>>" + aBoolean);
                    }
                });
    }

    public void test19() {
        Observable.just(1, 2, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {

                    }
                });
        Single.just(3)
                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Integer integer) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter e) throws Exception {

            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }

    public void test20() {
    }

    public void test21() {
    }

    public void test22() {
    }

    public void test23() {
    }

    public void test24() {
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10
            , R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16, R.id.btn17, R.id.btn18, R.id.btn19, R.id.btn20
            , R.id.btn21, R.id.btn22, R.id.btn23, R.id.btn24})
    public void onClick(View view) {
        clean();
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
                test_zip();
                break;
            case R.id.btn6:
                test_downLoadApk();
                break;
            case R.id.btn7:
                test_upload();
                break;
            case R.id.btn8:
                test_isEmpty();
                break;
            case R.id.btn9:
                test_9();
                break;
            case R.id.btn10:
                test10();
                break;
            case R.id.btn11:
                test11();
                break;
            case R.id.btn12:
                test12();
                break;
            case R.id.btn13:
                test13();
                break;
            case R.id.btn14:
                test14();
                break;
            case R.id.btn15:
                test15();
                break;
            case R.id.btn16:
                test16();
                break;
            case R.id.btn17:
                test17();
                break;
            case R.id.btn18:
                test18();
                break;
            case R.id.btn19:
                test19();
                break;
            case R.id.btn20:
                test20();
                break;
            case R.id.btn21:
                test21();
                break;
            case R.id.btn22:
                test22();
                break;
            case R.id.btn23:
                test23();
                break;
            case R.id.btn24:
                test24();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SubscriberDispose.builder()
                .dispose(resourceSubscriber)
                .dispose(test2_Disposable2)
                .dispose(retrofit2);
    }

    NotificationCompat.Builder builder;
    NotificationManager manager;
    private int notiId = 1;

    public void notification(int progress) {
        if (builder == null) {
            builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_head_normal)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_head_normal))
                    .setAutoCancel(false)//禁止用户点击删除按钮删除
                    .setOngoing(true)//禁止滑动删除
                    .setShowWhen(false);//取消右上角的时间显示
        }
        builder.setProgress(100, progress, false);
        if (progress == 100) {
            builder.setContentTitle("完成");
            builder.setOngoing(false);//下载完成可以滑动删除
            Intent intent = new Intent(this, NotificationShowActivity.class);
            intent.putExtra("extra", "通知的内容");
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addParentStack(NotificationShowActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
        } else {
            builder.setContentTitle("下载中..." + progress + "%");
        }
        manager.notify(notiId, builder.build());
    }

    private StringBuffer old = new StringBuffer();

    public void showText(String add) {
        old.append("\n").append(add);
        tv_show.setText(old.toString());
    }

    public void clean() {
        old = new StringBuffer();
        tv_show.setText(old.toString());
    }
}
