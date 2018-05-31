package com.zl.http.client;

import java.util.concurrent.TimeoutException;

import com.zl.core.ClientBuilder;
import com.zl.core.exception.ConnectStatusException;
import com.zl.core.exception.ReadTimeoutException;
import com.zl.core.exception.WriteTimeoutException;
import com.zl.core.interface_.IClient;
import com.zl.http.codec.DefaultFullHttpDecoder;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 该类为keepAlive连接方式，在多次访问同一服务器同意端口得时候使用该类可以减少底层TCP连接和断开带来得性能消耗，使用完毕需要调用close方法关闭
 */
public class KeepAliveHttpClient extends SessionHttpClient {

	private IClient<DefaultFullHttpRequest, DefaultFullHttpResponse> client = null;
	private String preHost = null;
	private Integer prePort = null;

	@Override
	public DefaultFullHttpResponse request(DefaultFullHttpRequest request, String host, Integer port) {
		request.headers().add("Host", "127.0.0.1")
				.add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0")
				.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
				.add("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
				.add("Connection", "keep-alive").add("Accept-Encoding", "gzip, deflate")
				.add("Upgrade-Insecure-Requests", "1");

		port = port == -1 ? 80 : port;

		if (preHost == null || prePort == null || !preHost.equals(host) || prePort != port) {
			preHost = host;
			prePort = port;
			try {
				client = new ClientBuilder<DefaultFullHttpRequest, DefaultFullHttpResponse>(host, port)
						.addHandler(new HttpRequestEncoder()).addHandler(new HttpResponseDecoder())
						.addHandler(new DefaultFullHttpDecoder())
						.builder();
			} catch (Exception e) {
				throw new RuntimeException("连接失败", e);
			}
		}
		try {
			return client.execute(request);
		} catch (ReadTimeoutException | WriteTimeoutException e) {
			return new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.REQUEST_TIMEOUT);
		} catch (ConnectStatusException e) {
			throw new RuntimeException("连接失败", e);
		}
	}

	public void close() {
		if (client != null) {
			client.close();
		}
	}
}
