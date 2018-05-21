package com.zl.http.response;

/**
 * 返回字符串内容响应实体类,在使用IHttpClient请求字符串内容时返回，如getBytes
 * @author zl
 *
 */
public class BytesResponse {
	
	//状态码
	private Integer status;
	//内容
	private byte[] bytes;
	
	public BytesResponse() {
		super();
	}
	
	public BytesResponse(Integer status, byte[] bytes) {
		super();
		this.status = status;
		this.bytes = bytes;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	
	
	
}
