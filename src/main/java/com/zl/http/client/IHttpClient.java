package com.zl.http.client;

import com.zl.core.ClientCallback;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * 用于发送http请求的接口
 */
public interface IHttpClient {

    /**
     * 发送http请求
     * @param request http请求包
     * @param callback http请求执行成功时执行的代码
     */
    void request(HttpRequest request, ClientCallback<HttpResponse> callback);

}
