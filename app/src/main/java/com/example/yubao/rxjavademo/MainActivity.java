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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.flowables.GroupedFlowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
        //Observable
//        Observable
//                .create(new ObservableOnSubscribe<String>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<String> e) throws Exception {
//                        e.onNext("aaa");
//                        e.onNext("bAbb");
//                        e.onNext("ccc");
//                        e.onNext("ddAd");
//                        e.onNext("ddd");
//                        e.onNext("ddad");
//                        e.onNext("ddAd");
//                        e.onNext("ddAAAAd");
//                        e.onNext("ddAaad");
//                        e.onNext("ddAd");
//                        e.onComplete();
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Predicate<String>() {
//                    @Override
//                    public boolean test(String s) throws Exception {
//                        return s.contains("a");
//                    }
//                })
//                .flatMap(new Function<String, ObservableSource<String>>() {
//                    @Override
//                    public ObservableSource<String> apply(String s) throws Exception {
//                        return Observable.just("contains a：" + s);
//                    }
//                })
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String s) throws Exception {
//                        L.i("----------->>>>>---" + SystemClock.currentThreadTimeMillis() + "--" + s);
//                    }
//                });

        //Flowable
        Flowable
                .create(new FlowableOnSubscribe<String>() {
                    @Override
                    public void subscribe(FlowableEmitter<String> e) throws Exception {
                        L.i("rx_call__create__" + Thread.currentThread().getName());
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
                }, BackpressureStrategy.DROP)
                .compose(Transformer.<String>ioMain())
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
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        L.i("rx_call__subscribe__" + Thread.currentThread().getName());
                        L.i("test1--t>--" + SystemClock.currentThreadTimeMillis() + "--" + s);
                        showText(s);
                    }
                });
    }

    Disposable test2_Disposable;
    Disposable test2_Disposable2;

    public void test2() {
        //count down by Observable
//        test2_Disposable = Observable.interval(1, TimeUnit.SECONDS)
//                .take(60)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        L.i("test2_Observable--" + aLong);
//                    }
//                });
        //count down by Flowable
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
        String aaaa = "";
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
                .zip(Flowable.timer(3, TimeUnit.SECONDS), Flowable.just("212"), new BiFunction<Long, String, String>() {
                    @Override
                    public String apply(Long s, String s2) throws Exception {
                        L.i("rx_call__zip__" + Thread.currentThread().getName());
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
                .subscribe(new ResourceSubscriber<String>() {
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
                .groupBy(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer) throws Exception {
                        return integer % 2;
                    }
                })
                .flatMap(new Function<GroupedFlowable<Integer, Integer>, Publisher<Integer>>() {
                    @Override
                    public Publisher<Integer> apply(@NonNull GroupedFlowable<Integer, Integer> integerIntegerGroupedFlowable) throws Exception {
                        return integerIntegerGroupedFlowable.flatMap();
                    }
                })

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

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
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
