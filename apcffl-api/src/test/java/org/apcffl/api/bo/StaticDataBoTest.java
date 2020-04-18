package org.apcffl.api.bo;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apcffl.ApcfflTest;
import org.apcffl.api.dto.GroupActivityMapDto;
import org.apcffl.api.persistence.model.GroupActivityMapModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.repository.GroupActivityMapRepository;
import org.apcffl.api.persistence.repository.UserGroupRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class StaticDataBoTest {

	@Mock
	private UserGroupRepository userGroupRepository;

	@Mock
	private GroupActivityMapRepository groupActivityRepository;
	
	@InjectMocks
	@Resource
	private StaticDataBo bo;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		List<UserGroupModel> mockUserGroups = ApcfflTest.buildUserGroups();
		when(userGroupRepository.findAll()).thenReturn(mockUserGroups);
		
		List<GroupActivityMapModel> mockGroupActivity = ApcfflTest.buildGroupActivityList();	
		when(groupActivityRepository.findAll()).thenReturn(mockGroupActivity);
		
		bo.init();
	}
	
	@Test
	public void testGetUserGroupMap() {
		Map<String, Long> userGroups = StaticDataBo.getUserGroupMap();
		assertEquals(userGroups.size(), 3);
		assertEquals(userGroups.get(ApcfflTest.USER_GROUP_ADMIN), ApcfflTest.USER_GROUP_ADMIN_ID);
		assertEquals(userGroups.get(ApcfflTest.USER_GROUP_OWNER), ApcfflTest.USER_GROUP_OWNER_ID);
		assertEquals(userGroups.get(ApcfflTest.USER_GROUP_GUEST), ApcfflTest.USER_GROUP_GUEST_ID);
	}
	
	@Test
	public void testGetGroupActivityMap() {
		Map<String, GroupActivityMapDto> groupMap = StaticDataBo.getGroupActivityMap();
		assertEquals(groupMap.size(), 2);
		
		assertEquals(groupMap.get(ApcfflTest.USER_GROUP_ADMIN).getGroupName(), ApcfflTest.USER_GROUP_ADMIN);
		Map<String,String> activityMap = groupMap.get(ApcfflTest.USER_GROUP_ADMIN).getApplicationActivity();
		assertEquals(activityMap.size(), 2);
		assertEquals(activityMap.get(ApcfflTest.ACTIVITY_USER_CREATE), ApcfflTest.ACTIVITY_USER_CREATE_DESC);
		assertEquals(activityMap.get(ApcfflTest.ACTIVITY_LINEUP_CREATE), ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		
		assertEquals(groupMap.get(ApcfflTest.USER_GROUP_OWNER).getGroupName(), ApcfflTest.USER_GROUP_OWNER);
		activityMap = groupMap.get(ApcfflTest.USER_GROUP_OWNER).getApplicationActivity();
		assertEquals(activityMap.size(), 1);
		assertEquals(activityMap.get(ApcfflTest.ACTIVITY_LINEUP_CREATE), ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
	}
}
