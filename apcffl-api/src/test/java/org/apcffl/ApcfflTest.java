package org.apcffl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.persistence.model.ApplicationActivityModel;
import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.model.GroupActivityMapModel;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.security.dto.PasswordResetRequest;

public class ApcfflTest {
	
	// General constants
	
	public static final Long   PRIMARY_KEY = 42L;
	
	// General security constants
	
	public static final String USER_NAME              = "dan.kamp";
	public static final String PASSWORD               = "password";
	public static final String TEST_TOKEN             = "R1BCLhVAYJb2zQ9Mkewwmg==";
	public static final Integer TEST_RESET_PSWD_TOKEN = new Integer(42);
	
	public static final Date   TEST_DATE   = new Date();
	
	// Config
	
	public static final String CONFIG_SESSION_KEY     = "security.token.expiration";
	public static final String CONFIG_SESSION_VAL     = "21600000";
	public static final String CONFIG_SESSION_DESC    = "Security expiration token is in milliseconds. Default (21600000)";
	public static final String CONFIG_PSWD_RESET_KEY  = "security.password.token.expiration";
	public static final String CONFIG_PSWD_RESET_VAL  = "300000";
	public static final String CONFIG_PSWD_RESET_DESC = "Password reset token expiration in milliseconds. Default (300000)";
	
	
	// League
	
	public static final Long    LEAGUE_1_ID        = 1L;
	public static final String  LEAGUE_1_NAME      = "The Apcffl";
	public static final Integer LEAGUE_1_NUM_TEAMS = 8;
	public static final Integer LEAGUE_1_NUM_DIV   = 2;
	public static final Long    LEAGUE_2_ID        = 2L;
	public static final String  LEAGUE_2_NAME      = "Test League";
	public static final Integer LEAGUE_2_NUM_TEAMS = 8;
	public static final Integer LEAGUE_2_NUM_DIV   = 2;
	
	// User Groups
	
	public static final String USER_GROUP_ADMIN    = "ADMINISTRATOR";
	public static final Long   USER_GROUP_ADMIN_ID = 1L;
	public static final String USER_GROUP_OWNER    = "TEAM_OWNER";
	public static final Long   USER_GROUP_OWNER_ID = 2L;
	public static final String USER_GROUP_GUEST    = "GUEST";
	public static final Long   USER_GROUP_GUEST_ID = 3L;
	
	// Activity
	
	public static final String ACTIVITY_USER_CREATE        = "USER_CREATE";
	public static final String ACTIVITY_USER_CREATE_DESC   = "Admin: Create a user";
	public static final Long   ACTIVITY_USER_CREATE_ID     = 1L;
	public static final String ACTIVITY_LINEUP_CREATE      = "LINEUP_CREATE";
	public static final String ACTIVITY_LINEUP_CREATE_DESC = "League: Create lineup";
	public static final Long   ACTIVITY_LINEUP_CREATE_ID   = 46L;
	
	// Owner
	
	public static final String OWNER_LAST_NAME             = "Kamp";
	public static final String OWNER_FIRST_NAME            = "Dan";
	public static final String OWNER_EMAIL1                = "email1@gmail.com";
	public static final String OWNER_EMAIL2                = "email2@gmail.com";
	public static final String OWNER_EMAIL3                = "email3@gmail.com";
	public static final Boolean OWNER_ACTIVE               = true;
	
	// Email
	
	public static final String EMAIL_RECIPIENT             = "test@gmail.com";
	public static final String EMAIL_SUBJECT               = "test email";
	public static final String EMAIL_MESSAGE               = "This is a test email";
	
	public static final UserGroupModel buildUserGroup(Long id, String name) {
		UserGroupModel group = new UserGroupModel();
		group.setUserGroupId(id);
		group.setUserGroupName(name);
		
		return group;
	}
	
	public static List<UserGroupModel> buildUserGroups() {
		List<UserGroupModel> groups = new ArrayList<>();
		
		groups.add(buildUserGroup(USER_GROUP_ADMIN_ID, USER_GROUP_ADMIN));
		groups.add(buildUserGroup(USER_GROUP_OWNER_ID, USER_GROUP_OWNER));
		groups.add(buildUserGroup(USER_GROUP_GUEST_ID, USER_GROUP_GUEST));
		
		return groups;
	}
	
	public static ApplicationActivityModel buildActivity(Long id, String activity, String desc) {
		ApplicationActivityModel appActivity = new ApplicationActivityModel();
		appActivity.setActivityId(id);
		appActivity.setActivityName(activity);
		appActivity.setActivityDesc(desc);
		
		return appActivity;
	}
	
	public static List<ApplicationActivityModel> buildActivityList() {
		List<ApplicationActivityModel> activities = new ArrayList<>();
		
		activities.add(buildActivity(ACTIVITY_USER_CREATE_ID, ACTIVITY_USER_CREATE, ACTIVITY_USER_CREATE_DESC));
		activities.add(buildActivity(ACTIVITY_LINEUP_CREATE_ID, ACTIVITY_LINEUP_CREATE, ACTIVITY_LINEUP_CREATE_DESC));
		
		return activities;
	}
	
