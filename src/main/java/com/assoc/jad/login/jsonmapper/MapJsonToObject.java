package com.assoc.jad.login.jsonmapper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;

public class MapJsonToObject<T> {
	private static Log LOG = LogFactory.getLog(MapJsonToObject.class);
	
	private List<Method> gettersMethods = new ArrayList<>();
	private List<Method> settersMethods = new ArrayList<>();
	private T instance;
	
	public MapJsonToObject(JSONObject json,T instance) {
		this.instance = instance;
		Class<?> clazz = instance.getClass();
		findObjGetters(clazz);
		findField(json,clazz);
	}
	
	private void findObjGetters(Class<?> clazz) {		

		Method[] methods = clazz.getMethods(); 
		for (int i=0;i<methods.length;i++) {
			String wrkname = methods[i].getName();
			if (wrkname.startsWith("get")) gettersMethods.add(methods[i]);
			else if (wrkname.startsWith("set")) settersMethods.add(methods[i]);
		}
	}
	@SuppressWarnings("unchecked")
	private void findField(JSONObject json,Class<?> clazz) {		
		Iterator<String> keys = json.keySet().iterator();
		while (keys.hasNext()) {
			String fieldname = keys.next();
			Object value = json.get(fieldname);
        	String wrkName = "set"+fieldname;
    		for (int j = 0; j < settersMethods.size(); j++) {
    			Method wrkMethod = settersMethods.get(j);
				if (!wrkName.equalsIgnoreCase(wrkMethod.getName())) continue;
				
				String argType = wrkMethod.getParameterTypes()[0].getName();
				methodParamsExecSetter(value,argType,wrkMethod);
//				executeSetters(wrkMethod,value);
//				updateInstance(value,wrkMethod,fieldname);
				break;
    		}
		}
	}
	private <S> void methodParamsExecSetter(S value,String argType,Method method) {
		
		if (value.getClass().getCanonicalName().equals("java.lang.String")) {		
			if (argType.equals("int") || argType.equals("java.lang.Integer") ) 
				executeSetters( method,Integer.valueOf(value.toString().toString()));
			else if (argType.equals("long") || argType.equals("java.lang.Long")) 
				executeSetters( method,Long.valueOf(value.toString()));
			else if (argType.equals("java.lang.String")) executeSetters(method,value);
			
		} else executeSetters(method,value);

//		argType.equals("byte") ||
//		argType.equals("char") ||
//		argType.equals("short") ||
//		argType.equals("float") ||
//		argType.equals("double") ||
		
	}
	private void executeSetters(Method method,Object arg) {
		Object[] arguments = {arg};
		try {
			method.invoke(instance, arguments);
		} catch (Exception e) {
			String msg = "invoked method failed "+instance.getClass().getName()+"."+method.getName()+" "+e;
			LOG.warn(msg);
		}
	}
//	private <S> void updateInstance(Object value, Method wrkMethod,String fieldname) {
//
////			Class<?> retClass = wrkMethod.getReturnType();
////			String classname = retClass.toString().replaceFirst("class", "").trim();
//			
////			execMethod(classname, instance, wrkMethod);
////			if (value != null) {				
////				if (!isRetTypeAnObject(classname)) {
////					JSONObject newJson = embeddedObeject(retClass, fieldname,value);
////					json.put(fieldname, newJson);
////				} else json.put(fieldname, value.toString());
////			} else json.put(fieldname, value);
//	} 
//	private JSONObject  embeddedObeject(Class<?> clazz,String fieldname,Object instance) {
//        List<Method> newMethods = Arrays.asList(clazz.getMethods());
//        List<Field> newFields = Arrays.asList(clazz.getDeclaredFields());
//        JSONObject newJson = new JSONObject();
//        for (int i=0;i<newFields.size();i++) {
//        	String wrkName = "get"+newFields.get(i).getName();
//        	for (int j=0;j<newMethods.size();j++) {
//        		String methodName = newMethods.get(j).getName();
//        		if (!methodName.startsWith("get")) continue;
//        		if (!wrkName.equalsIgnoreCase(methodName)) continue;
//        		
//        		getFieldValue(instance, newMethods.get(j),newFields.get(i).getName(),newJson) ;       		
//        	}
//        }
//        return newJson;
//	}
//	private Object execMethod(String classname, Object instance, Method method) {
//		Object output = executeGetters(instance,method);
//		
//		if (classname.equals("[B")) {	//TODO for image the return type is String but the input is array [B
//			output = Base64.getEncoder().encodeToString((byte[]) output);
//		}
//		return output;
//	} 
//	private boolean isRetTypeAnObject(String retTypeClass) {
//		boolean flag = false;
//
//		if (retTypeClass.equals("java.math.BigDecimal") ||
//			retTypeClass.equals("[B") ||
//			retTypeClass.equals("int") ||
//			retTypeClass.equals("long") ||
//			retTypeClass.equals("byte") ||
//			retTypeClass.equals("char") ||
//			retTypeClass.equals("short") ||
//			retTypeClass.equals("float") ||
//			retTypeClass.equals("double") ||
//			(retTypeClass.indexOf("boolean") != -1) ||
//			retTypeClass.equals("java.lang.String"))
//			flag = true;
//		
//		return flag;
//	}

/*
 * getters and setters
 */
	public T getInstance() {
		return instance;
	}
	public void setInstance(T instance) {
		this.instance = instance;
	}

}
