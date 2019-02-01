package org.apcffl.api.controller.security;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times; 
import static org.mockito.Mockito.verify; 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apcffl.ApcfflTest;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.security.service.AuthorizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SecurityController.class)
public class SecurityControllerTest {
	
	private static final String LOGIN_URL      = "/api/security/login/userName/{userName}/password/{password}";
	private static final String PSWD_RESET_URL = "/api/security/passwordResetToken/userName/{userName}";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorizationService service;
 
    @Before
    public void setUp() {
    }
    
    @Test
    public void testLoginFailure() throws Exception {
    	
    	// build the mock service response
    	
    	when(service.login(anyString(), anyString()))
    	.thenThrow(new SecurityException("error1"));
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(LOGIN_URL, ApcfflTest.USER_NAME, ApcfflTest.PASSWORD)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    }
    
    @Test
    public void testLogin() throws Exception {
    	
    	// build the mock service response
    	
    	UserDto mockDto = 
    			new UserDto(ApcfflTest.USER_NAME, ApcfflTest.USER_GROUP_ADMIN, ApcfflTest.TEST_TOKEN);
    	
    	when(service.login(anyString(), anyString()))
    	.thenReturn(mockDto);
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(LOGIN_URL, ApcfflTest.USER_NAME, ApcfflTest.PASSWORD)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
    	
    	// verify results
    	
    	verify(service, times(1)).login(anyString(), anyString());
    }
    
    @Test
    public void testPasswordResetTokenFailure() throws Exception {
    	
    	// build the mock service response
    	
    	Mockito.doThrow(new SecurityException("error"))
    	.when(service).passwordResetToken(anyString());
    	
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    }
    
    @Test
    public void testPasswordResetToken() throws Exception {
    	
    	// build the mock service response
    	
    	AuthorizationService spy = Mockito.spy(service);
    	Mockito.doNothing().when(spy).passwordResetToken(anyString());
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
    }
}
