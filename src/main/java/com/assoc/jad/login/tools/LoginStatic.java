package com.assoc.jad.login.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class LoginStatic {
	public static String projAPI = "com.assoc.jad.login";		// database doesn't accept dots.
	public static boolean webContentConfig = false;
	public static boolean databaseConfig =false;
	
	public static final	String webContentTag = "resources/WebContent";
	public static final String schema = projAPI.replaceAll("\\.", "")+"db";
	//public static final String loginHTML = projAPI+"/login"+Configuration.DEFAULT_SUFFIX;

	public static String findFormName(HttpServletRequest request) {
		
		if (request == null) return "";
		
		String formname = request.getParameter("formname");
		if (formname == null) return "";
		
		Enumeration<String> keys = request.getParameterNames();
		while (keys.hasMoreElements()) {
			String fullFormName = keys.nextElement();
			if (fullFormName.startsWith(formname)) continue;
			if (fullFormName.indexOf(formname) == -1) continue;
			formname = fullFormName;
			break;
		}
		return formname+":";
	}
    public synchronized static String hashPasscode(String password) {

    	if (password == null) password = "";
        MessageDigest md=null;
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
        md.update(password.getBytes());

        byte byteData[] = md.digest(); 

        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }
}
