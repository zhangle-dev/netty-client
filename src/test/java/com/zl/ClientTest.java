package com.zl;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;

public class ClientTest {

	private static String host = "www.baidu.com";
	private static Integer port = 80;
	
	public static void main(String[] args) throws Exception {
		
		//创建客户端
		IClient<HttpRequest, HttpResponse> client = new ClientBuilder<HttpRequest, HttpResponse>(host,port)
			.addHandler(new HttpRequestEncoder())
			.addHandler(new HttpResponseDecoder())
			.builder();
		
		//创建httprequest并发送
		DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/");
		HttpResponse response = client.execute(request);
		
		System.out.println(response);
		client.close();
	}

}
