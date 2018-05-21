package com.zl.http.response;

/**
 * 返回字符串内容响应实体类,在使用IHttpClient请求字符串内容时返回，如getString
 * @author zl
 *
 */
public class StringResponse {

	//返回状态码
	public Integer status;
	//内容
	public String content;
		
	public StringResponse() {
		super();
	}
	
	public StringResponse(Integer status, String content) {
		super();
		this.status = status;
		this.content = content;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
