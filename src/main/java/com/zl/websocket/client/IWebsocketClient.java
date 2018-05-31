package com.zl.websocket.client;

import com.zl.websocket.client.process.IProcess;

/**
 * websocket客户端接口
 * @author zl
 *
 */
public interface IWebsocketClient {

	/**
	 * 发送byte数组
	 * @param bytes 要发送的byte数组
	 */
	void send(byte[] bytes);
	
	/**
	 * 发送字符串消息
	 * @param s 要发送的字符串信息
	 */
	void send(String s);
	
	/**
	 * 启动websocket客户端
	 */
	void start();
	
	/**
	 * 关闭websocket客户端
	 */
	void stop();
	
	/**
	 * 设置消息处理器
	 * @param process 消息处理器
	 */
	void setProcess(IProcess process);
}
