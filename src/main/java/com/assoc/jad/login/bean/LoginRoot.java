package com.assoc.jad.login.bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.assoc.jad.login.tools.LoginStatic;
public class LoginRoot implements Serializable {

	private static final long serialVersionUID = 1L;
//	private static final String WEBAPP = "Login";
//	private static HashMap<String,Object> sessions = new HashMap<String,Object>();
//	private static final Log LOG = LogFactory.getLog(LoginRoot.class);
//	
//	private String 	loginid; 
//	private String 	psw; 
//	private String 	newpsw; 
//	private String 	confpsw; 
//	private String 	message = "To use the '"+WEBAPP+" admin menu' you must login";
//	private String	parentPage;
//	private SignalInterface signalImplementor = new SignalImplementor();
//	private UserDB userDB = new UserDB();
//	private boolean loggedin = false;
//	private boolean userHasPhoto = false;
//	private String firstname;
//	private String lastname;
//	private HttpServletRequest request;
//	private String infomsg;
//		
//	@Inject
//	private UserBean userBean;
//
//	@PostConstruct
//	private void init() {
//		if (userBean == null) System.out.println("JADTEST nulls......");
//		else System.out.println("JADTEST it has a value/......");
//	}
//
//	private Object getObject(String objName) {
//		if (request == null) {
//			LOG.warn("HttpServletRequest value is NULL....");
//			return null;
//		}
//		Object object = request.getSession().getAttribute(objName);
//		return object;
//	}
//	@SuppressWarnings("unchecked")
//	private String bldMessage(User user) {
//		if (this.request == null) {
//			LOG.warn("httpservletrequest request is null...");
//			return "";
//		}
//		String contextPath = request.getContextPath();
//		int ndx = request.getRequestURL().indexOf(contextPath);
//		String sessionId = request.getSession().getId();
////		String url = request.getRequestURL().substring(0,ndx)+contextPath+"/confirmEmail.xhtml?sessionId="+sessionId;
//		JSONObject wrkjson = new JSONObject();
//		wrkjson.put("sessionid", sessionId);
//		String wrkstr="";
//		try {
//			wrkstr =  URLEncoder.encode(wrkjson.toJSONString(), "UTF-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
//		String url = request.getRequestURL().substring(0,ndx)+contextPath+"/confirmEmail.xhtml?jsonitem="+ wrkstr;
//		LoginRoot.sessions.put(sessionId,user);
//
//		StringBuilder sb = new StringBuilder();
//		sb.append("<br/>Please click the url below to confirm your email.thanks.<br/>"); 
//		sb.append("<br/><a href="+url+">click here</a><br/>"); 
//		return sb.toString();
//	}
//	private String bldAndSendEmail(User user) {
//		String email = user.getEmail();
//		if (email == null || email.length() == 0) return "need an email";
//		
//        String text= bldMessage(user);
//        String[] contactEmails={email};
//        String subject = "Email confirmation"; 
//        SendEmail sendEmail = new SendEmail(contactEmails, text, subject);
//        new Thread(sendEmail,email).start();
//
//        return "Please click 'confirm' on the email that was sent to "+loginid;
//	}
//	private void maxFailed(User user) {
//		int max = 3;
//		Date date = new Date();
//		long maxDisabledtime = 24*60*60*1000;
//		if (user == null) return;
//		
//		int ctr = user.getFailedcount();
//		user.setFailedcount(++ctr);
//		if (ctr > max) {
//			if (date.getTime() - user.getFailedtime() > maxDisabledtime) { //reset id to allow login after 24 hours
//				user.setFailedcount(0);
//				user.setFailedtime(0);
//				userDB.updateUser(user);
//			}
//		} else {
//			if (user.getFailedtime() == 0) user.setFailedtime(date.getTime());
//			user.setFailedcount(ctr);
//			userDB.updateUser(user);
//			message += userDB.getInfomsg();
//		}
//	}
//	private boolean userExistInDB() {
//		message = null;
//		User user = userDB.getUserviaEmail();
//		
//		if (user.getEmail() == null) return false;
//		
//		userBean.setUser(user);
//		return true;
//	}
//	public void login() {
//		loggedin = false;
//		if (!userExistInDB()) return;
//		
//		User user = userBean.getUser();
//		
//		if (user == null || !user.getPassword().equals(LoginStatic.hashPasscode(psw.trim()))) {
//			message = "invalid user id or password";
//			this.maxFailed(user);
//		} else {
//			message = "login successful";
//			loggedin = true;
//			user.setFailedcount(0);	//reset values in case there was an invalid login request.
//			user.setFailedtime(0);
//			userDB.updateUser(user);
//		}
//	}
//	public void register(User user) {
//		loggedin = false;
//		user.setFailedcount(0);	//reset values in case there was an invalid login request.
//		user.setFailedtime(0);
//		user.setAccess(UserBean.SYSADMIN);
//		userDB.insertUser(user);
//		infomsg = userDB.getInfomsg();
//		if (!userDB.isExecutionFailed()) loggedin = true;
//	}
//	public boolean isRegistered(String loginid) {
//
//		this.loginid = loginid;
//		User user = userDB.getUserviaUserid(loginid);
//		infomsg = userDB.getInfomsg();
//		if (user != null && loginid.equalsIgnoreCase(user.getUserid())) return true;
//		
//		return false;
//	}
//	public void verifyUserEmail(User user) {
//		message = bldAndSendEmail(user);
////		SignalReceiver signalReceiver = new SignalReceiver(signalImplementor);
////	    new Thread(signalReceiver,"signalReceiver").start();
//		//signalReceiver.run();
//
//	}
//
//	public void psw(ValueChangeEvent ev)
//			throws AbortProcessingException {
//		psw = (String)ev.getNewValue();
//		
//	}
//	public String loginAction() {
//		this.parentPage = "index.xhtml";
//		ExternalContext external = FacesContext.getCurrentInstance().getExternalContext();
//		HttpServletRequest request = (HttpServletRequest) external.getRequest();
//		if (userDB.getUser() != null) {
//			return invokePostProcessor(request);
//		}
//
//		String navigate = "index.xhtml";
//		if (this.isLoggedin()) navigate = "loggedin.xhtml";
//		return navigate;
//	}
//	private String invokePostProcessor(HttpServletRequest request) {
//		String navigator = "success";
//		BeanInterface parentInterfaceBean = (BeanInterface) request.getSession().getAttribute("parentInterfaceBean");
//		if (parentInterfaceBean != null) {
//			String parentNavigator = parentInterfaceBean.postProcessor();
//			if (parentNavigator != null && parentNavigator.length()>0) parentPage = parentNavigator;
//		}
//		return navigator;
//	}
//
//	private void invokePreProcessor() {
//		BeanInterface parentInterfaceBean = (BeanInterface)getObject("parentInterfaceBean");
//		if (parentInterfaceBean != null) {
//			String parentInfomsg = parentInterfaceBean.preProcessor();
//			message += System.getProperty("line.separator") +parentInfomsg;
//		}
//	}
//	public User addUser(User user) {
//		if (user == null) return new User();
//
//		userDB.setUser(user);
//		userDB.insertUser();
//		infomsg = userDB.getInfomsg();
//	    new Thread(new SignalSender(signalImplementor),"signalSender").start(); //wake up receiver
//	    return userDB.getUser();
//	}
//	public LoginPhotos registerAddPhotoToDB(UploadedFile uploadedFile,byte[] data,User user) {
//		LoginPhotosDB LoginPhotosDB = new LoginPhotosDB();
//		LoginPhotos loginPhoto = LoginPhotosDB.updatePhoto(uploadedFile,user.getEmail(),data);
//		if (loginPhoto  == null) {
//			message += LoginPhotosDB.getInfomsg();
//		} else {
//			message += "Successfully added photo for newly registered user='"+user.getEmail()+"'";
//		}
//		return loginPhoto;
//	}
//	private boolean fndPhoto() {
//		if (userHasPhoto) return true;
//		
//		LoginPhotosDB  loginPhotosDB = new LoginPhotosDB();
//		List<Object> wrkObjs =loginPhotosDB.getPhoto(userDB.getUser().getId());
//		if (wrkObjs == null || wrkObjs.size() == 0) return false;
//		
//		this.userBean.setLoginPhoto((LoginPhotos)wrkObjs.get(0));
//		userHasPhoto = true;
//		return true;
//	}
//
//	public User retrieveUser(String sessionid) {
//		User user = (User)LoginRoot.sessions.get(sessionid);
//		LoginRoot.sessions.remove(sessionid);
//		return user;
//	}
//	/*
//	 * getter and setters 
//	 */
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
//		invokePreProcessor();
//		String output = message;
//		message = "";
//		return output;
//	}
//	public String getParentPage() {
//		String temp = parentPage;
//		parentPage = "index.xhtml";
//		return temp;
//	}
//	public void setParentPage(String parentPage) {
//		this.parentPage = parentPage;
//	}
//	public static Object getSessionId(String sessionId) {
//		return sessions.get(sessionId);
//	}
//	public UserDB getUserDB() {
//		return userDB;
//	}
//	public boolean isLoggedin() {
//		return loggedin;
//	}
//	public void setLoggedin(boolean loggedin) {
//		this.loggedin = loggedin;
//	}
//	public String getFirstname() {
//		return firstname;
//	}
//	public void setFirstname(String firstname) {
//		this.firstname = firstname;
//	}
//	public String getLastname() {
//		return lastname;
//	}
//	public void setLastname(String lastname) {
//		this.lastname = lastname;
//	}
//	public boolean hasPhoto() {
//		return fndPhoto();
//	}
//	public boolean isUserHasPhoto() {
//		return userHasPhoto;
//	}
//	public void setUserHasPhoto(boolean userHasPhoto) {
//		this.userHasPhoto = userHasPhoto;
//	}
//	public HttpServletRequest getRequest() {
//		return request;
//	}
//	public void setRequest(HttpServletRequest request) {
//		this.request = request;
//	}
//
//	public String getInfomsg() {
//		return infomsg;
//	}
//
//	public void setInfomsg(String infomsg) {
//		this.infomsg = infomsg;
//	}
}
