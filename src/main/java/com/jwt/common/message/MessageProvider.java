package com.jwt.common.message;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



/**
 * @author stefan.busnita
 * jwt project
 */
@Component
public class MessageProvider {

	@Autowired
	Environment environment;
	
	private static final String successKey = "success";
	private static final String errorKey = "error";
	
	public String constructKeyFromMethod(Method method) {
		String className = method.getDeclaringClass().getCanonicalName();
		String[] resultSet = className.split(".dao.");
		String methodName = method.getName();
		return resultSet[1]+'.'+methodName;
	}
	
	private String getPartialKey(Class<?> classObject,String methodName) {
		String partialKey = classObject.getCanonicalName();
		String[] resultSet = partialKey.split(".dao.");
		
		return resultSet[1]+'.'+methodName;
	}
	
	public String getLocalizedMessageForClass(Class<?> classObject,String methodName) {
		String partialKey = getPartialKey(classObject, methodName);
		return getLocalizedMessage(partialKey);
	}
	
	public String getReqLocalizedMessageForClass(Class<?> classObject,String methodName) {
		String partialKey = getPartialKey(classObject, methodName);
		return getRequiredLocalizedMessage(partialKey);
	}
	
	public String getLocalizedMessage(String key) {
		return environment.getProperty(key);
	}

	public String getDefaultSuccessMessage() {
		return getRequiredLocalizedMessage(successKey);
	}
	
	public String getDefaultErrorMessage() {
		return getRequiredLocalizedMessage(errorKey);
	}
	
	public String getRequiredLocalizedMessage(String key) {
		return environment.getRequiredProperty(key);
	}
}
