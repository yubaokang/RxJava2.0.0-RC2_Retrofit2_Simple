package com.example.yubao.rxjavademo.http;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

/**
 * Created by yubaokang on 2016/9/14.
 */
public interface IRetrofitDownload {
    @Streaming
    @GET("http://122.228.72.141/imtt.dd.qq.com/16891/3370B7BBF52CC0548B8D31B265871AEF.apk?mkey=57d89d6d1b42c68f&f=d511&c=0&fsname=goujiawang.gjw_3.0.4_13.apk&hsr=4d5s&p=.apk")
    Flowable<ResponseBody> downLoadApk();
}
