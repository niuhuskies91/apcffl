package org.apcffl.api.league.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.constants.ApcfflConstants;
import org.apcffl.api.league.dto.Conference;
import org.apcffl.api.league.dto.League;
import org.apcffl.api.league.dto.LeagueOwner;
import org.apcffl.api.league.dto.School;
import org.apcffl.api.league.dto.TeamRoster;
import org.apcffl.api.persistence.model.ConferenceModel;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.TeamRosterModel;
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
		assertEquals(ApcfflTest.USER_NAME, result.get(0).getUserName());
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
		assertEquals(ApcfflTest.USER_NAME, result.get(0).getUserName());
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
		assertEquals(ApcfflTest.USER_NAME, result.get(0).getUserName());
		assertEquals(true, result.get(0).getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, result.get(0).getEmail());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, result.get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, result.get(0).getLastName());

		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, result.get(0).getTeamName());
		assertEquals(ApcfflTest.LEAGUE_1_DIV_1, result.get(0).getDivisionName());
	}
	
	@Test
	public void verify_convertConferences_nullConferences() {
		
		// prepare test data
		
		List<ConferenceModel> models = null;
		
		// invoke
		
		List<Conference> conferences = LeagueMapper.convertConferences(models);
		
		// verify
		
		assertEquals(0, conferences.size());
	}
	
	@Test
	public void verify_convertConferences_emptyConferences() {
		
		// prepare test data
		
		List<ConferenceModel> models = Collections.emptyList();
		
		// invoke
		
		List<Conference> conferences = LeagueMapper.convertConferences(models);
		
		// verify
		
		assertEquals(0, conferences.size());
	}
	
	@Test
	public void verify_convertConferences() {
		
		// prepare test data
		
		List<ConferenceModel> models = ApcfflTest.buildConferenceModels();
		
		// invoke
		
		List<Conference> conferences = LeagueMapper.convertConferences(models);
		
		// verify
		
		assertEquals(2, conferences.size());
		
		Conference conference = conferences.get(0);
		assertEquals(ApcfflTest.CONF_ABBR_BIG_10, conference.getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_BIG_10, conference.getConferenceName());
		assertEquals(ApcfflConstants.NCAA_CONFERENCE_FBS, conference.getConferenceType());
		School school = conference.getSchools().get(0);
		assertEquals(ApcfflTest.CONF_ABBR_BIG_10, school.getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_BIG_10, school.getConferenceName());
		assertEquals(ApcfflTest.SCHOOL_NAME_BIG_10_1, school.getSchoolName());
		school = conference.getSchools().get(1);
		assertEquals(ApcfflTest.CONF_ABBR_BIG_10, school.getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_BIG_10, school.getConferenceName());
		assertEquals(ApcfflTest.SCHOOL_NAME_BIG_10_2, school.getSchoolName());
		
		conference = conferences.get(1);
		assertEquals(ApcfflTest.CONF_ABBR_MAC, conference.getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_MAC, conference.getConferenceName());
		assertEquals(ApcfflConstants.NCAA_CONFERENCE_FBS, conference.getConferenceType());
		school = conference.getSchools().get(0);
		assertEquals(ApcfflTest.CONF_ABBR_MAC, school.getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_MAC, school.getConferenceName());
		assertEquals(ApcfflTest.SCHOOL_NAME_MAC_1, school.getSchoolName());
		school = conference.getSchools().get(1);
		assertEquals(ApcfflTest.CONF_ABBR_MAC, school.getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_MAC, school.getConferenceName());
		assertEquals(ApcfflTest.SCHOOL_NAME_MAC_2, school.getSchoolName());
	}
	
	@Test
	public void verify_convertTeamRosters_nullModel() {
		
		// prepare test data
		
		List<TeamRosterModel> models = null;
		
		// invoke
		
		List<TeamRoster> result = LeagueMapper.convertTeamRosters(models);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_convertTeamRosters_emptyModel() {
		
		// prepare test data
		
		List<TeamRosterModel> models = Collections.emptyList();
		
		// invoke
		
		List<TeamRoster> result = LeagueMapper.convertTeamRosters(models);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_convertTeamRosters() {
		
		// prepare test data
		
		List<TeamRosterModel> models = ApcfflTest.buildTeamRosterModels();
		
		// invoke
		
		List<TeamRoster> result = LeagueMapper.convertTeamRosters(models);
		
		// verify
		
		assertEquals(2, result.size());
		
		assertEquals(ApcfflTest.SCHOLARSHIP_POINT_MIN, result.get(0).getScholarshipPoints());
		assertEquals(ApcfflTest.CONF_ABBR_BIG_10, result.get(0).getSchool().getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_BIG_10, result.get(0).getSchool().getConferenceName());
		assertEquals(ApcfflTest.SCHOOL_NAME_BIG_10_1, result.get(0).getSchool().getSchoolName());
		assertEquals(ApcfflTest.SCHOLARSHIP_POINT_MIN, result.get(1).getScholarshipPoints());
		assertEquals(ApcfflTest.CONF_ABBR_BIG_10, result.get(1).getSchool().getConferenceAbbr());
		assertEquals(ApcfflTest.CONF_NAME_BIG_10, result.get(1).getSchool().getConferenceName());
		assertEquals(ApcfflTest.SCHOOL_NAME_BIG_10_2, result.get(1).getSchool().getSchoolName());
	}
}
