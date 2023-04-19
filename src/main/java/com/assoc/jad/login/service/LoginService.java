package com.assoc.jad.login.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.assoc.jad.login.controller.ALogin;
import com.assoc.jad.login.dao.UserDao;
import com.assoc.jad.login.jsonmapper.MapJsonToObject;
import com.assoc.jad.login.jsonmapper.MapObjectToJson;
import com.assoc.jad.login.model.Users;
import com.assoc.jad.login.tools.SendEmail;

@Service
public class LoginService  extends ALogin {
	private static final Log LOG = LogFactory.getLog(LoginService.class);
	@Autowired
	UserDao dao;
	@Value("${spring.data.rest.base-path}")
	private String restName;

	@SuppressWarnings("unchecked")
	public JSONObject confirmCredentials(JSONObject json,HttpServletRequest req) {
		JSONObject wrkjson = new JSONObject();
		wrkjson.put("srvcompleted", true);
		jsonCallerInfo(wrkjson,req);
		
		String psw = (String) json.get("password");
		String loginid = (String) json.get("loginid");
		String service = (String) wrkjson.get("service");

		wrkjson.put("infomsg","login succesful...");
		wrkjson.put("loginid",loginid);
		List<Users> users = dao.findByLoginidAndService(loginid,service);
		if (users.size() == 0) {
			wrkjson.put("infomsg","'"+loginid+"' does not exist for service='"+service+"' .You need to register.<br/> Please click 'Register' button below");
			wrkjson.put("srvcompleted", false);
		}
		if (users.size() > 1) {
			wrkjson.put("infomsg","user does not exist or there is more than one user with userId="+loginid);
			wrkjson.put("srvcompleted", false);
		}
		String hashPsw = hashPasscode( psw);
		if (users.size() == 1 && !hashPsw.equals(users.get(0).getPassword())) {
			wrkjson.put("infomsg","invalid password or email");
			wrkjson.put("srvcompleted", false);
		}
		if (users.size() == 1 ) {
			jsonMapper(users.get(0),wrkjson);
			String goodemail = users.get(0).getGoodemail();
			if (!"yes".equalsIgnoreCase(goodemail)) {
				wrkjson.put("srvcompleted", false);
				bldAndSendEmail(req,wrkjson);
			}
		}

		return wrkjson;
	}
	@SuppressWarnings("unchecked")
	public JSONObject addUser(JSONObject json, HttpServletRequest req, HttpServletResponse resp) {

		JSONObject outjson = new JSONObject();
		Users user = new Users();
		initialzeUserFromCaller(user,req,outjson);
		new MapJsonToObject<>(json,user);
		userDefaults(user);
		String psw = (String) json.get("password");
		String hashPsw = hashPasscode( psw);
		json.remove("password");
		json.remove("vpassword");
		
		String loginid = user.getLoginid();
		String service = user.getService();
		resp.setStatus(HttpServletResponse.SC_CONFLICT);
		
		outjson.put("srvcompleted", false);
		if (loginid == null || loginid.length() == 0) {
			outjson.put("infomsg","'"+loginid+"' no value. Enter some info for loginid.<br/>");
			return outjson;
		}
		List<Users> users = dao.findByLoginidAndService(loginid,service);
		if (users != null) user = users.get(0);
		jsonMapper(user,outjson);
		
		if (users.size() == 1 && hashPsw.equals(users.get(0).getPassword())) {
			outjson.put("srvcompleted", true);
			outjson.put("infomsg","'"+loginid+"' exist and the password is correct.<br/>");
			jsonCallerInfo( outjson, req);
			return outjson;
		} else if (users.size() != 0) {
			outjson.put("infomsg","'"+loginid+"' exist. Enter a different loginid.<br/>");
			return outjson;
		}

		resp.setStatus(HttpServletResponse.SC_OK);
		user.setPassword(hashPsw);
		user.setGoodemail("pending confirmation");			//values: yes or no => if no then it is pending verification
		user.setService((String)outjson.get("service"));
		user = dao.save(user);

		return outjson;
	}
	@SuppressWarnings("unchecked")
	public JSONObject bldAndSendEmail(HttpServletRequest req, JSONObject json) {
		JSONArray usersJson = (JSONArray) json.get("users");
		JSONObject userJson = (JSONObject) usersJson.get(0);
		if (userJson == null) {
			json.put("infomsg","There is no user data passed with the json object<br/>");
			return json;
		}
		String email = (String) userJson.get("email");
		if (email == null || email.length() == 0)  {
			json.put("infomsg","json object has 'user' <br/>user object has no email...");
			return json;
		}
	    String text= bldMessage(req,userJson,"/confirmEmail.jsp");
	    String[] contactEmails={email};
	    String subject = "Email confirmation"; 
	    SendEmail sendEmail = new SendEmail(contactEmails, text, subject);
	    new Thread(sendEmail,email).start();
	
		json.put("infomsg","To complete the process you must respond to an email sent to "+email);
	    return json;
	}
	@SuppressWarnings("unchecked")
	public JSONObject confirmEmail(JSONObject json) {
		String wrkstr = (String)json.get("id");
		int id = Integer.valueOf(wrkstr);
		Users user = dao.findById(id);
		if (user == null || user.getGoodemail().equalsIgnoreCase("yes")) {
			json.put("infomsg","User in database doesnot reflect the required state...");
			json.put("srvcompleted", false);
			return json;
		}
		json.put("infomsg","User for"+user.getLoginid()+" updated sucessfully...");
		user.setGoodemail("yes");
		dao.save(user);
		json.put("srvcompleted", true);
		json.put("html",json.get("callbackNext"));
		jsonMapper(user,json);
		return json;
	}
	@SuppressWarnings("unchecked")
	public JSONObject changePassword(JSONObject json,HttpServletRequest req) {
		
		json.put("srvcompleted", false);
		String caller = req.getHeader("caller");	
		JSONObject callerJson = getJson(caller);
		String service = (String) callerJson.get("serviceOrig");
		String loginid = (String) json.get("loginid");
		
		List<Users> users = dao.findByLoginidAndService(loginid,service);
		if (users.size() == 0 || users.size() > 1) {
			json.put("infomsg","'"+loginid+"user does not exist or there is more than one user with userId="+loginid);
			return json;
		}
		Users user = users.get(0);
		String password = (String) json.get("password");
		String hashPsw = hashPasscode( password);
		if (!user.getPassword().equals(hashPsw)) {
			json.put("infomsg","Password doesn't match for user with loginid='"+loginid+"'");
			return json;
		}
		
		MapObjectToJson<Users> mapper = new MapObjectToJson<Users>(user,user.getClass());
		JSONObject wrkJson = mapper.getJson();
		String newpsw = (String) json.get("newpsw");
		wrkJson.remove("password");
		
		hashPsw = hashPasscode( newpsw);
		user.setPassword(hashPsw);
		dao.save(user);
		
		wrkJson.put("srvcompleted", true);
		wrkJson.put("infomsg","Password succesfully changed... ");	
		return wrkJson;
	}
	@SuppressWarnings("unchecked")
	public JSONObject forgotPassword(JSONObject json, HttpServletRequest req) {
		json.put("srvcompleted", false);
		String caller = req.getHeader("caller");	
		JSONObject callerJson = getJson(caller);
		String service = (String) callerJson.get("serviceOrig");
		String loginid = (String) json.get("loginid");
		
		List<Users> users = dao.findByLoginidAndService(loginid,service);
		if (users.size() == 0 || users.size() > 1) {
			json.put("infomsg","'"+loginid+"user does not exist or there is more than one user with userId="+loginid);
			return json;
		}
		Users user = users.get(0);
		String email = user.getEmail();
		MapObjectToJson<Users> mapper = new MapObjectToJson<Users>(user,user.getClass());
		JSONObject wrkJson = mapper.getJson();
		wrkJson.remove("password");
		
	    String text= bldMessage(req,wrkJson,"/resetPassword.jsp");
	    String[] contactEmails={email};
	    String subject = "Email password reset"; 
	    SendEmail sendEmail = new SendEmail(contactEmails, text, subject);
	    new Thread(sendEmail,email).start();
		json.put("infomsg","To reset password you must respond to email sent to "+email);		
		return json;
	}
	@SuppressWarnings("unchecked")
	public JSONObject resetPassword(JSONObject json, HttpServletRequest req) {
		json.put("srvcompleted", false);
		String wrkId = (String)json.get("id");
		int id = Integer.valueOf(wrkId);
		Users user = dao.findById(id);
		if (user == null) {
			json.put("infomsg","no user found....");
			return json;
		}
		String hashPsw = hashPasscode( (String)json.get("password"));
		user.setPassword(hashPsw);
		dao.save(user);
		
		json.put("srvcompleted", true);
		jsonCallerInfo( json, req);
		jsonMapper(user,json);
		json.put("infomsg","Password succesfully changed... ");	
		return json;
	}
	@SuppressWarnings("unchecked")
	public JSONObject findUserid(JSONObject json, HttpServletRequest req) {
		JSONObject wrkjson = new JSONObject();
		String email = (String) json.get("email");
		String service = (String) json.get("service");
		List<Users> users = null;
		if (service == null) 
			users = dao.findByEmail(email);
		else users = dao.findByEmailAndService(email,service);
		
		if (users.size() == 0) {
			wrkjson.put("infomsg","'"+email+"' does not exist.You need to register.<br/> Please click 'Register' button below");
			wrkjson.put("srvcompleted", false);
		} else if (users.size() > 1) {
			wrkjson.put("infomsg","user does not exist or there is more than one user with userId="+email);
			wrkjson.put("srvcompleted", false);
		} else {
			Users user = users.get(0);
			wrkjson.put("loginid", user.getLoginid());
			jsonMapper(user,wrkjson);
			wrkjson.put("srvcompleted", false);
			wrkjson.put("infomsg","Continue processing for userid="+user.getLoginid()+".<br/>Enter 'Passowrd' and click <b>login</b> button");
		}

		return wrkjson;
	}

