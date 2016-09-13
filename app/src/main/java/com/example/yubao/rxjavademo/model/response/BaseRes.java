package com.example.yubao.rxjavademo.model.response;

/**
 * Created by yubaokang on 2016/9/13.
 */
public class BaseRes<T> {
    private String msg;
    private String returnCode;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
