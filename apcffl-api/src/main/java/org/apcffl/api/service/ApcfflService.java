package org.apcffl.api.service;

import java.util.List;

import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.exception.constants.ErrorCodeEnums;

public abstract class ApcfflService {

	protected ErrorDto validateGroupRole(String userGroupName, List<String> validAccessRoles) {
		if (!validAccessRoles.contains(userGroupName)) {
			return new ErrorDto(ErrorCodeEnums.UserGroupAccessError.toString(), UIMessages.ERROR_USER_GROUP_ACCESS);
		} else {
			return null;
		}
	}
}
