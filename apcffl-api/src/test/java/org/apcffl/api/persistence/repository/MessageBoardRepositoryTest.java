package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.persistence.model.MessageBoardModel;
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
public class MessageBoardRepositoryTest {

	@Autowired
	private MessageBoardRepository repository;
	
	@Test
	public void verify_findByDateRange() {
		
		// invoke
		
		List<MessageBoardModel> result = repository.findByDateRange(null, null, "invalid");
		
		// verify
		
		assertNotNull(result);
	}
}
