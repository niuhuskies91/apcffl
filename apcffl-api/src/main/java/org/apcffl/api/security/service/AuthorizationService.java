package org.apcffl.api.security.service;

import org.apcffl.api.security.dto.UserDto;

public interface AuthorizationService {
	
	public UserDto login(String userName, String password);

}
