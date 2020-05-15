package org.apcffl.api.league.dto;

import java.util.List;

import org.apcffl.api.dto.ApiRequest;

public class TeamsDivisionAssignmentRequest extends ApiRequest {

	private String ownerLeagueName;
	private List<Team> teams;
	
	public String getOwnerLeagueName() {
		return ownerLeagueName;
	}

	public void setOwnerLeagueName(String ownerLeagueName) {
		this.ownerLeagueName = ownerLeagueName;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
}
