package org.apcffl.api.exception;

public class SecurityException extends RuntimeException {

	/**
	 * STS generated serialized id
	 */
	private static final long serialVersionUID = -3263617547778782135L;

	public SecurityException(String message) {
		super(message);
	}
	
	public SecurityException(Throwable tr) {
		super(tr);
	}
	
	public SecurityException(Throwable tr, String message) {
		super(message, tr);
	}
}
