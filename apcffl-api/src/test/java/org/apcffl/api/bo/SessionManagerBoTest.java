package org.apcffl.api.bo;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Base64;
import java.util.List;

import javax.annotation.Resource;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.ApiServiceConfig;
import org.apcffl.api.persistence.model.ConfigModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class SessionManagerBoTest {
	
	@Mock
	private ApiServiceConfig config;

	@InjectMocks
	@Resource
	private SessionManagerBo bo;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		bo.init();
		
		List<ConfigModel> mockConfig = ApcfflTest.buildConfigModel();
		when(config.retrieveProperty(anyString())).thenReturn(mockConfig.get(0));
		
		Logger root = (Logger) LoggerFactory.getLogger(SessionManagerBo.class);
		root.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testGenerateTokenForUser() {
		
		String token = bo.generateTokenForUser(ApcfflTest.USER_NAME);
		
		assertTrue(bo.isValidSessionToken(ApcfflTest.USER_NAME, token));
		assertFalse(bo.isValidSessionToken(ApcfflTest.USER_NAME, "invalid"));
		assertFalse(bo.isValidSessionToken("invalid", token));
	}
	
	@Test
	public void testGeneratePasswordToken() {
		
		int token = bo.generatePasswordResetToken(ApcfflTest.USER_NAME);
		
		assertTrue(bo.isValidPasswordResetToken(ApcfflTest.USER_NAME, token));
		assertFalse(bo.isValidPasswordResetToken(ApcfflTest.USER_NAME, 0));
		assertFalse(bo.isValidPasswordResetToken("invalid", token));
	}
	
	@Test
	public void testIsValidSessionTokenException() {
		
		// prepare test data
		
		when(config.retrieveProperty(anyString())).thenThrow(new NullPointerException("error"));

		String token = bo.generateTokenForUser(ApcfflTest.USER_NAME);
		
		// invoke method
		
		boolean flag = bo.isValidSessionToken(ApcfflTest.USER_NAME, token);
		
		// verify results
		
		assertEquals(flag, false);
	}
	
	@Test
	public void testIsValidPasswordResetTokenException() {
		
		// prepare test data
		
		when(config.retrieveProperty(anyString())).thenThrow(new NullPointerException("error"));

		int token = bo.generatePasswordResetToken(ApcfflTest.USER_NAME);
		
		// invoke method
		
		boolean flag = bo.isValidPasswordResetToken(ApcfflTest.USER_NAME, token);
		
		// verify results
		
		assertEquals(flag, false);
	}
}
