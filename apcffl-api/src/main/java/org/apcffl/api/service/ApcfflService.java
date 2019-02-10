package org.apcffl.api.service;

import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.exception.constants.ErrorCodeEnums;
import org.apcffl.api.exception.SecurityException;

public abstract class ApcfflService {
	
	public SessionManagerBo sessionManager;

	public boolean isValidSessionToken(String sessionToken, String userName) {
		
		boolean flag = sessionManager.isValidSessionToken(userName, sessionToken);
		if (!flag) {
			throw new SecurityException(ErrorCodeEnums.SessionTokenExpired.toString());
		}
		return flag;
	}
}