	@SuppressWarnings("unchecked")
	private String bldMessage(HttpServletRequest req, JSONObject userJson,String page) {
		if (req == null) {
			LOG.warn("httpservletrequest request is null...");
			(new Exception()).printStackTrace();
			return "";
		}
		int ndx = req.getRequestURL().indexOf(this.restName);
		
		JSONObject wrkjson = new JSONObject();
		wrkjson.put("id", userJson.get("id"));
		wrkjson.put("loginid", userJson.get("loginid"));
		String wrkstr = (String) req.getHeader("caller");
		JSONObject jsonCaller = getJson(wrkstr);
		wrkjson.put("callbackNext", jsonCaller.get("callbackNext"));
		wrkjson.put("callbackPrev", jsonCaller.get("callbackPrev"));
		wrkjson.put("service", jsonCaller.get("serviceOrig"));

		try {
			wrkstr =  URLEncoder.encode(wrkjson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String url = req.getRequestURL().substring(0,ndx)+page+"?jsonitem="+ wrkstr;
		//LoginRoot.sessions.put(sessionId,user);

		StringBuilder sb = new StringBuilder();
		sb.append("<br/>Please click the url below to confirm your email.thanks.<br/>"); 
		sb.append("<br/><a href="+url+">click here</a><br/>"); 
		return sb.toString();
	}
	private void userDefaults(Users user) {
		String email = user.getEmail();
		String loginid = user.getLoginid();
		if (email == null || email.length() == 0) {
		    Pattern pattern = Pattern.compile("^[a-zA-Z].*\\@.*\\..*$");
		    Matcher matcher = pattern.matcher(loginid);
		    if(matcher.find()) {
		      user.setEmail(loginid);
		    } else {
		    	user.setEmail("");
		    }
		}
		user.setCreatedate(System.currentTimeMillis());
		user.setFailedcount(0);
	}
	@SuppressWarnings("unchecked")
	private void initialzeUserFromCaller(Users user, HttpServletRequest req,JSONObject outjson) {
		String caller = req.getHeader("caller");
		JSONObject json = getJson(caller);
		jsonCallerInfo(outjson,caller);
		json.put("service", json.get("serviceOrig"));
		new MapJsonToObject<>(json,user);
	}
	@SuppressWarnings("unchecked")
	private JSONObject jsonMapper(Users user,JSONObject json) {
		
		MapObjectToJson<Users> mapper = new MapObjectToJson<Users>(user,user.getClass());
		JSONArray jsonArray = new JSONArray();
		JSONObject wrkJson = mapper.getJson();
		wrkJson.remove("password");
		jsonArray.add(wrkJson);
		json.put(wrkJson.get("beanname"), jsonArray);
		
		return json;
	}
	private void jsonCallerInfo(JSONObject json,HttpServletRequest req) {
		String caller = req.getHeader("caller");
		if (caller == null) {
			caller = (String)req.getSession().getAttribute("caller");
		}
		jsonCallerInfo(json, caller);
	}
	@SuppressWarnings("unchecked")
	private void jsonCallerInfo(JSONObject json,String caller) {
		JSONObject callerJson = getJson(caller);
		String callback = (String)callerJson.get("callbackNext");
		if (json.get("srvcompleted") == null || !(Boolean)json.get("srvcompleted")) callback = (String)callerJson.get("callbackPrev");
		json.put("html",callback);
		json.put("service", (String)callerJson.get("serviceOrig"));
	}
}
