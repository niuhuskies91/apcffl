package org.apcffl.api.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailConfigTest {
	
	private EmailConfig config;
	
	@Before
	public void setUp() {
		config = new EmailConfig();
	}

	@Test
	public void testEmailSender() {
		
		JavaMailSender result = config.getMailSender();
		
		assertNotNull(result);
	}
}
