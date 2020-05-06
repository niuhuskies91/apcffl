package org.apcffl.api.controller.admin;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountCreateRequest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.AllAccountsResponse;
import org.apcffl.api.admin.dto.LeagueAssignmentRequest;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ApiResponse;
import org.apcffl.api.service.manager.SessionManager;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountController.class)
public class AccountControllerTest {

	private static final String ACCOUNT_RETRIEVAL_URL       = "/account/accountRetrieval";
	private static final String ACCOUNT_RETRIEVAL_ALL_URL   = "/account/accountRetrievalAll";
	private static final String ACCOUNT_CREATION_URL        = "/account/accountCreation";
	private static final String ACCOUNT_UPDATE_URL          = "/account/accountUpdate";
	private static final String ACCOUNT_OWNER_LEAGUE_ASSIGN = "/account/ownerLeagueAssignment";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	AdminService service;
	
	@MockBean
	SessionManager sessionManager;
	
	private ObjectMapper objectMapper;
	
	@Captor
	private ArgumentCaptor<String> userNameCaptor;
	
	@Captor
	private ArgumentCaptor<String> tokenCaptor;
	
	@Captor
	private ArgumentCaptor<AccountRequest> accountRequestCaptor;
	
	@Captor
	private ArgumentCaptor<AccountCreateRequest> accountCreateRequestCaptor;
	
	@Captor
	private ArgumentCaptor<LeagueAssignmentRequest> leagueAssignmentCaptor;
 
    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		
    	objectMapper = new ObjectMapper();
		
