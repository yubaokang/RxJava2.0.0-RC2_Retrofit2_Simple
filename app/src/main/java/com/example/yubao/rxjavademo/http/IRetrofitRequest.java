package com.example.yubao.rxjavademo.http;


import com.example.yubao.rxjavademo.model.response.LoginData;
import com.example.yubao.rxjavademo.model.response.NewsDataRes;
import com.example.yubao.rxjavademo.model.response.WeatherDataRes;
import com.example.yubao.rxjavademo.model.response.WeiXinDataListRes;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by yubaokang
 */
public interface IRetrofitRequest {

    /**
     * 来源聚合数据
     */

    /**
     * 天气预报
     *
     * @param cityName
     * @return
     */
    @FormUrlEncoded
    @POST("http://op.juhe.cn/onebox/weather/query?key=dd88019df4220cc4929acbe013af42d5")
    Flowable<WeatherDataRes> getWeather(@Field("cityname") String cityName);

    /**
     * 微信精选
     *
     * @param pno
     * @return
     */
    @FormUrlEncoded
    @POST("http://v.juhe.cn/weixin/query?key=b1d95432ce5be3b2d6f6bd64780b592d")
    Flowable<WeiXinDataListRes> getWeiXin(@Field("pno") String pno);

    /**
     * 新闻头条
     *
     * @param pno
     * @param typeName 类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)
     * @return
     */
    @FormUrlEncoded
    @POST("http://v.juhe.cn/toutiao/index?key=0c7bb0b02f3750d523e39ab8ad7ab373")
    Flowable<NewsDataRes> getNews(@Field("pno") String pno, @Field("type") String typeName);

    /**
     * 登录
     *
     * @param userName 用户名
     * @param password 密码 md5
     * @return
     */
    @FormUrlEncoded
    @POST("http://nphoneapi.goujiawang.com/NAUserInfoWebsiteJson/login.html")
    Flowable<LoginData> login(@Field("userName") String userName, @Field("password") String password);
}
