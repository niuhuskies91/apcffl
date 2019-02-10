package org.apcffl.api.controller.admin;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
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
	
	private ObjectMapper objectMapper;
 
    @Before
    public void setUp() {
    	objectMapper = new ObjectMapper();
    }
	
	@Test
	public void testAccountRetrieval() throws Exception {
		
		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setSecurityToken(ApcfflTest.TEST_TOKEN);
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		request.setUserName(ApcfflTest.USER_NAME);
		
		AccountResponse mockResponse = new AccountResponse();
		mockResponse.setEmail1(ApcfflTest.OWNER_EMAIL1);
		mockResponse.setEmail2(ApcfflTest.OWNER_EMAIL2);
		mockResponse.setEmail3(ApcfflTest.OWNER_EMAIL3);
		mockResponse.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		mockResponse.setLastName(ApcfflTest.OWNER_LAST_NAME);
		mockResponse.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		
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
