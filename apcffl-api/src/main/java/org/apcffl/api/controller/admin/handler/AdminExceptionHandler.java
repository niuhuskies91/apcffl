package org.apcffl.api.controller.admin.handler;

import org.apcffl.api.exception.PersistenceException;
import org.apcffl.api.exception.constants.ErrorCodeEnums;
import org.apcffl.api.exception.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface AdminExceptionHandler {

	@ExceptionHandler(PersistenceException.class)
	default ResponseEntity<ErrorDto> handlePersistenceException(PersistenceException ex) {
		return new ResponseEntity<ErrorDto>(
				createErrorFromException(ErrorCodeEnums.AdminError.toString(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	default ErrorDto createErrorFromException(String errorCode, String message) {
		return new ErrorDto(errorCode, message);
	}
}
