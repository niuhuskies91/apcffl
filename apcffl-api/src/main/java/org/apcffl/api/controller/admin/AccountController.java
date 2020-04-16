package org.apcffl.api.controller.admin;

import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.controller.admin.handler.AdminExceptionHandler;
import org.apcffl.api.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Account Services")
@RestController
@RequestMapping("/account")
public class AccountController extends ApcfflController implements AdminExceptionHandler {
	
	private final AdminService service;
	
	public AccountController(final AdminService service, final SessionManagerBo sessionManager) {
		this.service = service;
		this.sessionManager = sessionManager;
	}

	@ApiOperation(value="User/Owner account retrieval request", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = AccountResponse.class)
	@RequestMapping(value="/accountRetrieval", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AccountResponse> accountRetrieval(@RequestBody AccountRequest request) {
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			AccountResponse response = new AccountResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		// retrieve the account
		return new ResponseEntity<>(service.accountRetrieval(request), HttpStatus.OK);
	}
}
