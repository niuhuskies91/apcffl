package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class UserGroupModelTest {
	
	private UserGroupModel model;
	
	@Before
	public void setUp() {
		model = ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, 
				ApcfflTest.USER_GROUP_OWNER);
	}
	
	@Test
	public void testEqualsNotEqualsOtherObjectNull() {
		UserGroupModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}

	@Test
	public void testEqualsUserGroupIdNotEquals() {
		UserGroupModel other = new UserGroupModel();
		other.setUserGroupId(0L);
		other.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		assertEquals(model.equals(other), false);

		model.setUserGroupId(null);
		other.setUserGroupId(0L);
		other.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		assertEquals(model.equals(other), false);

		model.setUserGroupId(null);
		other.setUserGroupId(null);
		other.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		assertEquals(model.equals(other), true);
	}

	@Test
	public void testEqualsUserGroupNameNotEquals() {
		UserGroupModel other = new UserGroupModel();
		other.setUserGroupId(ApcfflTest.USER_GROUP_OWNER_ID);
		other.setUserGroupName("other");
		assertEquals(model.equals(other), false);

		other.setUserGroupId(ApcfflTest.USER_GROUP_OWNER_ID);
		model.setUserGroupName(null);
		other.setUserGroupName("other");
		assertEquals(model.equals(other), false);

		other.setUserGroupId(ApcfflTest.USER_GROUP_OWNER_ID);
		model.setUserGroupName(null);
		other.setUserGroupName(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		UserGroupModel other = model;
		
		assertEquals(model.equals(other), true);
	}
}
