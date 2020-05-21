package org.apcffl.api.controller.league;

import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.ConferenceListResponse;
import org.apcffl.api.league.dto.TeamResponse;
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

@Api(value = "Roster Services")
@RestController
@RequestMapping("/roster")
public class RosterController extends ApcfflController {

	private final LeagueListServices leagueListServices;
	private final LeagueServices leagueServices;
	
	public RosterController(final LeagueListServices leagueListServices, final LeagueServices leagueServices, 
			final SessionManager sessionManager) {
		
		this.leagueListServices = leagueListServices;
		this.leagueServices = leagueServices;
		this.sessionManager = sessionManager;
	}

	@ApiOperation(value="Retrieve all conferences request", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = ConferenceListResponse.class)
	@RequestMapping(value="/allConferences", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ConferenceListResponse> allConferences(@RequestBody ApiRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			ConferenceListResponse response = new ConferenceListResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		// retrieve all conferences
		return new ResponseEntity<>(leagueListServices.allConferences(request), HttpStatus.OK);
	}
	
	@ApiOperation(value="Retrieve team roster for the team owner", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = TeamResponse.class)
	@RequestMapping(value="/teamRoster", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<TeamResponse> teamRoster(@RequestBody ApiRequest request) {
		
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			TeamResponse response = new TeamResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		// retrieve the team roster
		return new ResponseEntity<>(leagueServices.teamRoster(request), HttpStatus.OK);
	}
}
