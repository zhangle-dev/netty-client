package com.zl.exception;

/**
 * 访问超时，没有设置超时时返回的对象
 *
 * @author 张乐
 * @create 2018-05-19 14:52
 **/
public class NotTimeoutResponseException extends RuntimeException{
    public NotTimeoutResponseException() {
        super("访问超时，没有设置超时时返回的对象");
    }
}
