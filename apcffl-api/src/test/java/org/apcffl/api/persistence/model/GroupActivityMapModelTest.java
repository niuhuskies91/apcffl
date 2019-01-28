package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class GroupActivityMapModelTest {
	
	private GroupActivityMapModel model;

	@Before
	public void setUp() {
		ApplicationActivityModel activityModel = ApcfflTest.buildActivity(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID, 
				ApcfflTest.ACTIVITY_LINEUP_CREATE, 
				ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		UserGroupModel userGroupModel = 
				ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER);
		
		model = new GroupActivityMapModel();
		model.setApplicationActivityModel(activityModel);
		model.setGroupActivityId(ApcfflTest.PRIMARY_KEY);
		model.setUserGroupModel(userGroupModel);
	}
	
	@Test
	public void testEqualsNotEqualsOtherObjectNull() {
		GroupActivityMapModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}
	
	@Test
	public void testEqualsGroupActivityActivityNotEquals() {
		GroupActivityMapModel other = new GroupActivityMapModel();
		other.setApplicationActivityModel(new ApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(model.equals(other), false);

		model.setApplicationActivityModel(null);
		other.setApplicationActivityModel(new ApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(model.equals(other), false);

		model.setApplicationActivityModel(null);
		other.setApplicationActivityModel(null);
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsGroupActivityIdNotEquals() {
		GroupActivityMapModel other = new GroupActivityMapModel();
		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(0L);
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(model.equals(other), false);

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		model.setGroupActivityId(null);
		other.setGroupActivityId(0L);
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(model.equals(other), false);

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		model.setGroupActivityId(null);
		other.setGroupActivityId(null);
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsGroupActivityUserGroupNotEquals() {
		GroupActivityMapModel other = new GroupActivityMapModel();
		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(new UserGroupModel());
		assertEquals(model.equals(other), false);

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		model.setUserGroupModel(null);
		other.setUserGroupModel(new UserGroupModel());
		assertEquals(model.equals(other), false);

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		model.setUserGroupModel(null);
		other.setUserGroupModel(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		GroupActivityMapModel other = model;
		
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testHash() {
		assertTrue(model.hashCode() > 0);
	}
	
	@Test
	public void testGetters() {
		assertNotNull(model.getApplicationActivityModel());
		assertNotNull(model.getGroupActivityId());
		assertNotNull(model.getUserGroupModel());
	}
}
