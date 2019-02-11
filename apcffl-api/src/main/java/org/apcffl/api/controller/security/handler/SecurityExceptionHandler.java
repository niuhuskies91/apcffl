package org.apcffl.api.controller.security.handler;

import org.apcffl.api.exception.EmailException;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.exception.constants.ErrorCodeEnums;
import org.apcffl.api.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface SecurityExceptionHandler {

	@ExceptionHandler(SecurityException.class)
	default ResponseEntity<ErrorDto> handleSecurityException(SecurityException ex) {
		return new ResponseEntity<ErrorDto>(
				createErrorFromException(ErrorCodeEnums.AuthorizationError.toString(), ex.getMessage()),
				HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(EmailException.class)
	default ResponseEntity<ErrorDto> handleEmailException(EmailException ex) {
		return new ResponseEntity<ErrorDto>(
				createErrorFromException(ErrorCodeEnums.EmailSenderError.toString(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	default ErrorDto createErrorFromException(String errorCode, String message) {
		return new ErrorDto(errorCode, message);
	}
}
