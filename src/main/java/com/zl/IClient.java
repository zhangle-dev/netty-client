package com.zl;

import java.util.List;

/**
 * ���ڲ���tcp���ӵĽӿ�
 * @author zl
 *
 */
public interface IClient<REQ,RSP> {

	/**
	 * ����һ��packet����������Ӧ
	 * @param packet �����
	 * @return
	 * @throws Exception 
	 */
	RSP execute(REQ packet) throws Exception;
	
	/**
	 * ���Ͷ������������packet�б�
	 * @param list ������б�
	 * @return
	 * @throws Exception 
	 */
	List<RSP> execute(REQ... list) throws Exception;
	
	/**
	 * ����һ������ɺ�ִ�лص�����
	 * @param packet �����
	 * @param callback �ص�����
	 * @throws Exception 
	 */
	void execute(REQ packet,ClientCallback<RSP> callback) throws Exception;
	
	/**
	 * ���Ͷ����,ÿ����ɺ�ִ�лص�����
	 * @param callback
	 * @param list
	 * @throws Exception
	 */
	void execute(ClientCallback<RSP> callback,REQ... list) throws Exception;
	
	/**
	 * �رյ�ǰ����
	 */
	void close();
}
