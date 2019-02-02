package org.apcffl.api.security.dto;

public class GenericSecurityResponse {

	private String message;
	
	public GenericSecurityResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
