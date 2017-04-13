package com.jwt.common.constants;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author stefan.busnita
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResultLevel {

	Success(1),Info(2),Warning(3),Danger(4);
	
	private int value;
	
	ResultLevel(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;		
	}
}
