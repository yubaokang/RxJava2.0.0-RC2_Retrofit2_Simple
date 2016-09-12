package com.example.yubao.rxjavademo.model.response;

/**
 * Created by ybk on 2016/4/11.
 */
public class BaseError {
    private String string;

    public BaseError(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }
}
