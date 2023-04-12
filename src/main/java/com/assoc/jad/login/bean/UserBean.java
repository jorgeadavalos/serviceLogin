package com.assoc.jad.login.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
//	
//	public final static String SYSADMIN  = "SYSADMIN"; //modify anything in the homenet DB
//	public final static String USERADMIN = "USERADMIN"; //modify only its COOP in DB
//	public final static String SIMPLEUSER  = "SIMPLEUSER"; //views only??? not modify
//	
//	private User user;
//	private User loggedUser;
//	private String infoMsg="";
//	private String[] types = {SYSADMIN,USERADMIN,SIMPLEUSER};
//	private List<SelectItem> typeSelectItems;
//	private String navigator;
//	private LoginPhotos loginPhoto;
//	
//	private boolean isLoggedInWithAccess() {
//		LoginBean loginBean = null;
//		setInfoMsg("");
//		FacesContext context = FacesContext.getCurrentInstance();
//		if (context != null) {
//			ExternalContext external = context.getExternalContext();
//			HttpServletRequest request = (HttpServletRequest) external.getRequest();
//			loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
//			if (loginBean == null) return false;
//			loggedUser = loginBean.isUserLoggedIn();
//		}
////		user = loggedUser;
//		if (loggedUser == null) return false;
//		
//		if (!loggedUser.getAccess().equals(UserBean.SYSADMIN)) {
////			admin = loggedUser;
//		}
//		if (loggedUser.getAccess().equals(UserBean.SYSADMIN)||loggedUser.getAccess().equals(UserBean.USERADMIN)) {
//			//level of access if correct
//		} else {
//			setInfoMsg("you do not have sufficient authority to update a homenet");
//			return false;
//		}
//		return true;
//	}
//	/*
//	 * type selection is based on an array positional priority. the first entry is the highest.
//	 * whoever is logged on can't create an user id with higher priority than him/herself.
//	 */
//	public List<SelectItem> getTypeSelectItems() {
//		typeSelectItems = new ArrayList<SelectItem>();
//		if (!isLoggedInWithAccess()) return typeSelectItems;
//		
//		boolean typeFound = false;
//		for (int i=0;i<types.length;i++) {
//			if (!loggedUser.getAccess().equals(types[i]) && !typeFound) continue;
//			
//			typeFound = true;
//			SelectItem item = new SelectItem();
//			item.setLabel(types[i].toLowerCase());
//			item.setValue(types[i]);
//			typeSelectItems.add(item);
//		}
//		return typeSelectItems;
//	}
//	public void addUser(ActionEvent event) {
//		if (!isLoggedInWithAccess()) return;
//		
//		user.setUserid(user.getUserid().toUpperCase());
//		DataBaseAccess dbacc = new DataBaseAccess(user.getClass(),user);
//		String sql   = "select * from users where loginid='"+user.getUserid()+"'";
//		List<Object> delList = dbacc.readSql(sql);
//		// it should be zero.
//		if (delList != null && delList.size() > 0) {
//			setInfoMsg("User "+user.getUserid()+" already exist");
//			return;
//		}
//
//		user.setCreatedate(System.currentTimeMillis());
//		String savePsw = user.getPassword();
//		user.setPassword(LoginStatic.hashPasscode(savePsw));
//		sql = "select * from users where loginid='"+user.getUserid()+"'";
//		if (dbacc.insertSql( sql,"users")) {
//			setInfoMsg("success added: "+user.getUserid());
//		} else {
//			setInfoMsg("error adding: "+user.getUserid());
//			
//		}
//	}
//	public void valueChangeUseridSelected(String userId) {
//		
//		DataBaseAccess dbacc = new DataBaseAccess(user.getClass(),user);
//		String sql   = "select * from users where loginid='"+userId+"'";
//		List<Object> wrkList = dbacc.readSql(sql);
//		if (wrkList == null || wrkList.size() != 1) return;
//		
//			user = (User)wrkList.get(0);
//	}
//	public User getLoggedUser() {
//		return loggedUser;
//	}
//	public void setLoggedUser(User loggedUser) {
//		this.loggedUser = loggedUser;
//	}
//	public String getInfoMsg() {
//		return infoMsg;
//	}
//	public void setInfoMsg(String infoMsg) {
//		this.infoMsg = infoMsg;
//	}
//	public void setAddedOk(String infoMsg) {
//		setInfoMsg( infoMsg);
//	}
//	public void setNavigator(String navigator) {
//		this.navigator = navigator;
//	}
//	public String getNavigator() {
//		return this.navigator;
//	}
//	public LoginPhotos getLoginPhoto() {
//		return loginPhoto;
//	}
//	public void setLoginPhoto(LoginPhotos loginPhoto) {
//		this.loginPhoto = loginPhoto;
//	}
//	public User getUser() {
////		if (!isLoggedInWithAccess()) return null;
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
//	public void deleteUser(ActionEvent event) {
//		if (!isLoggedInWithAccess()) return;
//		if (user.getUserid().equals(loggedUser.getUserid())) {
//			setInfoMsg(user.getUserid()+" you can not delete yourself.");
//			return;
//		}
//
//		DataBaseAccess dbacc = new DataBaseAccess(null,null);
//		String sql = "delete from users where loginid='"+user.getUserid()+"'";
//		if (!dbacc.deleteSql( sql)) {
//			setInfoMsg(user.getUserid()+" FAILED to delete:");
//			return;
//		}
//		setInfoMsg(user.getUserid()+" deleted successfully:");
//	}
//	public void updateUser(ActionEvent event) {
//		if (!isLoggedInWithAccess()) return;
//
//		DataBaseAccess dbacc = new DataBaseAccess(user.getClass(),user);
//		String sql = "select * from users where loginid='"+user.getUserid()+"'";
//		if (!dbacc.updateSql( sql,"users")) {
//			setInfoMsg(getInfoMsg()+"\nfailed to update users table loginid="+user.getUserid());
//			return;
//		}
//		setInfoMsg(user.getUserid()+" updated successfully:");
//	}
}