		sessionManager.init();
		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(true);
    }
    
    @Test
    public void verify_accountRetrieval_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
		AccountRequest request = ApcfflTest.buildAccountRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_RETRIEVAL_URL)
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
    	
    	verify(service, never()).accountRetrieval(accountRequestCaptor.capture());
    }
	
	@Test
	public void verify_AccountRetrieval() throws Exception {
		
		// prepare test data
		
		AccountRequest request = ApcfflTest.buildAccountRequest();
		AccountResponse mockResponse = ApcfflTest.buildAccountResponse();
		
		when(service.accountRetrieval(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_RETRIEVAL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email1").value(ApcfflTest.OWNER_EMAIL1))
			.andExpect(jsonPath("$.email2").value(ApcfflTest.OWNER_EMAIL2))
			.andExpect(jsonPath("$.email3").value(ApcfflTest.OWNER_EMAIL3))
			.andExpect(jsonPath("$.firstName").value(ApcfflTest.OWNER_FIRST_NAME))
			.andExpect(jsonPath("$.lastName").value(ApcfflTest.OWNER_LAST_NAME))
			.andExpect(jsonPath("$.leagueName").value(ApcfflTest.LEAGUE_1_NAME));
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
    	verify(service, times(1)).accountRetrieval(accountRequestCaptor.capture());
    	AccountRequest verifyRequest = accountRequestCaptor.getValue();
    	assertEquals(ApcfflTest.USER_GROUP_OWNER, verifyRequest.getUserGroupName());
    	assertEquals(ApcfflTest.USER_NAME, verifyRequest.getUserName());
    	assertEquals(ApcfflTest.TEST_TOKEN, verifyRequest.getSecurityToken());
	}
    
    @Test
    public void verify_accountRetrievalAll_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
		AccountRequest request = ApcfflTest.buildAccountRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_RETRIEVAL_ALL_URL)
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
    	
    	verify(service, never()).accountRetrievalAll(accountRequestCaptor.capture());
    }
	
	@Test
	public void verify_AccountRetrievalAll() throws Exception {
		
		// prepare test data
		
		AccountRequest request = ApcfflTest.buildAccountRequest();
		AllAccountsResponse mockResponse = ApcfflTest.buildAllAccountsResponse();
		
		when(service.accountRetrievalAll(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_RETRIEVAL_ALL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.accounts[0].email1").value(ApcfflTest.OWNER_EMAIL1))
			.andExpect(jsonPath("$.accounts[0].email2").value(ApcfflTest.OWNER_EMAIL2))
			.andExpect(jsonPath("$.accounts[0].email3").value(ApcfflTest.OWNER_EMAIL3))
			.andExpect(jsonPath("$.accounts[0].firstName").value(ApcfflTest.OWNER_FIRST_NAME))
			.andExpect(jsonPath("$.accounts[0].lastName").value(ApcfflTest.OWNER_LAST_NAME))
			.andExpect(jsonPath("$.accounts[0].leagueName").value(ApcfflTest.LEAGUE_1_NAME));
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
		
    	verify(service, times(1)).accountRetrievalAll(accountRequestCaptor.capture());
    	AccountRequest verifyRequest = accountRequestCaptor.getValue();
    	assertEquals(ApcfflTest.USER_GROUP_OWNER, verifyRequest.getUserGroupName());
    	assertEquals(ApcfflTest.USER_NAME, verifyRequest.getUserName());
    	assertEquals(ApcfflTest.TEST_TOKEN, verifyRequest.getSecurityToken());
	}
	
	@Test
	public void verify_accountCreation() throws Exception {
		
		// prepare test data
		
		AccountCreateRequest request = 
				new AccountCreateRequest(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
		
		when(service.accountCreate(any())).thenReturn(UIMessages.MSG_GENERIC_CHECK_EMAIL);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_CREATION_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").value(UIMessages.MSG_GENERIC_CHECK_EMAIL));
    	
    	// verify results
		
    	verify(service, times(1)).accountCreate(accountCreateRequestCaptor.capture());
    	AccountCreateRequest verifyRequest = accountCreateRequestCaptor.getValue();
    	assertEquals(ApcfflTest.USER_NAME, verifyRequest.getUserName());
    	assertEquals(ApcfflTest.PASSWORD, verifyRequest.getPassword());
	}
	
	@Test
	public void verify_accountUpdate_invalidSessionToken() throws Exception {
		
		// prepare test data
		
		AccountRequest request = ApcfflTest.buildAccountRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		AccountResponse mockResponse = ApcfflTest.buildAccountResponse();
		when(service.accountUpdate(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_UPDATE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    	// verify results
		
    	verify(service, never()).accountUpdate(accountRequestCaptor.capture());
	}
	
	@Test
	public void verify_accountUpdate() throws Exception {
		
		// prepare test data
		
		AccountRequest request = ApcfflTest.buildAccountRequest();
		
		AccountResponse mockResponse = ApcfflTest.buildAccountResponse();
		when(service.accountUpdate(any())).thenReturn(mockResponse);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_UPDATE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.email1").value(ApcfflTest.OWNER_EMAIL1))
			.andExpect(jsonPath("$.email2").value(ApcfflTest.OWNER_EMAIL2))
			.andExpect(jsonPath("$.email3").value(ApcfflTest.OWNER_EMAIL3))
			.andExpect(jsonPath("$.firstName").value(ApcfflTest.OWNER_FIRST_NAME))
			.andExpect(jsonPath("$.lastName").value(ApcfflTest.OWNER_LAST_NAME))
			.andExpect(jsonPath("$.leagueName").value(ApcfflTest.LEAGUE_1_NAME));
    	
    	// verify results
		
    	verify(service, times(1)).accountUpdate(accountRequestCaptor.capture());
    	AccountRequest verifyRequest = accountRequestCaptor.getValue();
    	assertEquals(ApcfflTest.USER_GROUP_OWNER, verifyRequest.getUserGroupName());
    	assertEquals(ApcfflTest.USER_NAME, verifyRequest.getUserName());
    	assertEquals(ApcfflTest.TEST_TOKEN, verifyRequest.getSecurityToken());
	}
    
    @Test
    public void verify_ownerLeagueAssignment_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	LeagueAssignmentRequest request = ApcfflTest.buildLeagueAssignmentRequest();

		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_OWNER_LEAGUE_ASSIGN)
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
    	
    	verify(service, never()).ownerLeagueAssignment(leagueAssignmentCaptor.capture());
    }
    
    @Test
    public void verify_ownerLeagueAssignment() throws Exception {
    	
    	// prepare test data
    	
    	LeagueAssignmentRequest request = ApcfflTest.buildLeagueAssignmentRequest();
    	
    	when(service.ownerLeagueAssignment(any())).thenReturn(new ApiResponse());
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_OWNER_LEAGUE_ASSIGN)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
    	
    	// verify results
    	
		verify(sessionManager, times(1))
		.isValidSessionToken(userNameCaptor.capture(), tokenCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.TEST_TOKEN, tokenCaptor.getValue());
    	
    	verify(service, times(1)).ownerLeagueAssignment(leagueAssignmentCaptor.capture());
    	LeagueAssignmentRequest leagueCaptorValue = leagueAssignmentCaptor.getValue();
    	assertEquals(ApcfflTest.TEST_TOKEN, leagueCaptorValue.getSecurityToken());
    	assertEquals(ApcfflTest.USER_GROUP_ADMIN, leagueCaptorValue.getUserGroupName());
    	assertEquals(ApcfflTest.USER_NAME, leagueCaptorValue.getUserName());
    	assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueCaptorValue.getOwnerLeagueName());
    	assertEquals(ApcfflTest.USER_GUEST_NAME, leagueCaptorValue.getOwnerUserName());
    }
}