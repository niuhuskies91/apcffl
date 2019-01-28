package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class ApplicationActivityModelTest {

	private ApplicationActivityModel model;
	
	@Before
	public void setUp() {
		model = ApcfflTest.buildActivity(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID, 
						ApcfflTest.ACTIVITY_LINEUP_CREATE, 
						ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
	}
	
	@Test
	public void testEqualsNotEqualsOtherObjectNull() {
		ApplicationActivityModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}
	
	@Test
	public void testEqualsActivityDescNotEquals() {
		ApplicationActivityModel other = new ApplicationActivityModel();
		other.setActivityDesc("invalid");
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(model.equals(other), false);
		
		model.setActivityDesc(null);
		other.setActivityDesc("invalid");
		assertEquals(model.equals(other), false);
		
		assertEquals(model.equals(other), false);
		model.setActivityDesc(null);
		other.setActivityDesc(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsActivityIdNotEquals() {
		ApplicationActivityModel other = new ApplicationActivityModel();
		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(0L);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(model.equals(other), false);

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		model.setActivityId(null);
		other.setActivityId(0L);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(model.equals(other), false);

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		model.setActivityId(null);
		other.setActivityId(null);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsActivityNameNotEquals() {
		ApplicationActivityModel other = new ApplicationActivityModel();
		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		other.setActivityName("other");
		assertEquals(model.equals(other), false);

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		other.setActivityName("other");
		assertEquals(model.equals(other), false);

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		model.setActivityName(null);
		other.setActivityName("other");
		assertEquals(model.equals(other), false);

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		model.setActivityName(null);
		other.setActivityName(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		ApplicationActivityModel other = model;
		
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testHash() {
		assertTrue(model.hashCode() > 0);
	}
	
	@Test
	public void testGetters() {
		assertEquals(model.getActivityDesc(), ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		assertEquals(model.getActivityId(), ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		assertEquals(model.getActivityName(), ApcfflTest.ACTIVITY_LINEUP_CREATE);
	}
}
