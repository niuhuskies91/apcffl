package org.apcffl;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.api.persistence.model.ApplicationActivityModel;
import org.apcffl.api.persistence.model.GroupActivityMapModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.model.UserModel;

public class ApcfflTest {
	
	// General constants
	
	public static final Long   PRIMARY_KEY = 42L;
	
	public static final String USER_NAME   = "dan.kamp";
	public static final String PASSWORD    = "password";
	public static final String TEST_TOKEN  = "R1BCLhVAYJb2zQ9Mkewwmg==";
	
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
}
