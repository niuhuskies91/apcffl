package org.apcffl.api.exception;

public class DataNotFoundException extends RuntimeException {

	/**
	 * STS generated serialized id
	 */
	private static final long serialVersionUID = -1510413387369510234L;

	public DataNotFoundException(String message) {
		super(message);
	}
	
	public DataNotFoundException(Throwable tr) {
		super(tr);
	}
	
	public DataNotFoundException(Throwable tr, String message) {
		super(message, tr);
	}
}
