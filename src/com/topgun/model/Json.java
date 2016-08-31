package com.topgun.model;

/**
 * 接收web service返回的json格式数据
 * 
 * @author liusx
 *
 */
public class Json {
	
	/**
	 * 反馈消息
	 */
	private String response;
	
	/**
	 * 反馈数据
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
