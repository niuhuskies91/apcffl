package org.apcffl.api.league.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apcffl.api.league.dto.Conference;
import org.apcffl.api.league.dto.Division;
import org.apcffl.api.league.dto.League;
import org.apcffl.api.league.dto.LeagueOwner;
import org.apcffl.api.league.dto.School;
import org.apcffl.api.league.dto.TeamRoster;
import org.apcffl.api.persistence.model.ConferenceModel;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.SchoolModel;
import org.apcffl.api.persistence.model.TeamRosterModel;

public class LeagueMapper {

	private LeagueMapper() {}
	
	public static List<League> convertLeagueList(List<LeagueModel> models) {
		List<League> leagueList = new ArrayList<League>();
		if (CollectionUtils.isEmpty(models)) {
			return leagueList;
		}
		for (LeagueModel model : models) {
			League league = new League();
			league.setLeagueName(model.getLeagueName());
			league.setNumDivisions(model.getNumberOfDivisions());
			league.setNumTeams(model.getNumberOfTeams());
			league.setDivisions( convertDivisions(model.getDivisions()) );
			leagueList.add(league);
		}
		return leagueList;
	}
	
	public static List<Division> convertDivisions(Set<DivisionModel> models) {
		List<Division> divisions = new ArrayList<Division>();
		if (CollectionUtils.isEmpty(models)) {
			return divisions;
		}
		for (DivisionModel model : models) {
			Division division = new Division(model.getDivisionName());
			divisions.add(division);
		}
		return divisions;
	}
	
	public static List<LeagueOwner> convertLeagueOwners(List<OwnerModel> owners) {
		List<LeagueOwner> leagueOwners = new ArrayList<LeagueOwner>();
		if (CollectionUtils.isEmpty(owners)) {
			return leagueOwners;
		}
		for (OwnerModel owner : owners) {
			LeagueOwner leagueOwner = new LeagueOwner();
			leagueOwner.setUserName(owner.getUserModel().getUserName());
			leagueOwner.setActiveFlag(owner.getActiveFlag());
			leagueOwner.setEmail(owner.getEmail1());
			leagueOwner.setFirstName(owner.getFirstName());
			leagueOwner.setLastName(owner.getLastName());
			if (owner.getTeamModel() != null) {
				leagueOwner.setTeamName(owner.getTeamModel().getTeamName());
				if (owner.getTeamModel().getDivisionModel() != null) {
					leagueOwner.setDivisionName(owner.getTeamModel().getDivisionModel().getDivisionName());
				}
			}
			leagueOwners.add(leagueOwner);
		}
		return leagueOwners;
	}
	
	public static List<Conference> convertConferences(List<ConferenceModel> models) {
		List<Conference> conferences = new ArrayList<Conference>();
		if (CollectionUtils.isEmpty(models)) {
			return conferences;
		}
		for (ConferenceModel model : models) {
			Conference conference = new Conference();
			conference.setConferenceAbbr(model.getConferenceAbbr());
			conference.setConferenceName(model.getConferenceName());
			conference.setConferenceType(model.getNcaaDivisionType());
			conference.setSchools(convertSchools(model.getSchools()));
			conferences.add(conference);
		}
		return conferences;
	}
	
	public static List<School> convertSchools(Set<SchoolModel> models) {
		List<School> schools = new ArrayList<School>();
		for (SchoolModel model : models) {
			schools.add(convertSchool(model));
		}
		return schools;
	}
	
	private static School convertSchool(SchoolModel model) {
		School school = new School();
		school.setSchoolName(model.getSchoolName());
		school.setConferenceName(model.getConference().getConferenceName());
		school.setConferenceAbbr(model.getConference().getConferenceAbbr());
		return school;
	}
	
	public static List<TeamRoster> convertTeamRosters(List<TeamRosterModel> models) {
		
		List<TeamRoster> roster = new ArrayList<TeamRoster>();
		if (CollectionUtils.isEmpty(models)) {
			return roster;
		}
		for (TeamRosterModel model : models) {
			TeamRoster rosterEntry = new TeamRoster();
			rosterEntry.setScholarshipPoints(model.getScholarshipPoints());
			rosterEntry.setSchool(convertSchool(model.getSchoolModel()));
			roster.add(rosterEntry);
		}
		return roster;
	}
}
