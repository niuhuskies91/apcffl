package org.apcffl.api.controller.security;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times; 
import static org.mockito.Mockito.verify; 
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apcffl.ApcfflTest;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.exception.EmailException;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.security.dto.AuthorizationRequest;
import org.apcffl.api.security.dto.PasswordResetRequest;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.security.service.AuthorizationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
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
	
	private static final String LOGIN_URL              = "/security/login";
	private static final String PSWD_RESET_TOKEN_URL   = "/security/passwordResetToken/userName/{userName}";
	private static final String PSWD_RESET_URL         = "/security/passwordReset";
	private static final String USER_NAME_RECOVERY_URL = "/security/userNameRecovery/email/{email}";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorizationService service;
	
	private ObjectMapper objectMapper;
	
	@Captor
	private ArgumentCaptor<String> userNameCaptor;
	
	@Captor
	private ArgumentCaptor<String> passwordCaptor;
	
	@Captor
	private ArgumentCaptor<PasswordResetRequest> pswdResetReqCaptor;
 
    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		
    	objectMapper = new ObjectMapper();
    }
    
    @Test
    public void verify_login_failure() throws Exception {
    	
    	// prepare test data
    	
    	when(service.login(anyString(), anyString()))
    	.thenThrow(new SecurityException("error1"));
    	
    	AuthorizationRequest request = new AuthorizationRequest();
    	request.setUserName(ApcfflTest.USER_NAME);
    	request.setPassword(ApcfflTest.PASSWORD);
		String jsonRequest = objectMapper.writeValueAsString(request);
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			post(LOGIN_URL)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    	// verify results
    	
    	verify(service, times(1)).login(userNameCaptor.capture(), passwordCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    	assertEquals(ApcfflTest.PASSWORD, passwordCaptor.getValue());
    }
    
    @Test
    public void verify_login() throws Exception {
    	
    	// prepare test data
    	
    	UserDto mockDto = 
    			new UserDto(ApcfflTest.USER_NAME, ApcfflTest.USER_GROUP_ADMIN, ApcfflTest.TEST_TOKEN);
    	
    	when(service.login(anyString(), anyString()))
    	.thenReturn(mockDto);
    	
    	AuthorizationRequest request = new AuthorizationRequest();
    	request.setUserName(ApcfflTest.USER_NAME);
    	request.setPassword(ApcfflTest.PASSWORD);
		String jsonRequest = objectMapper.writeValueAsString(request);
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			post(LOGIN_URL)
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.userName").value(ApcfflTest.USER_NAME))
			.andExpect(jsonPath("$.userGroupName").value(ApcfflTest.USER_GROUP_ADMIN))
			.andExpect(jsonPath("$.securityToken").value(ApcfflTest.TEST_TOKEN));
    	
    	// verify results

    	verify(service, times(1)).login(userNameCaptor.capture(), passwordCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    	assertEquals(ApcfflTest.PASSWORD, passwordCaptor.getValue());
    }
    
    @Test
    public void verify_passwordResetToken_emailException() throws Exception {
    	
    	// prepare test data
    	
    	Mockito.doThrow(new EmailException("error"))
    	.when(service).passwordResetToken(anyString());
    	
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_TOKEN_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isInternalServerError());
    	
    	// verify results
    	
    	verify(service, times(1)).passwordResetToken(userNameCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    	
    }
    
    @Test
    public void verify_passwordResetToken_failure() throws Exception {
    	
    	// prepare test data
    	
    	Mockito.doThrow(new SecurityException("error"))
    	.when(service).passwordResetToken(anyString());
    	
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_TOKEN_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isUnauthorized());
    	
    	// verify results
    	
    	verify(service, times(1)).passwordResetToken(userNameCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    }
    
    @Test
    public void verify_passwordResetToken() throws Exception {
    	
    	// prepare test data
    	
    	AuthorizationService spy = Mockito.spy(service);
    	Mockito.doNothing().when(spy).passwordResetToken(anyString());
    	
    	// perform the mock REST call
    	
    	mockMvc.perform(
    			get(PSWD_RESET_TOKEN_URL, ApcfflTest.USER_NAME)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(UIMessages.MSG_GENERIC_CHECK_EMAIL));
    	
    	// verify results
    	
    	verify(service, times(1)).passwordResetToken(userNameCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    }
    
    @Test
    public void verify_userNameRecovery_securityException() throws Exception {
    	
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
    	
    	// verify results
    	
    	verify(service, times(1)).userNameRecovery(anyString());
    }
    
    @Test
    public void verify_userNameRecovery_emailException() throws Exception {
    	
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
    	
    	// verify results
    	
    	verify(service, times(1)).userNameRecovery(anyString());
    }
    
    @Test
    public void verify_userNameRecovery() throws Exception {
    	
    	// prepare test data

    	String email = ApcfflTest.OWNER_EMAIL1;
    	
    	AuthorizationService spy = Mockito.spy(service);
    	Mockito.doNothing().when(spy).userNameRecovery(anyString());
    	
    	// perform the mock rest call
    	
    	mockMvc.perform(
    			get(USER_NAME_RECOVERY_URL, email)
				.accept(MediaType.APPLICATION_JSON))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(UIMessages.MSG_GENERIC_CHECK_EMAIL));
    	
    	// verify results
    	
    	verify(service, times(1)).userNameRecovery(anyString());
    }
    
    @Test
    public void verify_passwordReset_invalidTokenFailure() throws Exception {
    	
    	// prepare test data
    	
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
    	
    	// verify results
    	
    	verify(service, times(1)).resetPassword(pswdResetReqCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, pswdResetReqCaptor.getValue().getUserName());
    	assertEquals(ApcfflTest.PASSWORD, pswdResetReqCaptor.getValue().getPassword());
    	assertEquals(ApcfflTest.TEST_RESET_PSWD_TOKEN, pswdResetReqCaptor.getValue().getPasswordResetToken());
    }
    
    @Test
    public void verify_passwordReset() throws Exception {
    	
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
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.message").value(UIMessages.MSG_PSWD_RESET_SUCCESS));
    	
    	// verify results
    	
    	verify(service, times(1)).resetPassword(pswdResetReqCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, pswdResetReqCaptor.getValue().getUserName());
    	assertEquals(ApcfflTest.PASSWORD, pswdResetReqCaptor.getValue().getPassword());
    	assertEquals(ApcfflTest.TEST_RESET_PSWD_TOKEN, pswdResetReqCaptor.getValue().getPasswordResetToken());
    }
}
