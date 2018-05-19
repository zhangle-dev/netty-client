package com.zl;

import java.util.List;

/**
 * 用于测试tcp连接的接口
 * @author zl
 *
 */
public interface IClient<REQ,RSP> {

	/**
	 * 发送一个packet包并返回相应
	 * @param packet 请求包
	 * @return
	 * @throws Exception
	 */
	RSP execute(REQ packet) throws Exception;

	/**
	 * 发送多个包，并返回packet列表
	 * @param list 请求包列表
	 * @return
	 * @throws Exception
	 */
	List<RSP> execute(REQ... list) throws Exception;

	/**
	 * 发送一个包完成后执行回调方法
	 * @param packet 请求包
	 * @param callback 回掉方法
	 * @throws Exception
	 */
	void execute(REQ packet,ClientCallback<RSP> callback) throws Exception;

	/**
	 * 发送多个包,每次完成后执行回调方法
	 * @param callback
	 * @param list
	 * @throws Exception
	 */
	void execute(ClientCallback<RSP> callback,REQ... list) throws Exception;

	/**
	 * 关闭当前连接
	 */
	void close();
}
