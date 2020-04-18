package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.UserRepository;
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
public class UserRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	@Test
	public void verify_findByUserName_notFoud() {
		
		// invoke
		
		UserModel user = repository.findByUserName("invalid");
		
		// verify
		
		assertEquals(null, user);
	}
	
	@Test
	public void verify_findByUserName() {
		
		// invoke
		
		UserModel user = repository.findByUserName(ApcfflTest.USER_GUEST_NAME);
		
		// verify
		
		assertEquals(ApcfflTest.USER_GUEST_NAME, user.getUserName());
		assertEquals(ApcfflTest.USER_GUEST_PASSWORD, user.getPassword());
		assertEquals(ApcfflTest.USER_GUEST_USER_GROUP_NAME, user.getUserGroupModel().getUserGroupName());
	}
	
	
	
	

	
	@Test
	public void verify_findByUserNamePassword_userName_notFoud() {
		
		// invoke
		
		UserModel user = repository.findByUserNamePassword("invalid", ApcfflTest.USER_GUEST_PASSWORD);
		
		// verify
		
		assertEquals(null, user);
	}
	
	@Test
	public void verify_findByUserNamePassword_password_noMatch() {
		
		// invoke
		
		UserModel user = repository.findByUserNamePassword(ApcfflTest.USER_GUEST_NAME, "invalid");
		
		// verify
		
		assertEquals(null, user);
	}
	
	@Test
	public void verify_findByUserNamePassword() {
		
		// invoke
		
		UserModel user = 
				repository.findByUserNamePassword(ApcfflTest.USER_GUEST_NAME, ApcfflTest.USER_GUEST_PASSWORD);
		
		// verify
		
		assertEquals(ApcfflTest.USER_GUEST_NAME, user.getUserName());
		assertEquals(ApcfflTest.USER_GUEST_PASSWORD, user.getPassword());
		assertEquals(ApcfflTest.USER_GUEST_USER_GROUP_NAME, user.getUserGroupModel().getUserGroupName());
	}

}
