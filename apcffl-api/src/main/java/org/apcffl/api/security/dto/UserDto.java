package org.apcffl.api.security.dto;

import org.apcffl.api.dto.ApiRequest;

public class UserDto extends ApiRequest {
	
	public UserDto(String userName, String userGroupName, String securityToken) {
		this.userName = userName;
		this.userGroupName = userGroupName;
		this.securityToken = securityToken;
	}
}
