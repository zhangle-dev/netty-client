package com.zl.interface_;

import java.io.Closeable;

/**
 * 生命周期，拥有生命周期的类可以实现该接口
 * @author zl
 *
 */
public interface ILifeCycle extends Closeable {

	/**
	 * 启动实例
	 * @return 是否启动成功，如果启动成功返回true，否则返回false
	 */
	boolean start();
	
	/**
	 * 关闭实例
	 */
	void close();

	/**
	 * 获取实例运行状态
	 * @return 运行状态
	 */
	Status status();

	/**
	 * 实例的状态
	 * INIT 初始化状态,
	 * STARTING 正在启动,
	 * RUN 运行中,
	 * CLOSING 正在关闭,
	 * CLOSED 关闭状态
	 * @author zl
	 *
	 */
	enum Status{
		INIT,STARTING,RUN,CLOSING,CLOSED
	}
}
