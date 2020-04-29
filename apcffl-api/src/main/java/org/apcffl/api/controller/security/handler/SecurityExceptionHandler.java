package org.apcffl.api.controller.security.handler;

import org.apcffl.api.exception.EmailException;
import org.apcffl.api.exception.SecurityException;
import static org.apcffl.api.exception.constants.Enums.ErrorCodeEnums.*;

import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface SecurityExceptionHandler {

	@ExceptionHandler(SecurityException.class)
	default ResponseEntity<ErrorDto> handleSecurityException(SecurityException ex) {
		return new ResponseEntity<ErrorDto>(
				createErrorFromException(AuthorizationError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(EmailException.class)
	default ResponseEntity<ErrorDto> handleEmailException(EmailException ex) {
		return new ResponseEntity<ErrorDto>(
				createErrorFromException(EmailSenderError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	default ErrorDto createErrorFromException(String errorCode, String message) {
		return new ErrorDto(errorCode, message);
	}
}
