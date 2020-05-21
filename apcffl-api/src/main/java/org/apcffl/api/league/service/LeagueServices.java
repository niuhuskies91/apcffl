package org.apcffl.api.league.service;

import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.LeagueTeams;
import org.apcffl.api.league.dto.LeagueTeamsResponse;
import org.apcffl.api.league.dto.TeamResponse;

public interface LeagueServices {

	public LeagueOwnersResponse teamsDivisionAssignment(LeagueTeams request);
	
	public LeagueTeamsResponse activeLeagueRosters(LeagueTeams request);
	
	public TeamResponse teamRoster(ApiRequest request);
}
