package org.apcffl.api.league.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.league.dto.League;
import org.apcffl.api.persistence.model.LeagueModel;
import org.junit.Test;

public class LeagueMapperTest {

	@Test
	public void verify_convertLeagueList_nullLeagueModels() {
		
		// prepare test data
		
		List<LeagueModel> models = null;
		
		// invoke
		
		List<League> result = LeagueMapper.convertLeagueList(models);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_convertLeagueList_emptyLeagueModels() {
		
		// prepare test data
		
		List<LeagueModel> models = Collections.emptyList();
		
		// invoke
		
		List<League> result = LeagueMapper.convertLeagueList(models);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_convertLeagueList() {
		
		// prepare test data
		
		List<LeagueModel> models = ApcfflTest.buildLeagueModels();
		
		// invoke
		
		List<League> result = LeagueMapper.convertLeagueList(models);
		
		// verify
		
		assertEquals(2, result.size());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, result.get(0).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_DIV, result.get(0).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_TEAMS, result.get(0).getNumTeams());
		assertEquals(ApcfflTest.LEAGUE_2_NAME, result.get(1).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_DIV, result.get(1).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_TEAMS, result.get(1).getNumTeams());
	}
}
