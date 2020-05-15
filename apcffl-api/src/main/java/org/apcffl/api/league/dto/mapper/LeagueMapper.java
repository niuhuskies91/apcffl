package org.apcffl.api.league.dto.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apcffl.api.league.dto.Division;
import org.apcffl.api.league.dto.League;
import org.apcffl.api.league.dto.LeagueOwner;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;

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
}
