package org.apcffl.api.persistence.model;

import static org.junit.Assert.assertEquals;

import org.apcffl.ApcfflTest;
import org.junit.Before;
import org.junit.Test;

public class ConfigModelTest {
	
	private ConfigModel model;

	@Before
	public void setUp() {
		model = ApcfflTest.buildConfigModel().get(0);
	}

	@Test
	public void testEqualsOtherObjectNull() {
		ConfigModel other = null;
		
		assertEquals(model.equals(other), false);
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEqualsNotEqualsClassMismatch() {
		String other = new String();
		
		assertEquals(model.equals(other), false);
	}
	
	@Test
	public void testEqualsConfigKeyNotEquals() {
		ConfigModel other = new ConfigModel();
		other.setConfigKey("other");
		other.setConfigValue(ApcfflTest.CONFIG_SESSION_VAL);
		other.setConfigDesc(ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.equals(other), false);
		
		model.setConfigKey(null);
		other.setConfigKey("other");
		other.setConfigValue(ApcfflTest.CONFIG_SESSION_VAL);
		other.setConfigDesc(ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.equals(other), false);
		
		model.setConfigKey(null);
		other.setConfigKey(null);
		other.setConfigValue(ApcfflTest.CONFIG_SESSION_VAL);
		other.setConfigDesc(ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsConfigValueNotEquals() {
		ConfigModel other = new ConfigModel();
		other.setConfigKey(ApcfflTest.CONFIG_SESSION_KEY);
		other.setConfigValue("other");
		other.setConfigDesc(ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.equals(other), false);

		other.setConfigKey(ApcfflTest.CONFIG_SESSION_KEY);
		model.setConfigValue(null);
		other.setConfigValue("other");
		other.setConfigDesc(ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.equals(other), false);

		other.setConfigKey(ApcfflTest.CONFIG_SESSION_KEY);
		model.setConfigValue(null);
		other.setConfigValue(null);
		other.setConfigDesc(ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEqualsConfigDescNotEquals() {
		ConfigModel other = new ConfigModel();
		other.setConfigKey(ApcfflTest.CONFIG_SESSION_KEY);
		other.setConfigValue(ApcfflTest.CONFIG_SESSION_VAL);
		other.setConfigDesc("other");
		assertEquals(model.equals(other), false);

		other.setConfigKey(ApcfflTest.CONFIG_SESSION_KEY);
		other.setConfigValue(ApcfflTest.CONFIG_SESSION_VAL);
		model.setConfigDesc(null);
		other.setConfigDesc("other");
		assertEquals(model.equals(other), false);

		other.setConfigKey(ApcfflTest.CONFIG_SESSION_KEY);
		other.setConfigValue(ApcfflTest.CONFIG_SESSION_VAL);
		model.setConfigDesc(null);
		other.setConfigDesc(null);
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testEquals() {
		ConfigModel other = model;
		
		assertEquals(model.equals(other), true);
	}
	
	@Test
	public void testHash() {
		model.hashCode();
	}
	
	@Test
	public void testGetters() {
		assertEquals(model.getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(model.getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
		assertEquals(model.getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
	}
}
