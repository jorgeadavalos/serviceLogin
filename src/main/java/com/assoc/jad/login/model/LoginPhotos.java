package com.assoc.jad.login.model;


public class LoginPhotos {
	private long id;
	private long Usersid;
	private long created;
	private String name;
	private byte[] photo;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public void setCreated(Long created) {
		this.created = created;
	}
	public long getUsersid() {
		return Usersid;
	}
	public void setUsersid(long Usersid) {
		this.Usersid = Usersid;
	}
	public void setUsersid(Long Usersid) {
		this.Usersid = Usersid;
	}
	public void setUsersid(Integer Usersid) {
		this.Usersid = Usersid;
	}
}
