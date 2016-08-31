package com.topgun.model;

import org.ksoap2.serialization.SoapPrimitive;

import android.net.Uri;

public class FileInfo {
	private String name;
	private String path;
	private Uri uri;
	private String suffix;
	private SoapPrimitive sb;
	private long length;

	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public SoapPrimitive getSb() {
		return sb;
	}
	public void setSb(SoapPrimitive sb) {
		this.sb = sb;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Uri getUri() {
		return uri;
	}
	public void setUri(Uri uri) {
		this.uri = uri;
	}
	
	public long getLength() {
		return length;
	}
	public void setLength(long length) {
		this.length = length;
	}
	public FileInfo(String name, String path, Uri uri, String suffix,
			SoapPrimitive sb,long length) {
		super();
		this.name = name;
		this.path = path;
		this.uri = uri;
		this.suffix = suffix;
		this.sb = sb;
		this.length = length;
	}
	
	public FileInfo(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}
	public FileInfo() {
		super();
	}
	
	
}
