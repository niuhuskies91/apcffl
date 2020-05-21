package org.apcffl.api.security.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apcffl.ApcfflTest;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.dto.PasswordResetRequest;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.service.manager.EmailManager;
import org.apcffl.api.service.manager.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class AuthorizationServiceImplTest {
	
	private AuthorizationServiceImpl service;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private OwnerRepository ownerRepository;
	
	@Mock
	private SessionManager sessionManager;
	
	@Mock
	private EmailManager emailManager;

	@Captor
	private ArgumentCaptor<String> userNameCaptor;

	@Captor
	private ArgumentCaptor<String> passwordCaptor;

	@Captor
	private ArgumentCaptor<String> emailAddrCaptor;

	@Captor
	private ArgumentCaptor<Integer> tokenCaptor;
	
	@Captor
	private ArgumentCaptor<UserModel> userCaptor;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		sessionManager.init();
		
		when(sessionManager.generateTokenForUser(anyString())).thenReturn(ApcfflTest.TEST_TOKEN);
		
		service = new AuthorizationServiceImpl(userRepository, ownerRepository, emailManager, sessionManager);
	}

	@Test(expected = org.apcffl.api.exception.SecurityException.class)
	public void verify_login_userNotFound() {
		
		// prepare test data
		
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(null);
		
		// invoke service
		
		service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
	}

	@Test(expected = org.apcffl.api.exception.SecurityException.class)
	public void verify_login_ownerNotFound() {
		
		// prepare test data
		
		UserModel mockUser = ApcfflTest.buildUserModel();
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(mockUser);
		
		when(ownerRepository.findByUserName(anyString())).thenReturn(null);
		
		// invoke service
		
		service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
	}
	
	@Test
	public void verify_login_noTeam() {
		
		// prepare test data
		
		UserModel mockUser = ApcfflTest.buildUserModel();
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(mockUser);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		mockOwner.setTeamModel(null);
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke service
		
		UserDto dto = service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
		
		// verify results
		
		assertEquals(ApcfflTest.USER_GROUP_OWNER, dto.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, dto.getUserName());
		assertEquals(ApcfflTest.TEST_TOKEN, dto.getSecurityToken());
		assertEquals(null, dto.getLeagueName());
		assertEquals(null, dto.getTeamName());
		
		verify(userRepository, times(1))
		.findByUserNamePassword(userNameCaptor.capture(), passwordCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.PASSWORD, passwordCaptor.getValue());
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		
		verify(sessionManager, times(1)).generateTokenForUser(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
	}
	
	@Test
	public void verify_login_leagueNull() {
		
		// prepare test data
		
		UserModel mockUser = ApcfflTest.buildUserModel();
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(mockUser);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		mockOwner.getTeamModel().setLeagueModel(null);
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke service
		
		UserDto dto = service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
		
		// verify results
		
		assertEquals(ApcfflTest.USER_GROUP_OWNER, dto.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, dto.getUserName());
		assertEquals(ApcfflTest.TEST_TOKEN, dto.getSecurityToken());
		assertEquals(null, dto.getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, dto.getTeamName());
		
		verify(userRepository, times(1))
		.findByUserNamePassword(userNameCaptor.capture(), passwordCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.PASSWORD, passwordCaptor.getValue());
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		
		verify(sessionManager, times(1)).generateTokenForUser(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
	}
	
	@Test
	public void verify_login() {
		
		// prepare test data
		
		UserModel mockUser = ApcfflTest.buildUserModel();
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(mockUser);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke service
		
		UserDto dto = service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
		
		// verify results
		
		assertEquals(ApcfflTest.USER_GROUP_OWNER, dto.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, dto.getUserName());
		assertEquals(ApcfflTest.TEST_TOKEN, dto.getSecurityToken());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, dto.getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, dto.getTeamName());
		
		verify(userRepository, times(1))
		.findByUserNamePassword(userNameCaptor.capture(), passwordCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.PASSWORD, passwordCaptor.getValue());
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		
		verify(sessionManager, times(1)).generateTokenForUser(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
	}
	
	@Test(expected = org.apcffl.api.exception.SecurityException.class)
	public void verify_passwordResetToken_exception() {
		
		// prepare test data
		
		OwnerModel mockOwner = null;
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke method
		
		service.passwordResetToken(ApcfflTest.USER_NAME);
	}
	
	@Test
	public void verify_passwordResetToken() {
		
		// prepare test data
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		EmailManager spy = Mockito.spy(emailManager);
		Mockito.doNothing().when(spy).sendEmail(anyString(), anyString(), anyString());
		
		// invoke method
		
		service.passwordResetToken(ApcfflTest.USER_NAME);
		
		// verify
		
		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		
		verify(sessionManager, times(1)).generatePasswordResetToken(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		
		verify(emailManager, times(1)).sendEmail(anyString(), anyString(), anyString());
	}
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_resetPassword_nullUserName() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setUserName(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_resetPassword_emptyUserName() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setUserName("");

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_resetPassword_missingPasswordResetToken() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setPasswordResetToken(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_resetPassword_nullPassword() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setPassword(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_resetPassword_emptyPassword() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setPassword("");

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_resetPassword_noOwnerFound() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	
    	when(ownerRepository.findByUserName(anyString())).thenReturn(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_ResetPassword_invalidToken() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
    	
    	when(sessionManager.isValidPasswordResetToken(anyString(), anyInt())).thenReturn(false);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test
    public void testResetPassword() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
    	
    	when(sessionManager.isValidPasswordResetToken(anyString(), anyInt())).thenReturn(true);
		
    	UserModel mockUser = ApcfflTest.buildUserModel();
		when(userRepository.save(any())).thenReturn(mockUser);

    	// invoke method
    	
    	service.resetPassword(request);
    	
    	verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    	
    	verify(sessionManager, times(1))
    	.isValidPasswordResetToken(userNameCaptor.capture(), tokenCaptor.capture());
    	
    	assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
    	assertEquals(ApcfflTest.TEST_RESET_PSWD_TOKEN, tokenCaptor.getValue());
    	
    	verify(userRepository, times(1)).save(userCaptor.capture());
    	assertEquals(ApcfflTest.USER_NAME, userCaptor.getValue().getUserName());
    	assertEquals(ApcfflTest.PASSWORD, userCaptor.getValue().getPassword());
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_userNameRecovery_nullEmailParam() {
    	
    	service.userNameRecovery(null);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_userNameRecovery_emptyEmailParam() {
    	
    	service.userNameRecovery("");
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_userNameRecovery_noOwnerFoud() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = null;
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_userNameRecovery_noUserFoud() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	mockOwner.setUserModel(null);
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_userNameRecovery_nullUserNameFoud() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	UserModel mockUser = ApcfflTest.buildUserModel();
    	mockOwner.setUserModel(mockUser);
    	mockUser.setUserName(null);
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void verify_userNameRecovery_emptyUserNameFoud() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	UserModel mockUser = ApcfflTest.buildUserModel();
    	mockOwner.setUserModel(mockUser);
    	mockUser.setUserName("");
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
    
    @Test
    public void testUserNameRecovery() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	UserModel mockUser = ApcfflTest.buildUserModel();
    	mockOwner.setUserModel(mockUser);
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);

		
		EmailManager spy = Mockito.spy(emailManager);
		Mockito.doNothing().when(spy).sendEmail(anyString(), anyString(), anyString());
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    	// verify
    	
    	verify(ownerRepository, times(1)).findByEmail(emailAddrCaptor.capture());
    	assertEquals(ApcfflTest.OWNER_EMAIL1, emailAddrCaptor.getValue());
		
		verify(emailManager, times(1)).sendEmail(anyString(), anyString(), anyString());
    	
    }
}
