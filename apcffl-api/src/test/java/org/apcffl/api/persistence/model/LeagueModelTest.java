package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class LeagueModelTest {

	private LeagueModel model;
	
	@Before
	public void setUp() {
		model = ApcfflTest.buildLeagueModel(
				ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, 
				ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV);
	}
	
	@Test
	public void verify_equals_otherObjectNull() {
		LeagueModel other = null;
		
		assertEquals(false, model.equals(other));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void verify_equals_notEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(false, model.equals(other));
	}
	
	@Test
	public void verify_equals_leagueIdNotEquals() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_2_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(false, model.equals(other));
		
		model.setLeagueId(null);
		other.setLeagueId(ApcfflTest.LEAGUE_2_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(false, model.equals(other));
		
		model.setLeagueId(null);
		other.setLeagueId(null);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
	}
	
	@Test
	public void verify_equals_leagueNameNotEqual() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_2_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		model.setLeagueName(null);
		other.setLeagueName(ApcfflTest.LEAGUE_2_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		model.setLeagueName(null);
		other.setLeagueName(null);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
	}
	
	@Test
	public void verify_equals_numTeamsNotEqual() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(6);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		model.setNumberOfTeams(null);
		other.setNumberOfTeams(6);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		model.setNumberOfTeams(null);
		other.setNumberOfTeams(null);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
	}
	
	@Test
	public void verify_equals_numDivisionsNotEqual() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(6);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		model.setNumberOfDivisions(null);
		other.setNumberOfDivisions(6);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		model.setNumberOfDivisions(null);
		other.setNumberOfDivisions(null);
	}
}
