package com.zl.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test1 {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws UnknownHostException, IOException {
//		service();
		client();
	}

	private static void client() throws UnknownHostException, IOException {
		Socket socket = new Socket("192.168.2.3", 80);
		
		String ss = "GET /users/sign_in HTTP/1.1\r\n" + 
				"Host: 192.168.2.3\r\n" + 
				"User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0\r\n" + 
				"Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n" + 
				"Accept-Language: zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2\r\n" + 
				"Accept-Encoding: gzip, deflate\r\n" + 
				"Connection: keep-alive\r\n" + 
				"Upgrade-Insecure-Requests: 1\n\n\n";
		InputStream stream = socket.getInputStream();
		OutputStream stream2 = socket.getOutputStream();
		stream2.write(ss.getBytes());
		stream2.flush();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		for(String s = reader.readLine();s!=null;s = reader.readLine()) {
			System.out.println(s);
		}
	}

	private static void service() throws IOException {
		ServerSocket socket = new ServerSocket(80);
		Socket client = socket.accept();
		
		InputStream stream = client.getInputStream();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		for(String s = reader.readLine();s!=null;s = reader.readLine()) {
			System.out.println(s);
		}
	}
}
