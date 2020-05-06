package org.apcffl.api.controller.admin;

import org.apcffl.api.admin.dto.AccountCreateRequest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.AllAccountsResponse;
import org.apcffl.api.admin.dto.LeagueAssignmentRequest;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.dto.ApiResponse;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.service.manager.SessionManager;
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
public class AccountController extends ApcfflController {
	
	private final AdminService service;
	
	public AccountController(final AdminService service, final SessionManager sessionManager) {
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

	@ApiOperation(value="Retrieve all accounts request. Only Admin level credentials can do this.", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = AllAccountsResponse.class)
	@RequestMapping(value="/accountRetrievalAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AllAccountsResponse> accountRetrievalAll(@RequestBody AccountRequest request) {
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			AllAccountsResponse response = new AllAccountsResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		// retrieve the account
		return new ResponseEntity<>(service.accountRetrievalAll(request), HttpStatus.OK);
	}

	@ApiOperation(value="User/Owner account creation request", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = String.class)
	@RequestMapping(value="/accountCreation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<String> accountCreation(@RequestBody AccountCreateRequest request) {

		String result = service.accountCreate(request);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@ApiOperation(value="User/Owner account update request", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = AccountResponse.class)
	@RequestMapping(value="/accountUpdate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AccountResponse> accountUpdate(@RequestBody AccountRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			AccountResponse response = new AccountResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

		return new ResponseEntity<>(service.accountUpdate(request), HttpStatus.OK);
	}

	@ApiOperation(value="Assign a league to an owner request", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = ApiResponse.class)
	@RequestMapping(value="/ownerLeagueAssignment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApiResponse> ownerLeagueAssignment(@RequestBody LeagueAssignmentRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			ApiResponse response = new AccountResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}

		ApiResponse response = service.ownerLeagueAssignment(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
