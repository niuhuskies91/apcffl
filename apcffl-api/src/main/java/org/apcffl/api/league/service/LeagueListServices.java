package org.apcffl.api.league.service;

import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.league.dto.ConferenceListResponse;
import org.apcffl.api.league.dto.LeagueListsResponse;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;

public interface LeagueListServices {

	public LeagueListsResponse allLeagues(ApiRequest request);
	
	public LeagueOwnersResponse leagueOwners(LeagueOwnersRequest request);
	
	public ConferenceListResponse allConferences(ApiRequest request);
}
