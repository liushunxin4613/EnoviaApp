package com.topgun.model;

/**
 * ����web service���ص�json��ʽ����
 * 
 * @author liusx
 *
 */
public class Json {
	
	/**
	 * ������Ϣ
	 */
	private String response;
	
	/**
	 * ��������
	 */
	private String data;
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Json(String response, String data) {
		super();
		this.response = response;
		this.data = data;
	}
	public Json() {
		super();
	}
	
	
}
