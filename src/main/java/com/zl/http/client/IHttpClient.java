package com.zl.http.client;

import java.util.concurrent.TimeoutException;

import com.zl.http.response.BytesResponse;
import com.zl.http.response.StringResponse;

import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpMethod;

/**
 * 用于发送http请求的接口
 */
public interface IHttpClient {

	/**
	 * 发送请求
	 * @param request 请求包
	 * @param host 请求服务器主机名
	 * @param port 请求服务器端口号
	 * @return 响应信息
	 * @throws TimeoutException 
	 */
	DefaultFullHttpResponse request(DefaultFullHttpRequest request,String host,Integer port) throws TimeoutException;
	
	/**
	 * 以get的方式发送请求
	 * @param url 请求路径
	 * @return 响应信息
	 */
	DefaultFullHttpResponse get(String url);
	
	/**
	 * 以get方式发送请求并返回字符串内容
	 * @param url 请求路径
	 * @return 响应状态码和字符串格式的内容
	 */
	StringResponse getStringResult(String url);
	
	/**
	 * 以get方式发送请求并返回byte[]内容
	 * @param url 请求路径
	 * @return 响应状态码和byte[]内容
	 */
	BytesResponse getBytesResult(String url);
	
	/**
	 * 以post方式发送请求
	 * @param url 请求路径
	 * @param content 字符串请求体
	 * @return 响应信息
	 */
	DefaultFullHttpResponse post(String url,String content);
	
	/**
	 * 以post方式发送请求
	 * @param url 请求路径
	 * @param contentBytes 请求体byte[]
	 * @return 响应信息
	 */
	DefaultFullHttpResponse post(String url,byte[] contentBytes);
	
	/**
	 * 以post方式发送请求返回字符串类型响应
	 * @param url 请求路径
	 * @param content 字符串请求体
	 * @return 响应状态码和字符串格式的内容
	 */
	StringResponse postStringResult(String url,String content);
	
	/**
	 * 以post方式发送请求返回字符串类型响应
	 * @param url 请求路径
	 * @param contentBytes 二进制请求体
	 * @return 响应状态码和字符串格式的内容
	 */
	StringResponse postStringResult(String url,byte[] contentBytes);
	
	/**
	 * 以post方式发送请求返回二进制响应
	 * @param url 请求路径
	 * @param content 字符串请求体
	 * @return 响应状态码和二进制内容
	 */
	BytesResponse postBytesResult(String url,String content);
	
	/**
	 * 以post方式发送请求返回二进制响应
	 * @param url 请求路径
	 * @param contentBytes 二进制请求体
	 * @return 响应状态码和二进制内容
	 */
	BytesResponse postBytesResult(String url,byte[] contentBytes);
	
	
	/**
	 * 自定义请求方式去请求指定url并返回字符串响应
	 * @param url 请求路径
	 * @param httpMethod 请求方法
	 * @param content 请求体
	 * @return 字符串响应
	 */
	StringResponse customStringResult(String url,HttpMethod httpMethod,String content);
	
	/**
	 * 自定义请求方式去请求指定url并返回字符串响应
	 * @param url 请求路径
	 * @param httpMethod 请求方法
	 * @param contentBytes 请求体
	 * @return 字符串响应
	 */
	StringResponse customStringResult(String url,HttpMethod httpMethod,byte[] contentBytes);
	
	/**
	 * 自定义请求方式去请求指定url并返回二进制响应
	 * @param url 请求路径
	 * @param httpMethod 请求方法
	 * @param content 请求体
	 * @return 二进制响应
	 */
	BytesResponse customBytesResult(String url,HttpMethod httpMethod,String content);
	
	/**
	 * 自定义请求方式去请求指定url并返回二进制响应
	 * @param url 请求路径
	 * @param httpMethod 请求方法
	 * @param contentBytes 请求体
	 * @return 二进制响应
	 */
	BytesResponse customBytesResult(String url,HttpMethod httpMethod,byte[] contentBytes);
	
}
