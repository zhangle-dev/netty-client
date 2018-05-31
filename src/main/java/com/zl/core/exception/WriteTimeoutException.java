package com.zl.core.exception;

/**
 * 写入超时异常
 * @author zl
 *
 */
public class WriteTimeoutException extends Exception{

	public WriteTimeoutException() {
		super("write timeout");
	}
}
