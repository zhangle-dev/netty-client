package com.zl.http.client;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;

public class SessionHttpClient extends SimpleHttpClient {
	
	private String cookie;

	@Override
	public DefaultFullHttpResponse request(DefaultFullHttpRequest request, String host, Integer port) {
		if(cookie!=null) {
			request.headers().set("Cookie", cookie);			
		}
		DefaultFullHttpResponse response = super.request(request, host, port);
		cookie = response.headers().get("Set-Cookie");
		System.out.println("===========================================");
		System.out.println(cookie);
		System.out.println("===========================================");
		return response;
	}
}
