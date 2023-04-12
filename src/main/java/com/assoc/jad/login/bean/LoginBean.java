package com.assoc.jad.login.bean;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.assoc.jad.login.model.Users;
import com.assoc.jad.login.tools.LoginStatic;

public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
//	public static String returnHTML;
//
//	private String loginid; 
//	private String psw; 
//	private String newpsw; 
//	private String confpsw; 
//	private Users user = new Users();
//	private String message;
//	private boolean loggedin;
//	private String contextPath;
//
//	@Autowired
//	private UserBean userBean;
//
//	public void login(JSONObject json) {
//		
//		getInputParms();
//		verifyLogin(loginid,LoginStatic.hashPasscode(psw.trim()));
//		if (loggedin) LoginStatic.redirect(returnHTML);
//	}
////	public void register(ActionEvent event) {
////		
////		getInputParms();
////		user.setCreatedate(System.currentTimeMillis());
////		user.setEmail(loginid);
////		user.setPassword(LoginStatic.hashPasscode(psw.trim()));
////		user.setAccess("TODO");
////		user.setUserid(loginid);
////		user.setFirstname("");
////		user.setLastname("");
////		
////		UserDB userDB = new UserDB();
////		userDB.setUser(user);
////		userDB.insertUser();
////		message = userDB.getInfomsg();
////		LoginStatic.redirect(returnHTML);
////	}
//	private void getInputParms() {
//		
//		FacesContext context = FacesContext.getCurrentInstance();
//		ExternalContext external = context.getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) external.getRequest();
//		request.getSession().setAttribute("loginBean",this);
//		
//		String form = LoginStatic.findFormName(request);
//		loginid = request.getParameter(form+"loginid");
//		psw = request.getParameter(form+"psw");
//	}
//	private void verifyLogin(String loginid, String psw) {
//		String sql   = "select * from users where loginid='"+loginid+"'";
//		List<Object> list  = dbacc.readSql(sql);
//		message = "invalid user id or password";
//		loggedin = false;
//		if (list == null || list.size() == 0) {
//			loginid = null;
//			psw = null;
//			return;
//		}
//		user = (User)list.get(0);
//		if (!user.getPassword().equals(psw.trim())) {
//			loginid = null;
//			psw = null;
//		} else {
//			loggedin = true;
//			userBean.setUser(user);
//			message = "login successful";
//		}
//	}
//	public void bldpsw(ValueChangeEvent ev)
//			throws AbortProcessingException {
//		psw = (String)ev.getNewValue();
//		
//	}
//	public String loginAction() {
//		if (!loggedin) return "failure";
//		
//		LoginStatic.redirect(returnHTML);
//		return "";
//	}
//	public User isUserLoggedIn() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		ExternalContext external = context.getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) external.getRequest();
//		LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
//		if (loginBean == null) return null;
//		return user;
//	}
//	/*
//	 * if user logged in it will return it's priority (type) if priority matches the type requested.
//	 * else it the priority is lower than requested it will return ""
//	 */
//	public String getUserLoggedIn(HttpServletRequest request) {
//		LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
//		if (loginBean == null) return "";
//		UserBean userBean = new UserBean();
//		userBean.setLoggedUser(loginBean.user);
////		userBean.setLoggedUser(loggedUser);loggedUser = loginBean.isUserLoggedIn();
//		List<SelectItem> typeSelectItems = userBean.getTypeSelectItems();
//		WebTools coopTools = new WebTools();
//		String type = coopTools.ParseQuery(request.getQueryString(), "TYPE");
//		if (type.length() == 0) type = userBean.getLoggedUser().getAccess();
//		for (int i=0;i<typeSelectItems.size();i++) {
//			String wrkstr = (String)typeSelectItems.get(i).getValue();
//			if (wrkstr.equals(type)) return wrkstr;
//		}
//		return "";
//	}
//	public String setLogout(HttpServletRequest request) {
//		LoginBean loginBean = (LoginBean) request.getSession().getAttribute("loginBean");
//		loginBean.setUser(null);
//		UserBean userBean = (UserBean) request.getSession().getAttribute("userBean");
//		userBean.setLoggedUser(null);
//		 request.getSession().setAttribute("userBean",null);
//		return "200ok";
//	}
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
//	public String password() {
//		return "";
//	}
//	public void setLoginid(String loginid) {
//		this.loginid = loginid;
//	}
//	public String getLoginid() {
//		return loginid;
//	}
//	public void setPsw(String psw) {
//		this.psw = psw;
//	}
//	public String getPsw() {
//		return psw;
//	}
//	public void setNewpsw(String newpsw) {
//		this.newpsw = newpsw;
//	}
//	public String getNewpsw() {
//		return newpsw;
//	}
//	public void setConfpsw(String confpsw) {
//		this.confpsw = confpsw;
//	}
//	public String getConfpsw() {
//		return confpsw;
//	}
//	public String getMessage() {
//		return message;
//	}
//	public void setMessage(String message) {
//		this.message = message;
//	}
//	public String getAndroidLogin() {
//		FacesContext context = FacesContext.getCurrentInstance();
//		ExternalContext external = context.getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) external.getRequest();
//		String loginid = request.getParameter("id");
//		String psw = request.getParameter("password");
//		verifyLogin(loginid, psw);
//		return "";
//    }
//	public boolean isLoggedin() {
//		return loggedin;
//	}
//	public void setLoggedin(boolean loggedin) {
//		this.loggedin = loggedin;
//	}
//	public String getContextPath() {
//		if (contextPath != null) return contextPath;
//		
//		FacesContext context = FacesContext.getCurrentInstance();
//		ExternalContext external = context.getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) external.getRequest();
//		
//		return request.getContextPath();
//	}
//	public void setContextPath(String contextPath) {
//		this.contextPath = contextPath;
//	}
}
