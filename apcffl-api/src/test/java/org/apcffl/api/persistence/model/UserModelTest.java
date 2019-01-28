package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
	public void testEqualsOtherObjectNull() {
		UserModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}
	
	@Test
	public void testEqualsUserIdNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(0L);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), false);
		
		model.setUserId(null);
		other.setUserId(0L);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), false);
		
		model.setUserId(null);
		other.setUserId(null);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsUserNameNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName("other");
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), false);

		other.setUserId(ApcfflTest.PRIMARY_KEY);
		model.setUserName(null);
		other.setUserName("other");
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), false);

		other.setUserId(ApcfflTest.PRIMARY_KEY);
		model.setUserName(null);
		other.setUserName(null);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsPasswordNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword("other");
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), false);

		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName(ApcfflTest.USER_NAME);
		model.setPassword(null);
		other.setPassword("other");
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), false);

		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName(ApcfflTest.USER_NAME);
		model.setPassword(null);
		other.setPassword(null);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_OWNER_ID, ApcfflTest.USER_GROUP_OWNER));
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsUserGroupNotEquals() {
		UserModel other = new UserModel();
		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_GUEST_ID, ApcfflTest.USER_GROUP_GUEST));
		assertEquals(model.equals(other), false);

		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		model.setUserGroupModel(null);
		other.setUserGroupModel(ApcfflTest.buildUserGroup(ApcfflTest.USER_GROUP_GUEST_ID, ApcfflTest.USER_GROUP_GUEST));
		assertEquals(model.equals(other), false);

		other.setUserId(ApcfflTest.PRIMARY_KEY);
		other.setUserName(ApcfflTest.USER_NAME);
		other.setPassword(ApcfflTest.PASSWORD);
		model.setUserGroupModel(null);
		other.setUserGroupModel(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		UserModel other = model;
		
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testHash() {
		assertTrue(model.hashCode() > 0);
	}
	
	@Test
	public void testGetters() {
		assertEquals(model.getPassword(), ApcfflTest.PASSWORD);
		assertNotNull(model.getUserGroupModel());
		assertEquals(model.getUserId(), ApcfflTest.PRIMARY_KEY);
		assertEquals(model.getUserName(), ApcfflTest.USER_NAME);
	}
}
