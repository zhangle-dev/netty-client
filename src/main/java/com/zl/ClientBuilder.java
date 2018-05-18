package com.zl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.netty.channel.ChannelHandler;

/**
 * ���ڴ���Client����
 * @author zl
 *
 * @param <REQ> ���������
 * @param <RSP> ��Ӧ������
 */
public class ClientBuilder<REQ,RSP> {

	private String host;
	private Integer port;
	private List<ChannelHandler> list = new ArrayList<ChannelHandler>();

	public ClientBuilder() {
	}
	
	/**
	 * 
	 * @param host ����˵�ַ
	 * @param port ����˶˿ں�
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
	
	public ClientBuilder<REQ,RSP> addHandler(ChannelHandler handler) {
		list.add(handler);
		return this;
	}
	
	public ClientBuilder<REQ,RSP> addHandler(Collection<ChannelHandler> handlers) {
		list.addAll(handlers);
		return this;
	}
	
	public IClient<REQ,RSP> builder() throws Exception{
		if(host == null || port == null || host.isEmpty() || port == 0) {
			throw new RuntimeException("�����������벻��Ϊ��");
		}
		return new Client<REQ,RSP>(host, port, list);
	}
}
