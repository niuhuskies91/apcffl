package org.apcffl.api.league.dto;

import org.apcffl.api.dto.ApiResponse;

public class LeagueTeamsResponse extends ApiResponse {

	private LeagueTeams leagueTeams;

	public LeagueTeams getLeagueTeams() {
		return leagueTeams;
	}

	public void setLeagueTeams(LeagueTeams leagueTeams) {
		this.leagueTeams = leagueTeams;
	}
}
