package org.apcffl.api.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EmailExceptionTest {
	
	private RuntimeException tr;
	
	@Before
	public void setUp() {
		tr = new ClassCastException("error0");
	}

	@Test
	public void testConstructors() {
		
		EmailException exception = new EmailException("error1");
		assertEquals(exception.getMessage(), "error1");
		
		exception = new EmailException(tr);
		assertEquals(exception.getMessage(), ClassCastException.class.getCanonicalName() + ": error0");
		
		exception = new EmailException(tr, "error1");
		assertEquals(exception.getMessage(), "error1");
	}
}
