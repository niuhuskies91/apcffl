package org.apcffl.api.service;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;

public abstract class ApcfflService {

	protected ErrorDto validateGroupRole(String userGroupName, List<String> validAccessRoles) {
		if (!validAccessRoles.contains(userGroupName)) {
			return new ErrorDto(UserGroupAccessError.toString(), UIMessages.ERROR_USER_GROUP_ACCESS);
		} else {
			return null;
		}
	}

	protected ErrorDto validateGroupRole(String userGroupName, String leagueName, List<String> validAccessRoles) {
		if (!validAccessRoles.contains(userGroupName)) {
			return new ErrorDto(UserGroupAccessError.toString(), UIMessages.ERROR_USER_GROUP_ACCESS);
		} else if (StringUtils.isEmpty(leagueName)) {
			return new ErrorDto(LeagueNotAssignedError.toString(), UIMessages.ERROR_MISSING_LEAGUE_AFFILIATION);
		} else {
			return null;
		}
	}
}
