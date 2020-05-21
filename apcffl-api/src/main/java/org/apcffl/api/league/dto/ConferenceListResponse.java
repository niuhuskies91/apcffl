package org.apcffl.api.league.dto;

import java.util.List;

import org.apcffl.api.dto.ApiResponse;

public class ConferenceListResponse extends ApiResponse {

	private List<Conference> conferences;

	public List<Conference> getConferences() {
		return conferences;
	}

	public void setConferences(List<Conference> conferences) {
		this.conferences = conferences;
	}
	
}
