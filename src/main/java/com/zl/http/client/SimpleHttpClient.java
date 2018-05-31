package com.zl.http.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeoutException;

import com.zl.core.ClientBuilder;
import com.zl.core.interface_.IClient;
import com.zl.http.codec.DefaultFullHttpDecoder;
import com.zl.http.response.BytesResponse;
import com.zl.http.response.StringResponse;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

/**
 * 简单的httpclient类，可以提供简单访问http服务端的方法
 * @author zl
 *
 */
public class SimpleHttpClient implements IHttpClient {

	@Override
	public DefaultFullHttpResponse request(DefaultFullHttpRequest request, String host,Integer port) {
		
		request.headers()
			.add("Host","127.0.0.1")
			.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0")
			.add("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
			.add("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
			.add("Accept-Encoding","gzip, deflate")
			.add("Upgrade-Insecure-Requests","1");
		
		port = port == -1 ? 80 : port;
		IClient<DefaultFullHttpRequest, DefaultFullHttpResponse> client = null;
		try {
			client = new ClientBuilder<DefaultFullHttpRequest, DefaultFullHttpResponse>(host, port)
				.addHandler(new HttpRequestEncoder())
				.addHandler(new HttpResponseDecoder())
				.addHandler(new DefaultFullHttpDecoder())
				.setIdleRSP(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.REQUEST_TIMEOUT))
				.builder();
		} catch (Exception e) {
			throw new RuntimeException("连接失败",e);
		}
		try {
			return client.execute(request);
		} catch (TimeoutException e) {
			return new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.REQUEST_TIMEOUT);
		}finally {
			client.close();
		}
	}

	@Override
	public DefaultFullHttpResponse get(String url) {
		URL url2;
		try {
			url2 = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException("url格式不正确："+url,e);
		}
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url2.getPath());
		DefaultFullHttpResponse response = request(request, url2.getHost(), url2.getPort());
		return response;
	}

	@Override
	public StringResponse getStringResult(String url) {
		return customStringResult(url, HttpMethod.GET, new byte[0]);
	}

	@Override
	public BytesResponse getBytesResult(String url) {
		return customBytesResult(url, HttpMethod.GET, new byte[0]);
	}

	@Override
	public DefaultFullHttpResponse post(String url, String content) {
		return post(url,content.getBytes());
	}

	@Override
	public DefaultFullHttpResponse post(String url, byte[] contentBytes) {
		URL url2;
		try {
			url2 = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException("url格式不正确："+url,e);
		}
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST, url2.getPath());
		ByteBuf buf = request.content();
		buf.writeBytes(contentBytes);
		DefaultFullHttpResponse response = request(request, url2.getHost(), url2.getPort());
		return response;
	}

	@Override
	public StringResponse postStringResult(String url, String content) {
		return customStringResult(url, HttpMethod.POST, content);
	}

	@Override
	public StringResponse postStringResult(String url, byte[] contentBytes) {
		return customStringResult(url, HttpMethod.POST, contentBytes);
	}

	@Override
	public BytesResponse postBytesResult(String url, String content) {
		return customBytesResult(url, HttpMethod.POST, content);
	}

	@Override
	public BytesResponse postBytesResult(String url, byte[] contentBytes) {
		return customBytesResult(url, HttpMethod.POST, contentBytes);
	}

	@Override
	public StringResponse customStringResult(String url, HttpMethod httpMethod, String content) {
		BytesResponse result = customBytesResult(url, httpMethod, content.getBytes());
		return new StringResponse(result.getStatus(),new String(result.getBytes()));
	}

	@Override
	public StringResponse customStringResult(String url, HttpMethod httpMethod, byte[] contentBytes) {
		BytesResponse result = customBytesResult(url, httpMethod, contentBytes);
		return new StringResponse(result.getStatus(),new String(result.getBytes()));
	}

	@Override
	public BytesResponse customBytesResult(String url, HttpMethod httpMethod, String content) {
		return customBytesResult(url, httpMethod, content.getBytes());
	}

	@Override
	public BytesResponse customBytesResult(String url, HttpMethod httpMethod, byte[] contentBytes) {
		URL url2;
		try {
			url2 = new URL(url);
		} catch (MalformedURLException e) {
			throw new RuntimeException("url格式不正确："+url,e);
		}
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, httpMethod, url2.getPath());
		
		ByteBuf buf = request.content();
		buf.writeBytes(contentBytes);
		DefaultFullHttpResponse response = request(request, url2.getHost(), url2.getPort());
		
		ByteBuf resp = response.content();
		byte[] bytes = new byte[resp.readableBytes()];
		resp.readBytes(bytes);
		
		return new BytesResponse(response.status().code(), bytes);
	}

	@Override
	public void close() {}

}
