package org.apcffl.api.security.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.apcffl.ApcfflTest;
import org.apcffl.api.bo.EmailManagerBo;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.dto.PasswordResetRequest;
import org.apcffl.api.security.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class AuthorizationServiceImplTest {
	
	@InjectMocks
	private AuthorizationServiceImpl service;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private OwnerRepository ownerRepository;
	
	@Mock
	private SessionManagerBo sessionManager;
	
	@Mock
	private EmailManagerBo emailManager;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		sessionManager.init();
		
		when(sessionManager.generateTokenForUser(anyString())).thenReturn(ApcfflTest.TEST_TOKEN);
	}

	@Test(expected = org.apcffl.api.exception.SecurityException.class)
	public void testLoginFail() {
		
		// prepare test data
		
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(null);
		
		// invoke service
		
		service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
	}
	
	@Test
	public void testLogin() {
		
		// prepare test data
		
		UserModel mockUser = ApcfflTest.buildUserModel();
		when(userRepository.findByUserNamePassword(anyString(), anyString()))
		.thenReturn(mockUser);
		
		// invoke service
		
		UserDto dto = service.login(ApcfflTest.USER_NAME, ApcfflTest.PASSWORD);
		
		// verify results
		
		assertEquals(dto.getUserGroupName(), ApcfflTest.USER_GROUP_OWNER);
		assertEquals(dto.getUserName(), ApcfflTest.USER_NAME);
		assertEquals(dto.getSecurityToken(), ApcfflTest.TEST_TOKEN);
	}
	
	@Test(expected = org.apcffl.api.exception.SecurityException.class)
	public void testPasswordResetTokenException() {
		
		// prepare test data
		
		OwnerModel mockOwner = null;
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke method
		
		service.passwordResetToken(ApcfflTest.USER_NAME);
	}
	
	@Test
	public void testPasswordResetToken() {
		
		// prepare test data
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		EmailManagerBo spy = Mockito.spy(emailManager);
		Mockito.doNothing().when(spy).sendEmail(anyString(), anyString(), anyString());
		
		// invoke method
		
		service.passwordResetToken(ApcfflTest.USER_NAME);
	}
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordNullUserName() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setUserName(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordEmptyUserName() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setUserName("");

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordMissingPasswordResetToken() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setPasswordResetToken(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordNullPassword() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setPassword(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordEmptyPassword() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	request.setPassword("");

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordNoOwnerFound() {
    	
    	// prepare test data
    	
    	PasswordResetRequest request = ApcfflTest.buildPasswordResetRequest();
    	
    	when(ownerRepository.findByUserName(anyString())).thenReturn(null);

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testResetPasswordInvalidToken() {
    	
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
		
		when(userRepository.save(any())).thenReturn(new UserModel());

    	// invoke method
    	
    	service.resetPassword(request);
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testUserNameRecoveryNullEmailParam() {
    	
    	service.userNameRecovery(null);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testUserNameRecoveryEmptyEmailParam() {
    	
    	service.userNameRecovery("");
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testUserNameRecoveryNoOwnerFoud() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = null;
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testUserNameRecoveryNoUserFoud() {
    	
    	// prepare test data
    	
    	OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
    	mockOwner.setUserModel(null);
    	
    	when(ownerRepository.findByEmail(anyString())).thenReturn(mockOwner);
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
    
    @Test(expected = org.apcffl.api.exception.SecurityException.class)
    public void testUserNameRecoveryNullUserNameFoud() {
    	
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
    public void testUserNameRecoveryEmptyUserNameFoud() {
    	
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

		
		EmailManagerBo spy = Mockito.spy(emailManager);
		Mockito.doNothing().when(spy).sendEmail(anyString(), anyString(), anyString());
    	
    	// invoke method
    	
    	service.userNameRecovery(ApcfflTest.OWNER_EMAIL1);
    	
    }
}
