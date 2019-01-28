package org.apcffl.api.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class SecurityExceptionTest {
	
	private RuntimeException tr;
	
	@Before
	public void setUp() {
		tr = new ClassCastException("error0");
	}

	@Test
	public void testConstructors() {
		
		SecurityException exception = new SecurityException("error1");
		assertEquals(exception.getMessage(), "error1");
		
		exception = new SecurityException(tr);
		assertEquals(exception.getMessage(), ClassCastException.class.getCanonicalName() + ": error0");
		
		exception = new SecurityException(tr, "error1");
		assertEquals(exception.getMessage(), "error1");
	}
}
