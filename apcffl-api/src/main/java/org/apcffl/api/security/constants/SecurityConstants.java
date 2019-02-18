package org.apcffl.api.security.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecurityConstants {

	private SecurityConstants() {}
	
	// User groups
	
	public static final String USER_GROUP_ADMIN = "ADMINISTRATOR";
	public static final String USER_GROUP_OWNER = "TEAM_OWNER";
	public static final String USER_GROUP_GUEST = "GUEST";
	
	public static final List<String> USER_GROUP_TIER_ADMIN = new ArrayList<>(Arrays.asList(USER_GROUP_ADMIN));
	public static final List<String> USER_GROUP_TIER_OWNER = new ArrayList<>(Arrays.asList(USER_GROUP_ADMIN, USER_GROUP_OWNER));
	

}
