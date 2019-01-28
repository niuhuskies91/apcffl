package org.apcffl.api.controller.security;

import org.apcffl.api.controller.security.handler.SecurityExceptionHandler;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.security.service.AuthorizationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Security / Authorization Services")
@RestController
@RequestMapping("/api/security")
public class SecurityController implements SecurityExceptionHandler {
	
	private final AuthorizationService service;
	
	public SecurityController(AuthorizationService service) {
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
}
