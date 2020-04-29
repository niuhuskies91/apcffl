package org.apcffl.api.league.dto;

import java.util.List;

import org.apcffl.api.dto.ApiResponse;

public class LeagueListsResponse extends ApiResponse {

	private List<League> leagues;

	public List<League> getLeagues() {
		return leagues;
	}

	public void setLeagues(List<League> leagues) {
		this.leagues = leagues;
	}
	
}
