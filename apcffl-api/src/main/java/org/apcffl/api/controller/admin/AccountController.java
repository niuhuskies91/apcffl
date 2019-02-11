package org.apcffl.api.controller.admin;

import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.controller.admin.handler.AdminExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Account Services")
@RestController
@RequestMapping("/api/account")
public class AccountController extends ApcfflController implements AdminExceptionHandler {
	
	private final AdminService service;
	
	public AccountController(AdminService service, SessionManagerBo sessionManager) {
		this.service = service;
		this.sessionManager = sessionManager;
	}

	@ApiOperation(value="User/Owner account retrieval request", httpMethod = "POST",
			produces = MediaType.APPLICATION_JSON_VALUE, response = AccountResponse.class)
	@RequestMapping(method = RequestMethod.POST, value="/accountRetrieval")
	@ResponseBody
	public ResponseEntity<AccountResponse> accountRetrieval(@RequestBody AccountRequest request) {
		// validate the session token
		AccountResponse response = 
			isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		// retrieve the account
		return new ResponseEntity<>(service.accountRetrieval(request), HttpStatus.OK);
	}
}
