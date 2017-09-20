package com.test.user;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
	@JsonProperty("email") 
	private String email;
	@JsonProperty("passwd") 
	private String passwd;
	@JsonProperty("name") 
	private String name;
	@JsonProperty("mobile") 
	private String mobile;
	private Date regdate; 

	public User() {}
	
	public User(String email, String passwd) {
		this.email = email;
		this.passwd = passwd;
	}
	
	public User(String email, String passwd, String name, String mobile, Date regdate) {
		this.email = email;
		this.passwd = passwd;
		this.name = name;
		this.mobile = mobile;
		this.regdate = regdate;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
}
