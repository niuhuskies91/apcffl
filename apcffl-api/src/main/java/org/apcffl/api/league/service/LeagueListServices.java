package org.apcffl.api.league.service;

import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.league.dto.LeagueListsResponse;

public interface LeagueListServices {

	public LeagueListsResponse allLeagues(ApiRequest request);
}
