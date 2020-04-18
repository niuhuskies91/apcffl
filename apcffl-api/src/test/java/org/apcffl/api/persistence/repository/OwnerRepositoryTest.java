package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.persistence.model.OwnerModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = {RepositoryConfig.class})
@ActiveProfiles("local")
public class OwnerRepositoryTest {

	@Autowired
	private OwnerRepository repository;
	
	@Test
	public void verify_findByUserName_notFound() {
		
		// invoke
		
		OwnerModel owner = repository.findByUserName("invalid");
		
		// verify
		
		assertEquals(null, owner);
	}
	
	@Test
	public void verify_findByUserName() {
		
		// invoke
		
		OwnerModel owner = repository.findByUserName(ApcfflTest.USER_NAME);
		
		// verify
		
		assertEquals(true, owner.getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, owner.getEmail1());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, owner.getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, owner.getLastName());
	}
	
	@Test
	public void verify_findByEmail_notFound() {
		
		// invoke
		
		OwnerModel owner = repository.findByEmail("invalid");
		
		// verify
		
		assertEquals(null, owner);
	}
	
	@Test
	public void verify_findByEmail() {
		
		// invoke
		
		OwnerModel owner = repository.findByEmail(ApcfflTest.OWNER_EMAIL1);
		
		// verify
		
		assertEquals(true, owner.getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, owner.getEmail1());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, owner.getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, owner.getLastName());
	}
}
