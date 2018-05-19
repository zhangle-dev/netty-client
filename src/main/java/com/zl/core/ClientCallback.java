package com.zl.core;

/**
 * 回调方法,用IClient发送消息成功时调用
 * @param <T>
 */
public interface ClientCallback<T> {

	void callback(T packet);
}
