package org.apcffl.api.service.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.GeneralPropertiesConfig;
import org.apcffl.api.service.manager.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {GeneralPropertiesConfig.class})
@ActiveProfiles("local")
public class SessionManagerTest {
	
	@Autowired
	private GeneralPropertiesConfig config;

	private SessionManager manager;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		manager = new SessionManager(config);
		
		manager.init();
		
		Logger root = (Logger) LoggerFactory.getLogger(SessionManager.class);
		root.setLevel(Level.DEBUG);
	}
	
	@Test
	public void verify_generatePasswordToken() {
		
		int token = manager.generatePasswordResetToken(ApcfflTest.USER_NAME);
		
		assertTrue(manager.isValidPasswordResetToken(ApcfflTest.USER_NAME, token));
		assertFalse(manager.isValidPasswordResetToken(ApcfflTest.USER_NAME, 0));
		assertFalse(manager.isValidPasswordResetToken("invalid", token));
	}
	
	@Test
	public void verify_generateTokenForUser() {
		
		String token = manager.generateTokenForUser(ApcfflTest.USER_NAME);
		
		assertTrue(manager.isValidSessionToken(ApcfflTest.USER_NAME, token));
		assertFalse(manager.isValidSessionToken(ApcfflTest.USER_NAME, "invalid"));
		assertFalse(manager.isValidSessionToken("invalid", token));
	}
	
	@Test
	public void verify_isValidSessionToken_expired() throws InterruptedException {
		
		// prepare test data
		
		Long originalExp = config.getSecurityTokenExp();

		String token = manager.generateTokenForUser(ApcfflTest.USER_NAME);
		
		config.setSecurityTokenExp(100L);
		Thread.sleep(100);
		
		// invoke method
		
		boolean flag = manager.isValidSessionToken(ApcfflTest.USER_NAME, token);
		
		// verify results
		
		config.setSecurityTokenExp(originalExp);
		
		assertEquals(false, flag);
	}
	
	@Test
	public void verify_isValidPasswordResetToken_expired() throws InterruptedException {
		
		// prepare test data
		
		Long originalExp = config.getSecurityPassResetTokenExp();

		int token = manager.generatePasswordResetToken(ApcfflTest.USER_NAME);
		
		config.setSecurityPassResetTokenExp(100L);
		Thread.sleep(100);
		
		// invoke method
		
		boolean flag = manager.isValidPasswordResetToken(ApcfflTest.USER_NAME, token);
		
		// verify results
		
		config.setSecurityPassResetTokenExp(originalExp);
		
		assertEquals(false, flag);
	}
}
