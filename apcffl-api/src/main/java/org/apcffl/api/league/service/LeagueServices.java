package org.apcffl.api.league.service;

import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.TeamsDivisionAssignmentRequest;

public interface LeagueServices {

	public LeagueOwnersResponse teamsDivisionAssignment(TeamsDivisionAssignmentRequest request);
}
