package com.assoc.jad.login.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public abstract class ALogin {

    protected String hashPasscode(String password) {

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
    protected JSONObject getJson(String jsonitem) {
		JSONObject jsonIn = new JSONObject();
		if (jsonitem != null ) {
			try {
				jsonIn = (JSONObject) new JSONParser().parse(jsonitem);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return jsonIn;
	}
}
