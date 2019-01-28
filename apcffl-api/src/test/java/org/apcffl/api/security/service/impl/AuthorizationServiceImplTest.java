package org.apcffl.api.security.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.apcffl.ApcfflTest;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.dto.UserDto;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AuthorizationServiceImplTest {
	
	@InjectMocks
	private AuthorizationServiceImpl service;
	
	@Mock
	private UserRepository userRepository;
	
	private SessionManagerBo sessionManager;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sessionManager = new SessionManagerBo();
		sessionManager.init();
		
		service = new AuthorizationServiceImpl(userRepository, sessionManager);
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
		assertNotNull(dto.getSecurityToken());
	}
}
