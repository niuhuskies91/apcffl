package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
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
		model.setGroupActivityId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		model.setUserGroupModel(userGroupModel);
	}
	
	@Test
	public void verify_equals_notEqualsOtherObjectNull() {
		GroupActivityMapModel other = null;
		
		assertEquals(false, model.equals(other));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void verify_equals_notEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(false, model.equals(other));
	}
	
	@Test
	public void verify_equals_groupActivityActivityNotEquals() {
		GroupActivityMapModel other = new GroupActivityMapModel();
		other.setApplicationActivityModel(new ApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(false, model.equals(other));

		model.setApplicationActivityModel(null);
		other.setApplicationActivityModel(new ApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(false, model.equals(other));

		model.setApplicationActivityModel(null);
		other.setApplicationActivityModel(null);
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_groupActivityIdNotEquals() {
		GroupActivityMapModel other = new GroupActivityMapModel();
		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(0L);
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(false, model.equals(other));

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		model.setGroupActivityId(null);
		other.setGroupActivityId(0L);
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(false, model.equals(other));

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		model.setGroupActivityId(null);
		other.setGroupActivityId(null);
		other.setUserGroupModel(model.getUserGroupModel());
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_groupActivityUserGroupNotEquals() {
		GroupActivityMapModel other = new GroupActivityMapModel();
		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		other.setUserGroupModel(new UserGroupModel());
		assertEquals(false, model.equals(other));

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		model.setUserGroupModel(null);
		other.setUserGroupModel(new UserGroupModel());
		assertEquals(false, model.equals(other));

		other.setApplicationActivityModel(model.getApplicationActivityModel());
		other.setGroupActivityId(model.getGroupActivityId());
		model.setUserGroupModel(null);
		other.setUserGroupModel(null);
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals() {
		GroupActivityMapModel other = model;
		
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_hash() {
		assertTrue(model.hashCode() > 0);
	}
}
