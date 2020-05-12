package org.apcffl.api.league.dto;

import org.apcffl.api.dto.ApiRequest;

public class LeagueOwnersRequest extends ApiRequest {

	private String ownerLeagueName;

	public String getOwnerLeagueName() {
		return ownerLeagueName;
	}

	public void setOwnerLeagueName(String ownerLeagueName) {
		this.ownerLeagueName = ownerLeagueName;
	}
	
	
}
