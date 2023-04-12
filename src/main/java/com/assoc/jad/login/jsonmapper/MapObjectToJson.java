package com.assoc.jad.login.jsonmapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

public class MapObjectToJson<T> {
	private static Log LOG = LogFactory.getLog(MapObjectToJson.class);
	
	private JSONObject json = new JSONObject();
	private List<Method> gettersMethods = new ArrayList<>();
	private List<Method> settersMethods = new ArrayList<>();
	
	public MapObjectToJson(T obj,Class<?> originalClass) {
		findObjGetters(originalClass);
		findField(obj,originalClass);
	}
	
	@SuppressWarnings("unchecked")
	private void findObjGetters(Class<?> clazz) {		

		StringBuilder className = new StringBuilder(clazz.getSimpleName());
		char lower = (char) (className.charAt(0) ^ ' ');
		className.setCharAt(0, lower);
		json.put("beanname", className.toString());
		
		Method[] methods = clazz.getMethods(); 
		for (int i=0;i<methods.length;i++) {
			String wrkname = methods[i].getName();
			if (wrkname.startsWith("get")) gettersMethods.add(methods[i]);
			else if (wrkname.startsWith("set")) settersMethods.add(methods[i]);
		}
	}
	private void findField(T obj,Class<?> clazz) {		

		Field[] fields = clazz.getDeclaredFields();
		for (int i=0;i<fields.length;i++) {
        	String wrkName = "get"+fields[i].getName();
    		for (int j = 0; j < gettersMethods.size(); j++) {
    			Method wrkMethod = gettersMethods.get(j);
				if (!wrkName.equalsIgnoreCase(wrkMethod.getName())) continue;
				
				getFieldValue(obj,wrkMethod,fields[i].getName(),json);
    		}
		}
	}
	@SuppressWarnings("unchecked")
	private <S> void getFieldValue(Object instance, Method wrkMethod,String fieldname,JSONObject json) {

			Class<?> retClass = wrkMethod.getReturnType();
			String classname = retClass.toString().replaceFirst("class", "").trim();
			
			Object value = execMethod(classname, instance, wrkMethod);
			if (value != null) {				
				if (!isRetTypeAnObject(classname)) {
					JSONObject newJson = embeddedObeject(retClass, fieldname,value);
					json.put(fieldname, newJson);
				} else json.put(fieldname, value.toString());
			} else json.put(fieldname, value);
	} 
	private JSONObject  embeddedObeject(Class<?> clazz,String fieldname,Object instance) {
        List<Method> newMethods = Arrays.asList(clazz.getMethods());
        List<Field> newFields = Arrays.asList(clazz.getDeclaredFields());
        JSONObject newJson = new JSONObject();
        for (int i=0;i<newFields.size();i++) {
        	String wrkName = "get"+newFields.get(i).getName();
        	for (int j=0;j<newMethods.size();j++) {
        		String methodName = newMethods.get(j).getName();
        		if (!methodName.startsWith("get")) continue;
        		if (!wrkName.equalsIgnoreCase(methodName)) continue;
        		
        		getFieldValue(instance, newMethods.get(j),newFields.get(i).getName(),newJson) ;       		
        	}
        }
        return newJson;
	}
	private Object execMethod(String classname, Object instance, Method method) {
		Object output = executeGetters(instance,method);
		
		if (classname.equals("[B")) {	//TODO for image the return type is String but the input is array [B
			output = Base64.getEncoder().encodeToString((byte[]) output);
		}
		return output;
	} 
	private boolean isRetTypeAnObject(String retTypeClass) {
		boolean flag = false;

		if (retTypeClass.equals("java.math.BigDecimal") ||
			retTypeClass.equals("[B") ||
			retTypeClass.equals("int") ||
			retTypeClass.equals("long") ||
			retTypeClass.equals("byte") ||
			retTypeClass.equals("char") ||
			retTypeClass.equals("short") ||
			retTypeClass.equals("float") ||
			retTypeClass.equals("double") ||
			(retTypeClass.indexOf("boolean") != -1) ||
			retTypeClass.equals("java.lang.String"))
			flag = true;
		
		return flag;
	}
	private Object executeGetters(Object instance,Method method) {
		Object retObj = null;
		Object[] arguments = null;
		try {
			retObj = method.invoke(instance, arguments);
		} catch (Exception e) {
			String msg = "invoked method failed "+instance.getClass().getName()+"."+method.getName()+" "+e;
			LOG.warn(msg);
		}
		return retObj;
	}

/*
 * getters and setters
 */
	public JSONObject getJson() {
		return json;
	}
	public void setJson(JSONObject json) {
		this.json = json;
	}

}
