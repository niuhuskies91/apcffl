package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.constants.ApcfflConstants;
import org.apcffl.api.persistence.model.ConferenceModel;
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
public class ConferenceRepositoryTest {

	@Autowired
	private ConferenceRepository repository;
	
	@Test
	public void verify_findByConferenceType_notFound() {
		
		// invoke
		
		List<ConferenceModel> conference = repository.findByConferenceType("invalid");
		
		// verify
		
		assertEquals(0, conference.size());
	}
	
	@Test
	public void verify_findByConferenceType() {
		
		// invoke
		
		List<ConferenceModel> conference = 
				repository.findByConferenceType(ApcfflConstants.NCAA_CONFERENCE_FBS);
		
		// verify
		
		assertTrue(conference.size() > 5);
	}
}
