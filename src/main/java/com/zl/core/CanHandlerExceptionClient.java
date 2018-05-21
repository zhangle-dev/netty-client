package com.zl.core;

import com.zl.callback.OnTimeoutHandler;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 可以添加连接超时处理的客户端
 *
 * @author 张乐
 * @create 2018-05-19 14:57
 **/
public class CanHandlerExceptionClient<RQE,RSP> extends Client<RQE,RSP> implements ICanHandlerExceptionClient<RQE,RSP> {

    @Override
    public RSP execute(RQE packet, OnTimeoutHandler<RQE, RSP> onTimeoutHandler) {
        try {
            return execute(packet);
        } catch (TimeoutException e) {
            onTimeoutHandler.handler(e,this,packet);
        }
        return null;
    }

    @Override
    public List<RSP> execute(OnTimeoutHandler<RQE, RSP> onTimeoutHandler, RQE... list) {
        List<RSP> result = null;
        try {
            result = execute(list);
        } catch (TimeoutException e) {
            for (RQE req : list) {
                onTimeoutHandler.handler(e,this,req);
            }
        }
        return result;
    }

    @Override
    public void execute(RQE packet, ClientCallback<RSP> callback, OnTimeoutHandler<RQE, RSP> onTimeoutHandler) {
        try {
            execute(packet, callback);
        } catch (TimeoutException e) {
            onTimeoutHandler.handler(e,this,packet);
        }
    }

    @Override
    public void execute(ClientCallback<RSP> callback, OnTimeoutHandler<RQE, RSP> onTimeoutHandler, RQE... list) {
        try {
            execute(callback, list);
        } catch (TimeoutException e) {
            for (RQE req : list) {
                onTimeoutHandler.handler(e,this,req);
            }
        }
    }
}
