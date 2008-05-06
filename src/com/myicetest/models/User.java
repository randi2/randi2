package com.myicetest.models;

public class User {

	private String firstname;
	private String surname;
	private String loginname;
	private String password;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void save(){
		this.toString();
	}
	
	@Override
	public String toString(){
		return firstname+" "+surname+"\n"+loginname+" "+password;
	}
}
