package com.topgun.entity;

/**
 * ”√ªß±Ì
 * 
 * @author liusx
 *
 */
public class User {
	
	private String username;
	private String name;
	private String pwd;
	private String id;
	private String type;
	private String condition;
	private String objectId;
	private String swhere;
	private String email;
	private int startIndex;
	private int pageSize;
	private String issueId;
	
	
	public String getIssueId() {
		return issueId;
	}
	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSwhere() {
		return swhere;
	}
	public void setSwhere(String swhere) {
		this.swhere = swhere;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public User() {
		super();
	}
	public User(String username, String pwd) {
		super();
		this.username = username;
		this.pwd = pwd;
	}
	public User(String username, String pwd, String id) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.id = id;
	}
	public User(String username,String pwd,String id, String name,
			String email) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.id = id;
		this.name = name;
		this.email = email;
	}
	public User(String username, String pwd, String type, int startIndex,
			int pageSize) {
		super();
		this.username = username;
		this.pwd = pwd;
		this.type = type;
		this.startIndex = startIndex;
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", pwd=" + pwd
				+ ", id=" + id + ", email=" + email + "]";
	}
	
	
}
