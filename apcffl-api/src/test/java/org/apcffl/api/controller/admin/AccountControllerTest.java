package org.apcffl.api.controller.admin;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.exception.PersistenceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

	private static final String ACCOUNT_RETRIEVAL_URL = "/api/account/accountRetrieval";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	AdminService service;
	
	@MockBean
	SessionManagerBo sessionManager;
	
	private ObjectMapper objectMapper;
 
    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		
    	objectMapper = new ObjectMapper();
		
		sessionManager.init();
		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(true);
    }
    
    @Test
    public void testAccountRetrievalPersistenceException() throws Exception {
    	
    	// prepare test data
    	
		AccountRequest request = ApcfflTest.buildAccountRequest();

		when(service.accountRetrieval(any())).thenThrow(new PersistenceException("error1"));
		
		// perform the mock REST call
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		mockMvc.perform(
				post(ACCOUNT_RETRIEVAL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    	
    	// verify results
    	
    	verify(service, times(1)).accountRetrieval(any());
    }
    
    @Test
    public void testAccountRetrievalInvalidSessionToken() throws Exception {
    	
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
    	
    	verify(service, times(0)).accountRetrieval(any());
    }
	
	@Test
	public void testAccountRetrieval() throws Exception {
		
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
			.andExpect(status().isOk());
    	
    	// verify results
    	
    	verify(service, times(1)).accountRetrieval(any());
	}
}
