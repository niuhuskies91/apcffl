package org.apcffl.api.controller;

import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.exception.constants.ErrorCodeEnums;

public abstract class ApcfflController {
	
	private static final String ERROR_TOKEN_EXPIRED = "The login session has expired.";
	
	protected SessionManagerBo sessionManager;

	protected AccountResponse isValidSessionToken(String sessionToken, String userName) {
		AccountResponse response = null;
		
		if (!sessionManager.isValidSessionToken(userName, sessionToken)) {
			response = new AccountResponse();
			ErrorDto error = 
				new ErrorDto(ErrorCodeEnums.SessionTokenExpired.toString(), ERROR_TOKEN_EXPIRED);
			response.setError(error);
		}
		return response;
	}
}
