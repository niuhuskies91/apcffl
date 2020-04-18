package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class UserModelTest {
	
	private UserModel model;
	
	@Before
	public void setUp() {
		model = ApcfflTest.buildUserModel();
	}

	@Test
	public void verify_equals_otherObjectNull() {
		UserModel other = null;
		
		assertEquals(false, model.equals(other));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void verify_equals_notEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(false, model.equals(other));
	}
	
	@Test
	public void verify_equals_userIdNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(0L);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(false, model.equals(other));
		
		model.setUserId(null);
		other.setUserId(0L);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(false, model.equals(other));
		
		model.setUserId(null);
		other.setUserId(null);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_userNameNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName("other");
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(false, model.equals(other));

		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		model.setUserName(null);
		other.setUserName("other");
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(false, model.equals(other));

		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		model.setUserName(null);
		other.setUserName(null);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_passwordNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword("other");
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(false, model.equals(other));

		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName(ApcfflTest.USER_NAME);
		model.setPassword(null);
		other.setPassword("other");
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(false, model.equals(other));

		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName(ApcfflTest.USER_NAME);
		model.setPassword(null);
		other.setPassword(null);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_userGroupNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_GUEST_ID, ApcfflTest.USER_GROUP_GUEST));
		assertEquals(false, model.equals(other));

		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		model.setUserGroupModel(null);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_GUEST_ID, ApcfflTest.USER_GROUP_GUEST));
		assertEquals(false, model.equals(other));

		other.setUserId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		model.setUserGroupModel(null);
		other.setUserGroupModel(null);
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals() {
		UserModel other = model;
		
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_hash() {
		assertTrue(model.hashCode() > 0);
	}
}
