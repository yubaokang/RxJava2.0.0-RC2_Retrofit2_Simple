package com.example.yubao.rxjavademo.http;

import com.gj.base.lib.utils.L;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yubaokang
 */
public class RetrofitModule {

    private static IRetrofitRequest request;

    public static IRetrofitRequest getService() {
        if (request == null) {
            //拦截器
            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request authorised = request
                            .newBuilder()
                            .header("registeredChannels", "2")//来自1：iOS,2:Android,3:web
                            .build();
                    return chain.proceed(authorised);
                }
            };
            //打印拦截器
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient httpClient = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)//添加拦截器
                    .addInterceptor(logging)//添加打印拦截器
                    .connectTimeout(15, TimeUnit.SECONDS)//设置请求超时时间
                    .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(UrlConst.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build();
            L.i("------------>getService()");
            request = retrofit.create(IRetrofitRequest.class);
        }
        return request;
    }

//    public <T> Subscription execute(rx.Observable<Result<T>> observable, CallBack<T> subscriber) {
//        return observable.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap(new Func1<Result<T>, Observable<T>>() {
//
//                    public Observable<T> call(Result<T> t) {
//                        Result result = (Result) t;
//                        if (result.getStatus() == 0) {
//                            return Observable.error(new ServerException(result.getMsg()));
//                        }
//                        return Observable.just((T) result.getData());
//                    }
//                })
//                .subscribe(subscriber);
//    }
}

