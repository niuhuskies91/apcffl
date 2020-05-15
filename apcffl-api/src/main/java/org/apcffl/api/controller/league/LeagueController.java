package org.apcffl.api.controller.league;

import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.LeagueListsResponse;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.TeamsDivisionAssignmentRequest;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.league.service.LeagueServices;
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
public class LeagueController extends ApcfflController {

	private final LeagueListServices leagueListServices;
	
	private final LeagueServices leagueServices;
	
	public LeagueController(final LeagueListServices leagueListServices, final SessionManager sessionManager,
			final LeagueServices leagueServices) {
		this.leagueListServices = leagueListServices;
		this.sessionManager = sessionManager;
		this.leagueServices = leagueServices;
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
		return new ResponseEntity<>(leagueListServices.allLeagues(request), HttpStatus.OK);
	}

	@ApiOperation(value="Retrieve all owners in a league", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = LeagueOwnersResponse.class)
	@RequestMapping(value="/leagueOwners", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<LeagueOwnersResponse> leagueOwners(@RequestBody LeagueOwnersRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			LeagueOwnersResponse response = new LeagueOwnersResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		// retrieve all accounts
		return new ResponseEntity<>(leagueListServices.leagueOwners(request), HttpStatus.OK);
	}

	@ApiOperation(value="Assign teams to a division in a league", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = LeagueOwnersResponse.class)
	@RequestMapping(value="/teamsDivisionAssignment", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<LeagueOwnersResponse> teamsDivisionAssignment(@RequestBody TeamsDivisionAssignmentRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			LeagueOwnersResponse response = new LeagueOwnersResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		// assign teams to divisions
		return new ResponseEntity<>(leagueServices.teamsDivisionAssignment(request), HttpStatus.OK);
	}
}
