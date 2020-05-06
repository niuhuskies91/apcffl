package org.apcffl.api.controller;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.*;

import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.service.manager.SessionManager;

public abstract class ApcfflController {
	
	protected SessionManager sessionManager;

	protected ErrorDto isValidSessionToken(String sessionToken, String userName) {
		
		if (!sessionManager.isValidSessionToken(userName, sessionToken)) {
			return new ErrorDto(SessionTokenExpired.toString(), 
					UIMessages.ERROR_TOKEN_EXPIRED);
		}
		return null;
	}
}
