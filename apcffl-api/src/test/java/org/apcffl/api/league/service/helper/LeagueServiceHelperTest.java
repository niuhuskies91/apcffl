package org.apcffl.api.league.service.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.LeagueError;
import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.Team;
import org.apcffl.api.persistence.model.DivisionModel;
import org.junit.Test;

public class LeagueServiceHelperTest {
	
	@Test
	public void verify_validateDivisionNames_nullDivision() {
		
		// prepare test data
		
		Map<String,DivisionModel>  divisionByName = 
				ApcfflTest.buildDivisionModels().stream().
				collect(Collectors.toMap(DivisionModel::getDivisionName, d -> d));
		
		Team team = ApcfflTest.buildTeam();
		List<Team> teams = Arrays.asList(team);
		
		team.setDivisionName(null);
		
		// invoke
		
		ErrorDto result = LeagueServiceHelper.validateDivisionNames(teams, divisionByName);
		
		// verify
		
		assertEquals(null, result);
	}
	
	@Test
	public void verify_validateDivisionNames_invalidDivision() {
		
		// prepare test data
		
		Map<String,DivisionModel>  divisionByName = 
				ApcfflTest.buildDivisionModels().stream().
				collect(Collectors.toMap(DivisionModel::getDivisionName, d -> d));
		
		Team team = ApcfflTest.buildTeam();
		List<Team> teams = Arrays.asList(team);
		
		team.setDivisionName("invalid");
		
		// invoke
		
		ErrorDto result = LeagueServiceHelper.validateDivisionNames(teams, divisionByName);
		
		// verify
		
		assertEquals(LeagueError.toString(), result.getErrorCode());
		assertEquals(UIMessages.LEAGUE_DIVISION_NAME_NOT_MATCH, result.getMessage());
	}
	
	@Test
	public void verify_validateDivisionNames_tooManyTeamsInDivision() {
		
		// prepare test data
		
		Map<String,DivisionModel>  divisionByName = 
				ApcfflTest.buildDivisionModels().stream().
				collect(Collectors.toMap(DivisionModel::getDivisionName, d -> d));
		

		List<Team> teams = new ArrayList<Team>();
		
		Team team = ApcfflTest.buildTeam();
		teams.add(team);
		teams.add(team);
		teams.add(team);
		teams.add(team);
		
		team = ApcfflTest.buildTeam();
		team.setDivisionName(ApcfflTest.LEAGUE_1_DIV_2);
		teams.add(team);
		teams.add(team);
		teams.add(team);
		teams.add(team);
		teams.add(team);
		
		// invoke
		
		ErrorDto result = LeagueServiceHelper.validateDivisionNames(teams, divisionByName);
		
		// verify
		
		assertEquals(LeagueError.toString(), result.getErrorCode());
		assertEquals(UIMessages.LEAGUE_DIVISION_EXCEED_TEAM_COUNT, result.getMessage());
	}
	
	@Test
	public void verify_validateDivisionNames() {
		
		// prepare test data
		
		Map<String,DivisionModel>  divisionByName = 
				ApcfflTest.buildDivisionModels().stream().
				collect(Collectors.toMap(DivisionModel::getDivisionName, d -> d));
		
		Team team = ApcfflTest.buildTeam();
		List<Team> teams = Arrays.asList(team);
		
		// invoke
		
		ErrorDto result = LeagueServiceHelper.validateDivisionNames(teams, divisionByName);
		
		// verify
		
		assertEquals(null, result);
	}
}
