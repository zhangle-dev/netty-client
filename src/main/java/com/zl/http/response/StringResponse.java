package com.zl.http.response;

/**
 * �����ַ���������Ӧʵ����,��ʹ��IHttpClient�����ַ�������ʱ���أ���getString
 * @author zl
 *
 */
public class StringResponse {

	//����״̬��
	public Integer status;
	//����
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
