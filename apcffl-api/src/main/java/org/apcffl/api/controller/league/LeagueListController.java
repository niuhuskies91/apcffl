package org.apcffl.api.controller.league;

import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.LeagueListsResponse;
import org.apcffl.api.league.service.LeagueListServices;
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

@Api(value = "League List Services")
@RestController
@RequestMapping("/league")
public class LeagueListController extends ApcfflController {

	private final LeagueListServices service;
	
	public LeagueListController(final LeagueListServices service, final SessionManager sessionManager) {
		this.service = service;
		this.sessionManager = sessionManager;
	}

	@ApiOperation(value="Retrieve all leagues request", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = LeagueListsResponse.class)
	@RequestMapping(value="/allLeagues", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<LeagueListsResponse> allLeagues(@RequestBody ApiRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			LeagueListsResponse response = new LeagueListsResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		// retrieve all accounts
		return new ResponseEntity<>(service.allLeagues(request), HttpStatus.OK);
	}
}
