package org.apcffl.api.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmailConfig.class})
@ActiveProfiles("local")
public class EmailConfigTest {

	@Autowired
	private EmailConfig config;

	@Test
	public void verify_propertyInjection() {
		
		assertNotNull(config.getEmailHost());
		assertNotNull(config.getEmailPassword());
		assertNotNull(config.getEmailPort());
		assertNotNull(config.getEmailUser());
		assertNotNull(config.getJavaMailSender());
	}
}
