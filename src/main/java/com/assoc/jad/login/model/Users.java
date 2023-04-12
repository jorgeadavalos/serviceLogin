package com.assoc.jad.login.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

/**
 * This class represents a Userss in our application's domain model.
 * 
 * 
 */
@Entity
public class Users  extends RepresentationModel<Users> {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)	private int id;
	private String firstname;
	private String lastname;
	private String loginid;
	private String password;
	private String goodemail;		//values: yes or no => if no then it is pending verification
	private String access;
	private String email;
	private int failedcount;
	private long failedtime;
	private long createdate;
	private String service;

	public Users() {
	}

	public int compareTo(Users o) {
		return this.loginid.compareTo(o.loginid);
	}
	@Override
	public String toString() {
		return loginid;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	public void setCreatedate(long createdate) {
		this.createdate = createdate;
	}
	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}
	public long getCreatedate() {
		return createdate;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setId(Long id) {
		this.id = id.intValue();
	}
	public int getFailedcount() {
		return failedcount;
	}
	public void setFailedcount(int failedcount) {
		this.failedcount = failedcount;
	}
	public void setFailedcount(Integer failedcount) {
		this.failedcount = failedcount;
	}
	public long getFailedtime() {
		return failedtime;
	}
	public void setFailedtime(long failedtime) {
		this.failedtime = failedtime;
	}
	public void setFailedtime(Long failedtime) {
		this.failedtime = failedtime;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getGoodemail() {
		return goodemail;
	}
	public void setGoodemail(String goodemail) {
		this.goodemail = goodemail;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}
}
