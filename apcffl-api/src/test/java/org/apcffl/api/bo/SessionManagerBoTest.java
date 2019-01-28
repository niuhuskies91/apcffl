package org.apcffl.api.bo;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class SessionManagerBoTest {

	private SessionManagerBo bo;
	
	@Before
	public void setUp() {
		bo = new SessionManagerBo();
		bo.init();
		
		Logger root = (Logger) LoggerFactory.getLogger(SessionManagerBo.class);
		root.setLevel(Level.DEBUG);
	}
	
	@Test
	public void testGenerateTokenForUser() {
		
		String token = bo.generateTokenForUser(ApcfflTest.USER_NAME);
		
		assertTrue(bo.isValidToken(ApcfflTest.USER_NAME, token));
		assertFalse(bo.isValidToken(ApcfflTest.USER_NAME, "invalid"));
		assertFalse(bo.isValidToken("invalid", token));
	}
}
