package org.apcffl.api.exception;

public class PersistenceException extends RuntimeException {

	/**
	 * STS generated
	 */
	private static final long serialVersionUID = 5242818935836730772L;
	
	public PersistenceException(String message) {
		super(message);
	}
	
	public PersistenceException(Throwable tr) {
		super(tr);
	}
	
	public PersistenceException(Throwable tr, String message) {
		super(message, tr);
	}
}
