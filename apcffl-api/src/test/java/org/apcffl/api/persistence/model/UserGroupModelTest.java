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
	public void verify_equals_notEqualsOtherObjectNull() {
		UserGroupModel other = null;
		
		assertEquals(false, model.equals(other));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void verify_equals_notEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(false, model.equals(other));
	}

	@Test
	public void verify_equals_userGroupIdNotEquals() {
		UserGroupModel other = new UserGroupModel();
		other.setUserGroupId(0L);
		other.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		assertEquals(false, model.equals(other));

		model.setUserGroupId(null);
		other.setUserGroupId(0L);
		other.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		assertEquals(false, model.equals(other));

		model.setUserGroupId(null);
		other.setUserGroupId(null);
		other.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		assertEquals(true, model.equals(other));
	}

	@Test
	public void verify_equals_userGroupNameNotEquals() {
		UserGroupModel other = new UserGroupModel();
		other.setUserGroupId(ApcfflTest.USER_GROUP_OWNER_ID);
		other.setUserGroupName("other");
		assertEquals(false, model.equals(other));

		other.setUserGroupId(ApcfflTest.USER_GROUP_OWNER_ID);
		model.setUserGroupName(null);
		other.setUserGroupName("other");
		assertEquals(false, model.equals(other));

		other.setUserGroupId(ApcfflTest.USER_GROUP_OWNER_ID);
		model.setUserGroupName(null);
		other.setUserGroupName(null);
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals() {
		UserGroupModel other = model;
		
		assertEquals(true, model.equals(other));
	}
}
