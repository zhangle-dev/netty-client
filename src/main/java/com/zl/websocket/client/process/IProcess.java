package com.zl.websocket.client.process;

import io.netty.handler.codec.http.DefaultFullHttpResponse;

/**
 * 用于处理websocket的消息
 * @author zl
 *
 */
public interface IProcess {
	
	/**
	 * 当连接成功的时候调用
	 * @param response 连接完成时服务器端返回的http响应
	 */
	void onOpen(DefaultFullHttpResponse response);
	
	/**
	 * 接收到二进制消息时调用
	 * @param bytes
	 */
	void onMessage(byte[] bytes);
	
	/**
	 * 接收到字符串消息时调用
	 * @param msg
	 */
	void onMessage(String msg);
	
	/**
	 * 当连接关闭的时候调用
	 */
	void onClose();
	
	/**
	 * 当发生错误的时候调用的方法
	 * @param e 抛出的异常
	 */
	void onError(Exception e);
}
