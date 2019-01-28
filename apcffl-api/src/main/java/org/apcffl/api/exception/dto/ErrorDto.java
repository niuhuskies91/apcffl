package org.apcffl.api.exception.dto;

import java.io.Serializable;

public class ErrorDto implements Serializable {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 9211901045371678445L;
	
	private String errorCode;
	private String message;
	
	public ErrorDto(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getMessage() {
		return message;
	}
}
