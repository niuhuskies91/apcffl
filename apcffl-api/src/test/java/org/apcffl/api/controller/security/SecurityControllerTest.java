package org.apcffl.api.controller.security;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times; 
import static org.mockito.Mockito.verify; 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apcffl.ApcfflTest;
import org.apcffl.api.exception.EmailException;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.security.dto.PasswordResetRequest;
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

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = SecurityController.class)
public class SecurityControllerTest {
	
	private static final String LOGIN_URL              = "/api/security/login/userName/{userName}/password/{password}";
	private static final String PSWD_RESET_TOKEN_URL   = "/api/security/passwordResetToken/userName/{userName}";
	private static final String PSWD_RESET_URL         = "/api/security/passwordReset";
	private static final String USER_NAME_RECOVERY_URL = "/api/security/userNameRecovery/email/{email}";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorizationService service;
	
	private ObjectMapper objectMapper;
 
    @Before
    public void setUp() {
    	objectMapper = new ObjectMapper();
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
    public void testPasswordResetTokenEmailException() throws Exception {
    	
    	// build the mock service response
    	
    	Mockito.doThrow(new EmailException("error"))
    	.when(service).passwordResetToken(anyString());
    	
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_TOKEN_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    	
    }
    
    @Test
    public void testPasswordResetTokenFailure() throws Exception {
    	
    	// build the mock service response
    	
    	Mockito.doThrow(new SecurityException("error"))
    	.when(service).passwordResetToken(anyString());
    	
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_TOKEN_URL, ApcfflTest.USER_NAME)
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
    			get(PSWD_RESET_TOKEN_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
    }
    
    @Test
    public void testPasswordResetInvalidTokenFailure() throws Exception {
    	
    	// build the mock service response
    	
    	Mockito.doThrow(new SecurityException("error"))
    	.when(service).resetPassword(any());
    	
    	
    	// perform the mock REST call
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	
    	String json = objectMapper.writeValueAsString(request);
    	
    	mockMvc.perform(
    			post(PSWD_RESET_URL)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(json)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    }
    
    @Test
    public void testPasswordReset() throws Exception {
    	
    	// build the mock service response
    	
    	AuthorizationService spy = Mockito.spy(service);
    	Mockito.doNothing().when(spy).resetPassword(any());
    	
    	// perform the mock REST call
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	
    	String json = objectMapper.writeValueAsString(request);
    	
    	mockMvc.perform(
    			post(PSWD_RESET_URL)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(json)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
    }
    
    @Test
    public void testUserNameRecoverySecurityException() throws Exception {
    	
    	// prepare test data

    	String email = ApcfflTest.OWNER_EMAIL1;
    	
    	Mockito.doThrow(new SecurityException("error"))
    	.when(service).userNameRecovery(anyString());
    	
    	// perform the mock rest call
    	
    	mockMvc.perform(
    			get(USER_NAME_RECOVERY_URL, email)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    }
    
    @Test
    public void testUserNameRecoveryEmailException() throws Exception {
    	
    	// prepare test data

    	String email = ApcfflTest.OWNER_EMAIL1;
    	
    	Mockito.doThrow(new EmailException("error"))
    	.when(service).userNameRecovery(anyString());
    	
    	// perform the mock rest call
    	
    	mockMvc.perform(
    			get(USER_NAME_RECOVERY_URL, email)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    	
    }
    
    @Test
    public void testUserNameRecovery() throws Exception {
    	
    	// prepare test data

    	String email = ApcfflTest.OWNER_EMAIL1;
    	
    	AuthorizationService spy = Mockito.spy(service);
    	Mockito.doNothing().when(spy).userNameRecovery(anyString());
    	
    	// perform the mock rest call
    	
    	mockMvc.perform(
    			get(USER_NAME_RECOVERY_URL, email)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk());
    	
    }
}
