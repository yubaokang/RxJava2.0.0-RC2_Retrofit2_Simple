package com.example.yubao.rxjavademo.http;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by yubaokang on 2016/9/14.
 */

public interface IRetrofitUpload {
    @Multipart
    @POST("upload")
    Flowable<ResponseBody> upload(@Part("description") RequestBody description, @Part MultipartBody.Part file);
}