	public static List<GroupActivityMapModel> buildGroupActivityList() {
		List<GroupActivityMapModel> groupActivities = new ArrayList<>();
		
		UserGroupModel admin = buildUserGroup(USER_GROUP_ADMIN_ID, USER_GROUP_ADMIN);
		UserGroupModel owner = buildUserGroup(USER_GROUP_OWNER_ID, USER_GROUP_OWNER);
		
		ApplicationActivityModel userCreate = 
				buildActivity(ACTIVITY_USER_CREATE_ID, ACTIVITY_USER_CREATE, ACTIVITY_USER_CREATE_DESC);
		ApplicationActivityModel lineupCreate = 
				buildActivity(ACTIVITY_LINEUP_CREATE_ID, ACTIVITY_LINEUP_CREATE, ACTIVITY_LINEUP_CREATE_DESC);
		
		GroupActivityMapModel groupActivity = new GroupActivityMapModel();
		groupActivity.setGroupActivityId(PRIMARY_KEY);
		groupActivity.setApplicationActivityModel(userCreate);
		groupActivity.setUserGroupModel(admin);
		groupActivities.add(groupActivity);
		
		groupActivity = new GroupActivityMapModel();
		groupActivity.setGroupActivityId(PRIMARY_KEY);
		groupActivity.setApplicationActivityModel(lineupCreate);
		groupActivity.setUserGroupModel(admin);
		groupActivities.add(groupActivity);
		
		groupActivity = new GroupActivityMapModel();
		groupActivity.setGroupActivityId(PRIMARY_KEY);
		groupActivity.setApplicationActivityModel(lineupCreate);
		groupActivity.setUserGroupModel(owner);
		groupActivities.add(groupActivity);
		
		return groupActivities;
	}
	
	public static UserModel buildUserModel() {
		UserModel model = new UserModel();
		model.setPassword(PASSWORD);
		model.setUserGroupModel(buildUserGroup(USER_GROUP_OWNER_ID, USER_GROUP_OWNER));
		model.setUserId(PRIMARY_KEY);
		model.setUserName(USER_NAME);
		
		return model;
	}
	
	public static LeagueModel buildLeagueModel(Long id, String name, Integer numTeams, Integer numDiv) {
		LeagueModel model = new LeagueModel();
		model.setLeagueId(id);
		model.setLeagueName(name);
		model.setNumberOfTeams(numTeams);
		model.setNumberOfDivisions(numDiv);
		
		return model;
	}
	
	public static List<LeagueModel> buildLeagueModels() {
		List<LeagueModel> models = new ArrayList<>();
		
		LeagueModel model = 
				buildLeagueModel(LEAGUE_1_ID, LEAGUE_1_NAME, LEAGUE_1_NUM_TEAMS, LEAGUE_1_NUM_DIV);
		models.add(model);
		
		model = buildLeagueModel(LEAGUE_2_ID, LEAGUE_2_NAME, LEAGUE_2_NUM_TEAMS, LEAGUE_2_NUM_DIV);
		models.add(model);
		
		return models;
	}
	
	public static OwnerModel buildOwnerModel() {
		OwnerModel model = new OwnerModel();
		model.setOwnerId(PRIMARY_KEY);
		model.setLeagueModel(buildLeagueModel(LEAGUE_1_ID, LEAGUE_1_NAME, LEAGUE_1_NUM_TEAMS, LEAGUE_1_NUM_DIV));
		model.setUserModel(buildUserModel());
		model.setFirstName(OWNER_FIRST_NAME);
		model.setLastName(OWNER_LAST_NAME);
		model.setEmail1(OWNER_EMAIL1);
		model.setEmail2(OWNER_EMAIL2);
		model.setEmail3(OWNER_EMAIL3);
		model.setActiveFlag(OWNER_ACTIVE);
		model.setCreateDate(TEST_DATE);
		model.setUpdateDate(TEST_DATE);
		
		return model;
	}
	
	public static List<ConfigModel> buildConfigModel() {
		List<ConfigModel> configs = new ArrayList<>();
		
		ConfigModel config = new ConfigModel();
		configs.add(config);
		config.setConfigDesc(CONFIG_SESSION_DESC);
		config.setConfigKey(CONFIG_SESSION_KEY);
		config.setConfigValue(CONFIG_SESSION_VAL);
		
		config = new ConfigModel();
		configs.add(config);
		config.setConfigDesc(CONFIG_PSWD_RESET_DESC);
		config.setConfigKey(CONFIG_PSWD_RESET_KEY);
		config.setConfigValue(CONFIG_PSWD_RESET_VAL);
		
		return configs;
	}
	
	public static PasswordResetRequest buildPasswordResetRequest() {
		PasswordResetRequest request = new PasswordResetRequest();
		request.setPassword(PASSWORD);
		request.setPasswordResetToken(TEST_RESET_PSWD_TOKEN);
		request.setUserName(USER_NAME);
		
		return request;
	}
	
	public static AccountRequest buildAccountRequest() {
		AccountRequest request = new AccountRequest();
		request.setSecurityToken(ApcfflTest.TEST_TOKEN);
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		request.setUserName(ApcfflTest.USER_NAME);
		
		return request;
	}
	
	public static AccountResponse buildAccountResponse() {
		AccountResponse response = new AccountResponse();
		response.setEmail1(ApcfflTest.OWNER_EMAIL1);
		response.setEmail2(ApcfflTest.OWNER_EMAIL2);
		response.setEmail3(ApcfflTest.OWNER_EMAIL3);
		response.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		response.setLastName(ApcfflTest.OWNER_LAST_NAME);
		response.setLeagueName(ApcfflTest.LEAGUE_1_NAME);
		
		return response;
	}
}
