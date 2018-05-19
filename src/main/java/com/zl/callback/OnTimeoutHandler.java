package com.zl.callback;

import com.zl.core.ICanHandlerExceptionClient;

import java.util.concurrent.TimeoutException;

/**
 * 连接超时时回调handler方法
 *
 * @author 张乐
 * @create 2018-05-19 12:34
 **/
public interface OnTimeoutHandler<REQ,RSP> {

    void handler(TimeoutException exception, ICanHandlerExceptionClient<REQ,RSP> client, REQ request);
}
