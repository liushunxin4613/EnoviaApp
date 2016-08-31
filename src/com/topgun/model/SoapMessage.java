package com.topgun.model;

import org.ksoap2.serialization.SoapPrimitive;

import com.topgun.entity.User;

public class SoapMessage {
	
	private String ins0;
	private String ins1;
	private String ins2;
	private String ins3;
	private String ins4;
	private Integer ini3;
	private Integer ini4;
	private SoapPrimitive insb5;
	
	public String getIns0() {
		return ins0;
	}
	public void setIns0(String ins0) {
		this.ins0 = ins0;
	}
	public String getIns1() {
		return ins1;
	}
	public void setIns1(String ins1) {
		this.ins1 = ins1;
	}
	public String getIns2() {
		return ins2;
	}
	public void setIns2(String ins2) {
		this.ins2 = ins2;
	}
	public String getIns3() {
		return ins3;
	}
	public void setIns3(String ins3) {
		this.ins3 = ins3;
	}
	public String getIns4() {
		return ins4;
	}
	public void setIns4(String ins4) {
		this.ins4 = ins4;
	}
	public Integer getIni3() {
		return ini3;
	}
	public void setIni3(Integer ini3) {
		this.ini3 = ini3;
	}
	public Integer getIni4() {
		return ini4;
	}
	public void setIni4(Integer ini4) {
		this.ini4 = ini4;
	}
	
	public SoapPrimitive getInsb5() {
		return insb5;
	}
	public void setInsb5(SoapPrimitive insb5) {
		this.insb5 = insb5;
	}
	public SoapMessage() {
		
	}
	
	public SoapMessage(User user) {
		this.ins0 = user.getUsername();
		this.ins1 = user.getPwd();
	}
	
	public SoapMessage(User user, String ins2, Integer ini3, Integer ini4) {
		this.ins0 = user.getUsername();
		this.ins1 = user.getPwd();
		this.ins2 = ins2;
		this.ini3 = ini3;
		this.ini4 = ini4;
	}

	public SoapMessage(User user, String ins2, String ins3,
			String ins4, SoapPrimitive insb5) {
		this.ins0 = user.getUsername();
		this.ins1 = user.getPwd();
		this.ins2 = ins2;
		this.ins3 = ins3;
		this.ins4 = ins4;
		this.insb5 = insb5;
	}
	public SoapMessage(User user, String ins2) {
		super();
		this.ins0 = user.getUsername();
		this.ins1 = user.getPwd();
		this.ins2 = ins2;
	}
	public SoapMessage(User user, String ins2, String ins3,
			String ins4) {
		this.ins0 = user.getUsername();
		this.ins1 = user.getPwd();
		this.ins2 = ins2;
		this.ins3 = ins3;
		this.ins4 = ins4;
	}
	public SoapMessage(User user, String ins2, String ins3) {
		this.ins0 = user.getUsername();
		this.ins1 = user.getPwd();
		this.ins2 = ins2;
		this.ins3 = ins3;
	}
	
	
	
}
