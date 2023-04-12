package com.assoc.jad.login.model;

public class EndUserEnv {
	private long id;
	private String Usersid;
	private String Usersenv;
	private long faileddate;
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsersid() {
		return Usersid;
	}
	public void setUsersid(String Usersid) {
		this.Usersid = Usersid;
	}
	public String getUsersenv() {
		return Usersenv;
	}
	public void setUsersenv(String Usersenv) {
		this.Usersenv = Usersenv;
	}
	public long getFaileddate() {
		return faileddate;
	}
	public void setFaileddate(long faileddate) {
		this.faileddate = faileddate;
	}
	public void setFaileddate(Long faileddate) {
		this.faileddate = faileddate;
	}

}
