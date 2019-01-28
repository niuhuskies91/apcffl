package org.apcffl.api.exception;

public class ServiceException extends RuntimeException {

	/**
	 * STS generated serialized id
	 */
	private static final long serialVersionUID = -1510413387369510234L;

	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Throwable tr) {
		super(tr);
	}
	
	public ServiceException(Throwable tr, String message) {
		super(message, tr);
	}
}
