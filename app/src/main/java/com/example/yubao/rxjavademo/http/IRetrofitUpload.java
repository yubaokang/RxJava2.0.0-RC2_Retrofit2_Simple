package com.example.yubao.rxjavademo.http;

import com.google.gson.JsonObject;

import io.reactivex.Flowable;
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
    @POST("https://api.imgur.com/3/image")
    Flowable<ResponseBody> upload(@Part("file\"; filename=\"") RequestBody externalFileParameters);

    @Multipart
    @POST("/upload")
    Flowable<JsonObject> uploadImage(@Part("upload\"; filename=\"1\" ") ProgressRequestBody requestBody);
}
