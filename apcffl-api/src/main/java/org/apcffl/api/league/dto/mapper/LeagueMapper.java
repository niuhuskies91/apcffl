package org.apcffl.api.league.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.api.league.dto.League;
import org.apcffl.api.persistence.model.LeagueModel;
import org.springframework.util.CollectionUtils;

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
			leagueList.add(league);
		}
		return leagueList;
	}
}
