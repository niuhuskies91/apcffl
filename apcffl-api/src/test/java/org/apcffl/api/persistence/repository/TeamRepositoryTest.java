package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.persistence.model.TeamModel;
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
public class TeamRepositoryTest {

	@Autowired
	private TeamRepository repository;
	
	@Test
	public void verify_findByLeagueName_notFound() {
		
		// invoke
		
		List<TeamModel> team = repository.findByLeagueName("invalid");
		
		// verify
		
		assertEquals(0, team.size());
	}
	
	@Test
	public void verify_findByLeagueName() {
		
		// invoke
		
		List<TeamModel> team = repository.findByLeagueName(ApcfflTest.LEAGUE_1_NAME);
		
		// verify
		
		assertTrue(team.size() > 0);
	}
}
