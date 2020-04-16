package org.apcffl.api.config;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GeneralPropertiesConfig.class})
@ActiveProfiles("local")
public class GeneralPropertiesConfigTest {

	@Autowired
	private GeneralPropertiesConfig config;
	
	@Test
	public void verify_propertyInjection() {
		assertNotNull(config.getSecurityPassResetTokenExp());
		assertTrue(config.getSecurityPassResetTokenExp() > 0);
		
		assertNotNull(config.getSecurityTokenExp());
		assertTrue(config.getSecurityTokenExp() > 0);
	}
}
