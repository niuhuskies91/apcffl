package org.apcffl.api.exception;

import static org.junit.Assert.assertEquals;

import org.apcffl.api.exception.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;

public class DataNotFoundExceptionTest {
	
	private RuntimeException tr;
	
	@Before
	public void setUp() {
		tr = new ClassCastException("error0");
	}

	@Test
	public void testConstructors() {
		
		DataNotFoundException exception = new DataNotFoundException("error1");
		assertEquals(exception.getMessage(), "error1");
		
		exception = new DataNotFoundException(tr);
		assertEquals(exception.getMessage(), ClassCastException.class.getCanonicalName() + ": error0");
		
		exception = new DataNotFoundException(tr, "error1");
		assertEquals(exception.getMessage(), "error1");
	}
}
