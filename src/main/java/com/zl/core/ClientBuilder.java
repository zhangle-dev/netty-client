package com.zl.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import io.netty.channel.ChannelHandler;

/**
 * 用于创建Client对象
 * @author zl
 *
 * @param <REQ> 请求包类型
 * @param <RSP> 响应包类型
 */
public class ClientBuilder<REQ,RSP> {

	private String host;
	private Integer port;

	//读取超时时间
	private Integer readTimeoutSeconds = 5;
	//发送超时时间
	private Integer writeTimeoutSeconds = 5;

	private List<ChannelHandler> list = new ArrayList();

	private RSP idleRSP;

	public ClientBuilder() {
	}

	/**
	 *
	 * @param host 服务端地址
	 * @param port 服务端端口号
	 */
	public ClientBuilder(String host, Integer port) {
		super();
		this.host = host;
		this.port = port;
	}

	public ClientBuilder<REQ,RSP> setHost(String host) {
		this.host = host;
		return this;
	}

	public ClientBuilder<REQ,RSP> setPort(Integer port) {
		this.port = port;
		return this;
	}

	public void setReadTimeoutSeconds(Integer readTimeoutSeconds) {
		this.readTimeoutSeconds = readTimeoutSeconds;
	}

	public void setWriteTimeoutSeconds(Integer writeTimeoutSeconds) {
		this.writeTimeoutSeconds = writeTimeoutSeconds;
	}

	public ClientBuilder<REQ,RSP> addHandler(ChannelHandler handler) {
		list.add(handler);
		return this;
	}

	public ClientBuilder<REQ,RSP> addHandler(Collection<ChannelHandler> handlers) {
		list.addAll(handlers);
		return this;
	}

	public ClientBuilder<REQ,RSP> addHandler(ChannelHandler... handlers) {
		list.addAll(Arrays.asList(handlers));
		return this;
	}

	public IClient<REQ,RSP> builder() throws Exception{
		if(host == null || port == null || host.isEmpty() || port == 0) {
			throw new RuntimeException("主机名或密码不能为空");
		}
		return new Client<REQ,RSP>(host, port, list,readTimeoutSeconds,writeTimeoutSeconds,idleRSP);
	}

	public void setIdleRSP(RSP idleRSP) {
		this.idleRSP = idleRSP;
	}
}
