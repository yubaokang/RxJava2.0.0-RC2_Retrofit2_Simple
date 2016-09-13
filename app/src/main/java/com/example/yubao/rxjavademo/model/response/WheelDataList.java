package com.example.yubao.rxjavademo.model.response;

/**
 * 轮播页
 * Created by Hank on 2016/7/26.
 */
public class WheelDataList {

    /**
     * imagePath : http://timage.goujiawang.com/store/imagePath
     * urlPath : NAHTMLView/getRecommendationView
     */

    private String imagePath;
    private String urlPath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }
}