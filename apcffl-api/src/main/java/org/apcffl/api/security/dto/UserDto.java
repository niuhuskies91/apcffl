package org.apcffl.api.security.dto;

public class UserDto {
	private String userName;
	private String userGroupName;
	private String securityToken;
	
	public UserDto(String userName, String userGroupName, String securityToken) {
		this.userName = userName;
		this.userGroupName = userGroupName;
		this.securityToken = securityToken;
	}

	public String getUserName() {
		return userName;
	}
	public String getUserGroupName() {
		return userGroupName;
	}
	public String getSecurityToken() {
		return securityToken;
	}
}
