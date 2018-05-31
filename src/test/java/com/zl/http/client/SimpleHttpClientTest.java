package com.zl.http.client;

import org.junit.Test;

import com.zl.http.response.StringResponse;

public class SimpleHttpClientTest {

//	@Test
	public void test() {
		SimpleHttpClient client = new SimpleHttpClient();
		StringResponse result = client.getStringResult("http://www.baidu.com");
		System.out.println(result.getContent());
	}
	
	public static void main(String[] args) {
		SimpleHttpClient client = new SessionHttpClient();
		
		for (int i = 0; i < 5; i++) {
			StringResponse result = client.getStringResult("http://localhost:8080/hello");
			System.out.println(result.getContent());
		}
	}
}
