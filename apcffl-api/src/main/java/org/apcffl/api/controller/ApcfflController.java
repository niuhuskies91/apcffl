package org.apcffl.api.controller;

import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.exception.constants.ErrorCodeEnums;

public abstract class ApcfflController {
	
	protected SessionManagerBo sessionManager;

	protected ErrorDto isValidSessionToken(String sessionToken, String userName) {
		
		if (!sessionManager.isValidSessionToken(userName, sessionToken)) {
			return new ErrorDto(ErrorCodeEnums.SessionTokenExpired.toString(), 
					UIMessages.ERROR_TOKEN_EXPIRED);
		}
		return null;
	}
}
