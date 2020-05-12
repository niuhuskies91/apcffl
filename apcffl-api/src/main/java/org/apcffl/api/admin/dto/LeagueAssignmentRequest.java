package org.apcffl.api.admin.dto;

import org.apcffl.api.dto.ApiRequest;

public class LeagueAssignmentRequest extends ApiRequest {
	
	private String ownerLeagueName;
	private String ownerUserName;
	private String ownerTeamName;
	
	public String getOwnerLeagueName() {
		return ownerLeagueName;
	}
	public void setOwnerLeagueName(String ownerLeagueName) {
		this.ownerLeagueName = ownerLeagueName;
	}
	public String getOwnerUserName() {
		return ownerUserName;
	}
	public void setOwnerUserName(String ownerUserName) {
		this.ownerUserName = ownerUserName;
	}
	public String getOwnerTeamName() {
		return ownerTeamName;
	}
	public void setOwnerTeamName(String ownerTeamName) {
		this.ownerTeamName = ownerTeamName;
	}

}
