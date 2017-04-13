package com.jwt.common.message;

import com.jwt.common.constants.ResultLevel;

/**
 * @author stefan.busnita
 */
public class Flash {

	private ResultLevel resultLevel;
	private String message;
	private Object result;
	
	public Flash() {
		
	}
	
	public Flash(ResultLevel resultLevel,String message) {
		this.resultLevel = resultLevel;
		this.message = message;
	}
	
	public Flash(ResultLevel resultLevel,String message,Object result) {
		this.resultLevel = resultLevel;
		this.message = message;
		this.result = result;
	}
	
	public ResultLevel getResultLevel() {
		return resultLevel;
	}
	public void setResultLevel(ResultLevel resultLevel) {
		this.resultLevel = resultLevel;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}
