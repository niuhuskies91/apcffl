package org.apcffl.api.league.dto;

import org.apcffl.api.dto.ApiResponse;

public class TeamResponse extends ApiResponse {

	private Team team;

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
