package com.zl.core.interface_;

import com.zl.core.ClientCallback;
import com.zl.core.exception.ConnectStatusException;
import com.zl.core.exception.ReadTimeoutException;
import com.zl.core.exception.WriteTimeoutException;
import com.zl.interface_.ILifeCycle;

import io.netty.util.concurrent.Future;

/**
 * 用于执行tcp请求的接口
 * @author zl
 *
 */
public interface IClient<REQ,RSP> extends ILifeCycle {

	/**
	 * 发送一个packet包并返回响应
	 * @param packet 请求包
	 * @return 响应包
	 * @throws ConnectStatusException 连接状态错误
	 */
	Future<RSP> request(REQ packet) throws ConnectStatusException;

	/**
	 * 发送一个包完成后执行回调方法
	 * @param packet 请求包
	 * @param callback 回掉方法
	 * @throws ReadTimeoutException 读取超时
	 * @throws WriteTimeoutException 写入超时
	 * @throws ConnectStatusException 连接状态错误
	 */
	void request(REQ packet,ClientCallback<RSP> callback) throws ReadTimeoutException,WriteTimeoutException,ConnectStatusException;

	/**
	 * 
	 * @param packet 请求包
	 * @return 响应包
	 * @throws ReadTimeoutException 读取超时
	 * @throws WriteTimeoutException 写入超时
	 * @throws ConnectStatusException 连接状态错误
	 */
	RSP execute(REQ packet) throws ReadTimeoutException, WriteTimeoutException, ConnectStatusException;

}
