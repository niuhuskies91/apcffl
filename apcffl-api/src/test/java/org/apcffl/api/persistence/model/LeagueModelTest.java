package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
	public void testEqualsOtherObjectNull() {
		LeagueModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}
	
	@Test
	public void testEqualsLeagueIdNotEquals() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_2_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), false);
		
		model.setLeagueId(null);
		other.setLeagueId(ApcfflTest.LEAGUE_2_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), false);
		
		model.setLeagueId(null);
		other.setLeagueId(null);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsLeagueNameNotEqual() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_2_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), false);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		model.setLeagueName(null);
		other.setLeagueName(ApcfflTest.LEAGUE_2_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), false);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		model.setLeagueName(null);
		other.setLeagueName(null);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsNumTeamsNotEqual() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(6);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), false);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		model.setNumberOfTeams(null);
		other.setNumberOfTeams(6);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), false);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		model.setNumberOfTeams(null);
		other.setNumberOfTeams(null);
		other.setNumberOfDivisions(ApcfflTest.LEAGUE_1_NUM_DIV);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsNumDivisionsNotEqual() {
		LeagueModel other = new LeagueModel();
		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		other.setNumberOfDivisions(6);
		assertEquals(model.equals(other), false);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		model.setNumberOfDivisions(null);
		other.setNumberOfDivisions(6);
		assertEquals(model.equals(other), false);

		other.setLeagueId(ApcfflTest.LEAGUE_1_ID);
		other.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		other.setNumberOfTeams(ApcfflTest.LEAGUE_1_NUM_TEAMS);
		model.setNumberOfDivisions(null);
		other.setNumberOfDivisions(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		LeagueModel other = model;
		
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testHash() {
		assertTrue(model.hashCode() > 0);
	}
	
	@Test
	public void testGetters() {
		assertEquals(model.getLeagueId(), ApcfflTest.LEAGUE_1_ID);
		assertEquals(model.getLeagueName(), ApcfflTest.LEAGUE_1_NAME);
		assertEquals(model.getNumberOfTeams(), ApcfflTest.LEAGUE_1_NUM_TEAMS);
		assertEquals(model.getNumberOfDivisions(), ApcfflTest.LEAGUE_1_NUM_DIV);
	}
}
