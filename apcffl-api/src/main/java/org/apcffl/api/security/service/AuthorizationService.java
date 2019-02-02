package org.apcffl.api.security.service;

import org.apcffl.api.security.dto.PasswordResetRequest;
import org.apcffl.api.security.dto.UserDto;

public interface AuthorizationService {
	
	public UserDto login(String userName, String password);
	
	public void passwordResetToken(String userName);
	
	public void userNameRecovery(String email);
	
	public void resetPassword(PasswordResetRequest request);
	
	public boolean isValidSessionToken(String sessionToken, String userName);
}
