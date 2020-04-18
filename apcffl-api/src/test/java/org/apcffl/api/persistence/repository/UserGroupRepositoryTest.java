package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.persistence.model.UserGroupModel;
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
public class UserGroupRepositoryTest {

	@Autowired
	private UserGroupRepository repository;
	
	@Test
	public void verify_findByUserGroupName_notFound() {
		
		// invoke
		
		UserGroupModel model = repository.findByUserGroupName("invalid");
		
		// verify
		
		assertEquals(null, model);
	}
	
	@Test
	public void verify_findByUserGroupName() {
		
		// invoke
		
		UserGroupModel model = repository.findByUserGroupName(ApcfflTest.USER_GROUP_ADMIN);
		
		// verify
		
		assertEquals(ApcfflTest.USER_GROUP_ADMIN, model.getUserGroupName());
	}
}
