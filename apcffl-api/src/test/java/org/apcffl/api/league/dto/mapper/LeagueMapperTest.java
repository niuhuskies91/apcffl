package org.apcffl.api.league.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.league.dto.League;
import org.apcffl.api.league.dto.LeagueOwner;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;
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
	public void verify_convertLeagueList_noDivisions() {
		
		// prepare test data
		
		List<LeagueModel> models = ApcfflTest.buildLeagueModels();
		models.get(0).setDivisions(null);
		models.get(1).setDivisions(new LinkedHashSet<DivisionModel>());
		
		// invoke
		
		List<League> result = LeagueMapper.convertLeagueList(models);
		
		// verify
		
		assertEquals(2, result.size());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, result.get(0).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_DIV, result.get(0).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_TEAMS, result.get(0).getNumTeams());
		assertEquals(0, result.get(0).getDivisions().size());
		assertEquals(ApcfflTest.LEAGUE_2_NAME, result.get(1).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_DIV, result.get(1).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_TEAMS, result.get(1).getNumTeams());
		assertEquals(0, result.get(1).getDivisions().size());
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
		assertEquals(ApcfflTest.LEAGUE_1_DIV_1, 
				result.get(0).getDivisions().get(0).getDivisionName());
		assertEquals(ApcfflTest.LEAGUE_1_DIV_2, 
				result.get(0).getDivisions().get(1).getDivisionName());
		assertEquals(ApcfflTest.LEAGUE_2_NAME, result.get(1).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_DIV, result.get(1).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_TEAMS, result.get(1).getNumTeams());
		assertEquals(ApcfflTest.LEAGUE_1_DIV_1, 
				result.get(1).getDivisions().get(0).getDivisionName());
		assertEquals(ApcfflTest.LEAGUE_1_DIV_2, 
				result.get(1).getDivisions().get(1).getDivisionName());
	}
	
	@Test
	public void verify_convertLeagueOwners_null_ownersProvided() {
		
		// prepare test data
		
		List<OwnerModel> owners = null;
		
		// invoke
		
		List<LeagueOwner> result = LeagueMapper.convertLeagueOwners(owners);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_convertLeagueOwners_empty_ownersProvided() {
		
		// prepare test data
		
		List<OwnerModel> owners = Collections.emptyList();
		
		// invoke
		
		List<LeagueOwner> result = LeagueMapper.convertLeagueOwners(owners);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_convertLeagueOwners_no_team_available() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		List<OwnerModel> owners = Arrays.asList(owner);
		
		owner.setTeamModel(null);
		
		// invoke
		
		List<LeagueOwner> result = LeagueMapper.convertLeagueOwners(owners);
		
		// verify
		
		assertEquals(1, result.size());
		assertEquals(true, result.get(0).getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, result.get(0).getEmail());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, result.get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, result.get(0).getLastName());

		assertEquals(null, result.get(0).getTeamName());
		assertEquals(null, result.get(0).getDivisionName());
	}
	
	@Test
	public void verify_convertLeagueOwners_no_division_available() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		List<OwnerModel> owners = Arrays.asList(owner);
		
		owner.getTeamModel().setDivisionModel(null);
		
		// invoke
		
		List<LeagueOwner> result = LeagueMapper.convertLeagueOwners(owners);
		
		// verify
		
		assertEquals(1, result.size());
		assertEquals(true, result.get(0).getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, result.get(0).getEmail());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, result.get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, result.get(0).getLastName());

		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, result.get(0).getTeamName());
		assertEquals(null, result.get(0).getDivisionName());
	}
	
	@Test
	public void verify_convertLeagueOwners() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		List<OwnerModel> owners = Arrays.asList(owner);
		
		// invoke
		
		List<LeagueOwner> result = LeagueMapper.convertLeagueOwners(owners);
		
		// verify
		
		assertEquals(1, result.size());
		assertEquals(true, result.get(0).getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, result.get(0).getEmail());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, result.get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, result.get(0).getLastName());

		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, result.get(0).getTeamName());
		assertEquals(ApcfflTest.LEAGUE_1_DIV_1, result.get(0).getDivisionName());
	}
}
