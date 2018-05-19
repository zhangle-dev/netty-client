package com.zl.core;

import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * 用于执行tcp请求的接口
 * @author zl
 *
 */
public interface IClient<REQ,RSP> {

	/**
	 * 发送一个packet包并返回响应
	 * @param packet 请求包
	 * @return 响应包
	 * @throws TimeoutException 连接超时时抛出异常
	 */
	RSP execute(REQ packet) throws TimeoutException;

	/**
	 * 发送多个包，并返回packet列表
	 * @param list 请求包列表
	 * @return 响应包
	 * @throws TimeoutException 连接超时时抛出异常
	 */
	List<RSP> execute(REQ... list) throws TimeoutException;

	/**
	 * 发送一个包完成后执行回调方法
	 * @param packet 请求包
	 * @param callback 回掉方法
	 * @throws TimeoutException 连接超时时抛出异常
	 */
	void execute(REQ packet,ClientCallback<RSP> callback) throws TimeoutException;

	/**
	 * 发送多个包,每次完成后执行回调方法
	 * @param callback
	 * @param list
	 * @throws TimeoutException 连接超时时抛出异常
	 */
	void execute(ClientCallback<RSP> callback,REQ... list) throws TimeoutException;

	/**
	 * 关闭当前连接
	 */
	void close();
}
