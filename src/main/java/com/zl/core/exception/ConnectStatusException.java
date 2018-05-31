package com.zl.core.exception;

/**
 * 写入超时异常
 * @author zl
 *
 */
public class ConnectStatusException extends Exception{

	public ConnectStatusException() {
		super("connect status exception");
	}
}
