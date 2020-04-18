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
	public void verify_entity() {
		
	}
	
	@Test
	public void verify_equals_notEquals_otherObjectNull() {
		ApplicationActivityModel other = null;
		
		assertEquals(false, model.equals(other));
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void verify_equals_notEquals_classMismatch() {
		String other = new String();
		
		assertEquals(false, model.equals(other));
	}
	
	@Test
	public void verify_equals_activityDescNotEquals() {
		ApplicationActivityModel other = new ApplicationActivityModel();
		other.setActivityDesc("invalid");
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(false, model.equals(other));
		
		model.setActivityDesc(null);
		other.setActivityDesc("invalid");
		assertEquals(false, model.equals(other));
		
		assertEquals(false, model.equals(other));
		model.setActivityDesc(null);
		other.setActivityDesc(null);
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_activityIdNotEquals() {
		ApplicationActivityModel other = new ApplicationActivityModel();
		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(0L);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(false, model.equals(other));

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		model.setActivityId(null);
		other.setActivityId(0L);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(false, model.equals(other));

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		model.setActivityId(null);
		other.setActivityId(null);
		other.setActivityName(ApcfflTest.ACTIVITY_LINEUP_CREATE);
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals_activityNameNotEquals() {
		ApplicationActivityModel other = new ApplicationActivityModel();
		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		other.setActivityName("other");
		assertEquals(false, model.equals(other));

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		other.setActivityName("other");
		assertEquals(false, model.equals(other));

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		model.setActivityName(null);
		other.setActivityName("other");
		assertEquals(false, model.equals(other));

		other.setActivityDesc(ApcfflTest.ACTIVITY_LINEUP_CREATE_DESC);
		other.setActivityId(ApcfflTest.ACTIVITY_LINEUP_CREATE_ID);
		model.setActivityName(null);
		other.setActivityName(null);
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_equals() {
		ApplicationActivityModel other = model;
		
		assertEquals(true, model.equals(other));
	}
	
	@Test
	public void verify_hash() {
		assertTrue(model.hashCode() > 0);
	}
}
