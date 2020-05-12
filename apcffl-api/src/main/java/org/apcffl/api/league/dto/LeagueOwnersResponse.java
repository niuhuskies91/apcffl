package org.apcffl.api.league.dto;

import java.util.List;

import org.apcffl.api.dto.ApiResponse;

public class LeagueOwnersResponse  extends ApiResponse {

	private List<LeagueOwner> leagueOwners;

	public List<LeagueOwner> getLeagueOwners() {
		return leagueOwners;
	}

	public void setLeagueOwners(List<LeagueOwner> leagueOwners) {
		this.leagueOwners = leagueOwners;
	}
}
