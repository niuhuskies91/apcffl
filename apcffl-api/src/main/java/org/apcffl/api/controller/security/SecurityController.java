package org.apcffl.api.controller.security;

import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.controller.security.handler.SecurityExceptionHandler;
import org.apcffl.api.security.dto.PasswordResetRequest;
import org.apcffl.api.security.dto.GenericSecurityResponse;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.security.service.AuthorizationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Security / Authorization Services")
@RestController
@RequestMapping("/api/security")
public class SecurityController implements SecurityExceptionHandler {
	
	private final AuthorizationService service;
	
	public SecurityController(final AuthorizationService service) {
		this.service = service;
	}

	@ApiOperation(value="Login Authorization", httpMethod = "GET",
			produces = MediaType.APPLICATION_JSON_VALUE, response = UserDto.class)
	@GetMapping(path="/login/userName/{userName}/password/{password}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public UserDto login(
			@PathVariable(required=true) String userName, 
			@PathVariable(required=true) String password) {
		
		return service.login(userName, password);
	}

	@ApiOperation(value="Password Reset token request", httpMethod = "GET",
			produces = MediaType.APPLICATION_JSON_VALUE, response = GenericSecurityResponse.class)
	@GetMapping(path="/passwordResetToken/userName/{userName}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public GenericSecurityResponse passwordResetToken(@PathVariable(required=true) String userName) {
		
		service.passwordResetToken(userName);
		
		return new GenericSecurityResponse(UIMessages.MSG_GENERIC_CHECK_EMAIL);
	}

	@ApiOperation(value="User name recovery request", httpMethod = "GET",
			produces = MediaType.APPLICATION_JSON_VALUE, response = GenericSecurityResponse.class)
	@GetMapping(path="/userNameRecovery/email/{email}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public GenericSecurityResponse userNameRecovery(@PathVariable(required=true) String email) {
		
		service.userNameRecovery(email);
		
		return new GenericSecurityResponse(UIMessages.MSG_GENERIC_CHECK_EMAIL);
	}

	@ApiOperation(value="Password Reset request", httpMethod = "POST",
			produces = MediaType.APPLICATION_JSON_VALUE, response = GenericSecurityResponse.class)
	@RequestMapping(method = RequestMethod.POST, value="/passwordReset")
	@ResponseBody
	public GenericSecurityResponse passwordReset(@RequestBody PasswordResetRequest request) {
		
		service.resetPassword(request);
		
		return new GenericSecurityResponse(UIMessages.MSG_PSWD_RESET_SUCCESS);
	}
} 
