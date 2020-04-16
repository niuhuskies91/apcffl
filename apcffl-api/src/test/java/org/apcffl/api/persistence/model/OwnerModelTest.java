package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class OwnerModelTest {

	private OwnerModel model;
	
	@Before
	public void setUp() {
		model = ApcfflTest.buildOwnerModel();
	}

	@Test
	public void testEqualsOtherObjectNull() {
		OwnerModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}
	
	@Test
	public void testEqualsOwnerIdNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(0L);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		model.setOwnerId(null);
		other.setOwnerId(0L);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		model.setOwnerId(null);
		other.setOwnerId(null);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsLeagueModelNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(new LeagueModel());
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		model.setLeagueModel(null);
		other.setLeagueModel(new LeagueModel());
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		model.setLeagueModel(null);
		other.setLeagueModel(null);
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsUsereModelNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(new UserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		model.setUserModel(null);
		other.setUserModel(new UserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		model.setUserModel(null);
		other.setUserModel(null);
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsFirstNameNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName("other");
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		model.setFirstName(null);
		other.setFirstName("other");
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		model.setFirstName(null);
		other.setFirstName(null);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsLastNameNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName("other");
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		model.setLastName(null);
		other.setLastName("other");
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		model.setLastName(null);
		other.setLastName(null);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsEmail1NotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1("other");
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		model.setEmail1(null);
		other.setEmail1("other");
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		model.setEmail1(null);
		other.setEmail1(null);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsEmail2NotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2("other");
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		model.setEmail2(null);
		other.setEmail2("other");
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		model.setEmail2(null);
		other.setEmail2(null);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsEmail3NotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3("other");
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		model.setEmail3(null);
		other.setEmail3("other");
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		model.setEmail3(null);
		other.setEmail3(null);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsActiveFlagNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(false);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		model.setActiveFlag(null);
		other.setActiveFlag(false);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		model.setActiveFlag(null);
		other.setActiveFlag(null);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsCreateDateNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(new Date());
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		model.setCreateDate(null);
		other.setCreateDate(new Date());
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		model.setCreateDate(null);
		other.setCreateDate(null);
		other.setUpdateDate(ApcfflTest.TEST_DATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsUpdateDateNotEquals() {
		OwnerModel other = new OwnerModel();
		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		other.setUpdateDate(new Date());
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setEmail3(ApcfflTest.OWNER_EMAIL3);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		model.setUpdateDate(null);
		other.setUpdateDate(new Date());
		assertEquals(model.equals(other), false);

		other.setOwnerId(ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		other.setLeagueModel(ApcfflTest.buildLeagueModel(ApcfflTest.LEAGUE_1_ID, ApcfflTest.LEAGUE_1_NAME, ApcfflTest.LEAGUE_1_NUM_TEAMS, ApcfflTest.LEAGUE_1_NUM_DIV));
		other.setUserModel(ApcfflTest.buildUserModel());
		other.setFirstName(ApcfflTest.OWNER_FIRST_NAME);
		other.setLastName(ApcfflTest.OWNER_LAST_NAME);
		other.setEmail1(ApcfflTest.OWNER_EMAIL1);
		other.setEmail2(ApcfflTest.OWNER_EMAIL2);
		other.setActiveFlag(ApcfflTest.OWNER_ACTIVE);
		other.setCreateDate(ApcfflTest.TEST_DATE);
		model.setUpdateDate(null);
		other.setUpdateDate(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		OwnerModel other = model;
		
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testHash() {
		model.hashCode();
	}
	
	@Test
	public void testGetters() {
		assertEquals(model.getActiveFlag(), ApcfflTest.OWNER_ACTIVE);
		assertEquals(model.getCreateDate(), ApcfflTest.TEST_DATE);
		assertEquals(model.getEmail1(), ApcfflTest.OWNER_EMAIL1);
		assertEquals(model.getEmail2(), ApcfflTest.OWNER_EMAIL2);
		assertEquals(model.getEmail3(), ApcfflTest.OWNER_EMAIL3);
		assertEquals(model.getFirstName(), ApcfflTest.OWNER_FIRST_NAME);
		assertEquals(model.getLastName(), ApcfflTest.OWNER_LAST_NAME);
		assertNotNull(model.getLeagueModel());
		assertEquals(model.getOwnerId(), ApcfflTest.ANSWER_TO_THE_UNIVERSE);
		assertEquals(model.getUpdateDate(), ApcfflTest.TEST_DATE);
		assertNotNull(model.getUserModel());
	}
}
