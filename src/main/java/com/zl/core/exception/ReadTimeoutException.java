package com.zl.core.exception;

/**
 * 写入超时异常
 * @author zl
 *
 */
public class ReadTimeoutException extends Exception{

	public ReadTimeoutException() {
		super("read timeout");
	}
}
