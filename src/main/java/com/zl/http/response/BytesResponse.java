package com.zl.http.response;

/**
 * �����ַ���������Ӧʵ����,��ʹ��IHttpClient�����ַ�������ʱ���أ���getBytes
 * @author zl
 *
 */
public class BytesResponse {
	
	//״̬��
	private Integer status;
	//����
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
