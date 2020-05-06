package org.apcffl.api.persistence.repository;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.apcffl.api.config.RepositoryConfig;
import org.apcffl.api.persistence.model.LeagueModel;
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
public class LeagueRepositoryTest {
	
	@Autowired
	private LeagueRepository repository;
	
	@Test
	public void verify_findByLeagueName_notFound() {
		
		// invoke
		
		LeagueModel league = repository.findByLeagueName("invalid");
		
		// verify
		
		assertEquals(null, league);
	}
	
	@Test
	public void verify_findByLeagueName() {
		
		// invoke
		
		LeagueModel league = repository.findByLeagueName(ApcfflTest.LEAGUE_1_NAME);
		assertEquals(ApcfflTest.LEAGUE_1_NAME.toUpperCase(), league.getLeagueName().toUpperCase());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_DIV, league.getNumberOfDivisions());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_TEAMS, league.getNumberOfTeams());
	}

}
