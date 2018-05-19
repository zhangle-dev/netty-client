package com.zl.exception;

/**
 * 未与服务端建立连接或连接已关闭
 *
 * @author 张乐
 * @create 2018-05-19 12:47
 **/
public class ClientChannlNotConnection extends RuntimeException {

    public ClientChannlNotConnection() {
    }

    public ClientChannlNotConnection(String message) {
        super(message);
    }
}
