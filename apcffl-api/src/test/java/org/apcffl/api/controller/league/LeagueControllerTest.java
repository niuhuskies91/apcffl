package org.apcffl.api.controller.league;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apcffl.ApcfflTest;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.league.dto.LeagueListsResponse;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.TeamsDivisionAssignmentRequest;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.league.service.LeagueServices;
import org.apcffl.api.service.manager.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LeagueController.class)
public class LeagueControllerTest {
	
	private static final String LEAGUE_LIST_RETRIEVE_ALL_URL           = "/league/allLeagues";
	private static final String LEAGUE_LIST_RETRIEVE_LEAGUE_OWNERS_URL = "/league/leagueOwners";
	private static final String LEAGUE_TEAMS_DIVISION_ASSIGNMENT_URL   = "/league/teamsDivisionAssignment";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	LeagueListServices leagueListservices;
	
	@MockBean 
	LeagueServices leagueServices;
	
	@MockBean
	SessionManager sessionManager;
	
	@Captor
	private ArgumentCaptor<String> userNameCaptor;
	
	@Captor
	private ArgumentCaptor<String> tokenCaptor;
	
	@Captor
	private ArgumentCaptor<ApiRequest> apiRequestCaptor;
	
	@Captor
	private ArgumentCaptor<LeagueOwnersRequest> leagueOwnerRequestCaptor;
	
	@Captor
	private ArgumentCaptor<TeamsDivisionAssignmentRequest> teamDivisionAssignmentCaptor;
	
	private ObjectMapper objectMapper;
 
    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		
    	objectMapper = new ObjectMapper();
		
		sessionManager.init();
		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(true);
    }
    
    @Test
    public void verify_allLeagues_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(LEAGUE_LIST_RETRIEVE_ALL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueListservices, never()).allLeagues(apiRequestCaptor.capture());
    }
    
    @Test
    public void verify_allLeagues() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

    	LeagueListsResponse mockResponse = new LeagueListsResponse();
    	mockResponse.setLeagues(ApcfflTest.buildLeagues());
		when(leagueListservices.allLeagues(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(LEAGUE_LIST_RETRIEVE_ALL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.leagues[0].leagueName").value(ApcfflTest.LEAGUE_1_NAME))
			.andExpect(jsonPath("$.leagues[0].numDivisions").value(ApcfflTest.LEAGUE_1_NUM_DIV))
			.andExpect(jsonPath("$.leagues[0].numTeams").value(ApcfflTest.LEAGUE_1_NUM_TEAMS));
				
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueListservices, times(1)).allLeagues(apiRequestCaptor.capture());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, apiRequestCaptor.getValue().getUserGroupName());
    }
    
    @Test
    public void verify_leagueOwners_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	LeagueOwnersRequest request = ApcfflTest.buildLeagueOwnersRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(LEAGUE_LIST_RETRIEVE_LEAGUE_OWNERS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueListservices, never()).leagueOwners(leagueOwnerRequestCaptor.capture());
    }
    
    @Test
    public void verify_leagueOwners() throws Exception {
    	
    	// prepare test data
    	
    	LeagueOwnersRequest request = ApcfflTest.buildLeagueOwnersRequest();

    	LeagueOwnersResponse mockResponse = new LeagueOwnersResponse();
    	mockResponse.setLeagueOwners(Arrays.asList(ApcfflTest.buildLeagueOwner()));
    	when(leagueListservices.leagueOwners(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(LEAGUE_LIST_RETRIEVE_LEAGUE_OWNERS_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.leagueOwners[0].email").value(ApcfflTest.OWNER_EMAIL1))
			.andExpect(jsonPath("$.leagueOwners[0].firstName").value(ApcfflTest.OWNER_FIRST_NAME))
			.andExpect(jsonPath("$.leagueOwners[0].lastName").value(ApcfflTest.OWNER_LAST_NAME))
			.andExpect(jsonPath("$.leagueOwners[0].activeFlag").value(true))
			.andExpect(jsonPath("$.leagueOwners[0].teamName").value(ApcfflTest.LEAGUE_1_TEAM_1))
			.andExpect(jsonPath("$.leagueOwners[0].divisionName").value(ApcfflTest.LEAGUE_1_DIV_1));
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueListservices, times(1)).leagueOwners(leagueOwnerRequestCaptor.capture());
		LeagueOwnersRequest captorReq = leagueOwnerRequestCaptor.getValue();
		assertEquals(ApcfflTest.TEST_TOKEN, captorReq.getSecurityToken());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, captorReq.getLeagueName());
		assertEquals(ApcfflTest.USER_NAME, captorReq.getUserName());
		assertEquals(ApcfflTest.USER_GROUP_ADMIN, captorReq.getUserGroupName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, captorReq.getLeagueName());
    }
    
    @Test
    public void verify_teamsDivisionAssignment_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	TeamsDivisionAssignmentRequest request = ApcfflTest.buildTeamsDivisionAssignmentRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(LEAGUE_TEAMS_DIVISION_ASSIGNMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueServices, never()).teamsDivisionAssignment(teamDivisionAssignmentCaptor.capture());
    }
    
    @Test
    public void verify_teamsDivisionAssignment() throws Exception {
    	
    	// prepare test data
    	
    	TeamsDivisionAssignmentRequest request = ApcfflTest.buildTeamsDivisionAssignmentRequest();

    	LeagueOwnersResponse mockResponse = new LeagueOwnersResponse();
    	mockResponse.setLeagueOwners(Arrays.asList(ApcfflTest.buildLeagueOwner()));
    	when(leagueServices.teamsDivisionAssignment(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(LEAGUE_TEAMS_DIVISION_ASSIGNMENT_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.leagueOwners[0].email").value(ApcfflTest.OWNER_EMAIL1))
			.andExpect(jsonPath("$.leagueOwners[0].firstName").value(ApcfflTest.OWNER_FIRST_NAME))
			.andExpect(jsonPath("$.leagueOwners[0].lastName").value(ApcfflTest.OWNER_LAST_NAME))
			.andExpect(jsonPath("$.leagueOwners[0].activeFlag").value(true))
			.andExpect(jsonPath("$.leagueOwners[0].teamName").value(ApcfflTest.LEAGUE_1_TEAM_1))
			.andExpect(jsonPath("$.leagueOwners[0].divisionName").value(ApcfflTest.LEAGUE_1_DIV_1));
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueServices, times(1)).teamsDivisionAssignment(teamDivisionAssignmentCaptor.capture());
		TeamsDivisionAssignmentRequest captorRequest = teamDivisionAssignmentCaptor.getValue();
		assertEquals(ApcfflTest.LEAGUE_1_NAME, captorRequest.getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, captorRequest.getOwnerLeagueName());
		assertEquals(ApcfflTest.TEST_TOKEN, captorRequest.getSecurityToken());
		assertEquals(ApcfflTest.USER_GROUP_ADMIN, captorRequest.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, captorRequest.getUserName());
    }
}
