package org.apcffl.api.league.service.helper;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.LeagueError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apcffl.api.constants.ApcfflConstants;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.Team;
import org.apcffl.api.persistence.model.DivisionModel;

public class LeagueServiceHelper {

	private LeagueServiceHelper() {
	}

	public static ErrorDto validateDivisionNames(List<Team> teams, Map<String, DivisionModel> divisionByName) {

		Map<String, Integer> teamDivisionCount = new HashMap<String, Integer>();

		for (Team team : teams) {
			String divisionName = team.getDivisionName();
			if (StringUtils.isNotEmpty(divisionName)) {
				if (!divisionByName.containsKey(divisionName)) {
					return new ErrorDto(LeagueError.toString(), UIMessages.LEAGUE_DIVISION_NAME_NOT_MATCH);
				}
				if (teamDivisionCount.containsKey(divisionName)) {
					teamDivisionCount.put(divisionName, teamDivisionCount.get(divisionName) + 1);
				} else {
					teamDivisionCount.put(divisionName, 1);
				}
			}
		}
		for (String key : teamDivisionCount.keySet()) {
			if (ApcfflConstants.BIZ_RULE_NUM_TEAMS_DIVISION < teamDivisionCount.get(key)) {
				return new ErrorDto(LeagueError.toString(), UIMessages.LEAGUE_DIVISION_EXCEED_TEAM_COUNT);
			}
		}
		return null;
	}

}
