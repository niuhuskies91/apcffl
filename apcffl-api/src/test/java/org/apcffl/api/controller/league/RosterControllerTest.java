package org.apcffl.api.controller.league;

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
import org.apcffl.api.league.dto.ConferenceListResponse;
import org.apcffl.api.league.dto.TeamResponse;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.league.service.LeagueServices;
import org.apcffl.api.service.manager.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = RosterController.class)
public class RosterControllerTest {

	private static final String ROSTER_ALL_CONFERENCES_URL    = "/roster/allConferences";
	private static final String ROSTER_TEAM_ROSTER_URL        = "/roster/teamRoster";

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
	
	private ObjectMapper objectMapper;
 
    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		
    	objectMapper = new ObjectMapper();
		
		sessionManager.init();
		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(true);
    }
    
    @Test
    public void verify_allConferences_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ROSTER_ALL_CONFERENCES_URL)
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
		
		verify(leagueListservices, never()).allConferences(apiRequestCaptor.capture());
    }
    
    @Test
    public void verify_allConferences() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

    	ConferenceListResponse mockResponse = new ConferenceListResponse();
    	mockResponse.setConferences(ApcfflTest.buildConferences());
    	when(leagueListservices.allConferences(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ROSTER_ALL_CONFERENCES_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.conferences[0].conferenceAbbr").value(ApcfflTest.CONF_ABBR_BIG_10))
			.andExpect(jsonPath("$.conferences[0].schools[0].schoolName").value(ApcfflTest.SCHOOL_NAME_BIG_10_1))
			.andExpect(jsonPath("$.conferences[0].schools[1].schoolName").value(ApcfflTest.SCHOOL_NAME_BIG_10_2))
			.andExpect(jsonPath("$.conferences[1].conferenceAbbr").value(ApcfflTest.CONF_ABBR_MAC))
			.andExpect(jsonPath("$.conferences[1].schools[0].schoolName").value(ApcfflTest.SCHOOL_NAME_MAC_1))
			.andExpect(jsonPath("$.conferences[1].schools[1].schoolName").value(ApcfflTest.SCHOOL_NAME_MAC_2));
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueListservices, times(1)).allConferences(apiRequestCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, apiRequestCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.TEST_TOKEN, apiRequestCaptor.getValue().getSecurityToken());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, apiRequestCaptor.getValue().getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, apiRequestCaptor.getValue().getUserName());
    }
    
    @Test
    public void verify_teamRoster_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ROSTER_TEAM_ROSTER_URL)
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
		
		verify(leagueServices, never()).teamRoster(apiRequestCaptor.capture());
    }
    
    @Test
    public void verify_teamRoster() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

    	TeamResponse mockResponse = ApcfflTest.buildTeamResponse();
		when(leagueServices.teamRoster(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ROSTER_TEAM_ROSTER_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.team.userName").value(ApcfflTest.USER_NAME))
			.andExpect(jsonPath("$.team.leagueName").value(ApcfflTest.LEAGUE_1_NAME))
			.andExpect(jsonPath("$.team.teamName").value(ApcfflTest.LEAGUE_1_TEAM_1))
			.andExpect(jsonPath("$.team.roster[0].school.schoolName").value(ApcfflTest.SCHOOL_NAME_BIG_10_1))
			.andExpect(jsonPath("$.team.roster[0].scholarshipPoints").value(ApcfflTest.SCHOLARSHIP_POINT_MIN))
			.andExpect(jsonPath("$.team.roster[1].school.schoolName").value(ApcfflTest.SCHOOL_NAME_BIG_10_2))
			.andExpect(jsonPath("$.team.roster[1].scholarshipPoints").value(ApcfflTest.SCHOLARSHIP_POINT_MIN));
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
		verify(leagueServices, times(1)).teamRoster(apiRequestCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, apiRequestCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.TEST_TOKEN, apiRequestCaptor.getValue().getSecurityToken());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, apiRequestCaptor.getValue().getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, apiRequestCaptor.getValue().getUserName());
    }
}
