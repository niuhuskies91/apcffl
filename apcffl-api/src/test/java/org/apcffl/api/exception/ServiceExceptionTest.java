package org.apcffl.api.exception;

import static org.junit.Assert.assertEquals;

import org.apcffl.api.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;

public class ServiceExceptionTest {
	
	private RuntimeException tr;
	
	@Before
	public void setUp() {
		tr = new ClassCastException("error0");
	}

	@Test
	public void testConstructors() {
		
		ServiceException exception = new ServiceException("error1");
		assertEquals(exception.getMessage(), "error1");
		
		exception = new ServiceException(tr);
		assertEquals(exception.getMessage(), ClassCastException.class.getCanonicalName() + ": error0");
		
		exception = new ServiceException(tr, "error1");
		assertEquals(exception.getMessage(), "error1");
	}
}
