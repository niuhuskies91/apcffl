package org.apcffl.api.security.dto;

public class PasswordResetRequest {

	private String userName;
	private Integer passwordResetToken;
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getPasswordResetToken() {
		return passwordResetToken;
	}
	public void setPasswordResetToken(Integer passwordResetToken) {
		this.passwordResetToken = passwordResetToken;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
