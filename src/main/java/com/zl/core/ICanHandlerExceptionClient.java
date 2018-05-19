package com.zl.core;

import com.zl.callback.OnTimeoutHandler;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 在连接超时时可以进行处理的客户端接口
 *
 * @author 张乐
 * @create 2018-05-19 12:27
 **/
public interface ICanHandlerExceptionClient<REQ,RSP> extends IClient<REQ,RSP>{

    /**
     *  发送一个packet包并返回响应
     * @param packet 请求包
     * @param onTimeoutHandler 连接超时时回调方法
     * @return 响应包
     */
    RSP execute(REQ packet,OnTimeoutHandler<REQ,RSP> onTimeoutHandler);

    /**
     * 发送多个包，并返回packet列表
     * @param onTimeoutHandler 连接超时时回调方法
     * @param list 请求包列表
     * @return 响应包
     */
    List<RSP> execute(OnTimeoutHandler<REQ,RSP> onTimeoutHandler,REQ... list);

    /**
     * 发送一个包完成后执行回调方法
     * @param packet 请求包
     * @param callback 回掉方法
     * @param onTimeoutHandler 连接超时时回调方法
     * @throws TimeoutException 连接超时时抛出异常
     */
    void execute(REQ packet,ClientCallback<RSP> callback,OnTimeoutHandler<REQ,RSP> onTimeoutHandler);

    /**
     * 发送多个包,每次完成后执行回调方法
     * @param callback 回调方法
     * @param onTimeoutHandler 连接超时时回调方法
     * @param list 请求包列表
     */
    void execute(ClientCallback<RSP> callback,OnTimeoutHandler<REQ,RSP> onTimeoutHandler,REQ... list);
}
