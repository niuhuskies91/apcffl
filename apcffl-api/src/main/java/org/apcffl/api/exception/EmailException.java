package org.apcffl.api.exception;

public class EmailException extends RuntimeException {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 1703690487899924732L;

	public EmailException(String message) {
		super(message);
	}
	
	public EmailException(Throwable tr) {
		super(tr);
	}
	
	public EmailException(Throwable tr, String message) {
		super(message, tr);
	}
}
