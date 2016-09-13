package com.example.yubao.rxjavademo.rxjava;

/**
 * 自定义异常类-api接口调用 服务器错误异常
 * Created by yubaokang on 2016/9/13.
 */
public class ServerException extends Exception {

    /**
     * Constructs a {@code ServerException} with no detail message.
     */
    public ServerException() {
        super();
    }

    /**
     * Constructs a {@code ServerException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ServerException(String s) {
        super(s);
    }
}
