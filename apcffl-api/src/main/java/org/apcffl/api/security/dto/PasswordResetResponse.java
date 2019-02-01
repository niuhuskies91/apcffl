package org.apcffl.api.security.dto;

public class PasswordResetResponse {

	private String message;
	
	public PasswordResetResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
