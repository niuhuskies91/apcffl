package org.apcffl.api.controller.league;

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
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.service.manager.SessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = LeagueListController.class)
public class LeagueListControllerTest {
	
	private static final String LEAGUE_LIST_RETRIEVE_ALL_URL = "/league/allLeagues";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	LeagueListServices service;
	
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
		
		verify(service, never()).allLeagues(apiRequestCaptor.capture());
    }
    
    @Test
    public void verify_allLeagues() throws Exception {
    	
    	// prepare test data
    	
    	ApiRequest request = ApcfflTest.buildApiRequest();

    	LeagueListsResponse mockResponse = new LeagueListsResponse();
    	mockResponse.setLeagues(ApcfflTest.buildLeagues());
		when(service.allLeagues(any())).thenReturn(mockResponse);
		
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
		
		verify(service, times(1)).allLeagues(apiRequestCaptor.capture());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, apiRequestCaptor.getValue().getUserGroupName());
    }

}
