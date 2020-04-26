package org.apcffl.api.controller.admin.handler;

import org.apcffl.api.exception.PersistenceException;
import static org.apcffl.api.exception.constants.Enums.ErrorCodeEnums.*;
import org.apcffl.api.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface AdminExceptionHandler {

	@ExceptionHandler(PersistenceException.class)
	default ResponseEntity<ErrorDto> handlePersistenceException(PersistenceException ex) {
		return new ResponseEntity<ErrorDto>(
				createErrorFromException(AdminError.toString(), ex.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	default ErrorDto createErrorFromException(String errorCode, String message) {
		return new ErrorDto(errorCode, message);
	}
}
