package org.apcffl.api.exception.constants;

public class Enums {
	
	private Enums() {}

	public static enum ErrorCodeEnums {
		AccountError("AccountError"), 
		AdminError("AdminError"), 
		AuthorizationError("AuthorizationError"), 
		EmailSenderError("EmailSenderError"), 
		LeagueError("LeagueError"),
		LeagueNotAssignedError("LeagueNotAssignedError"), 
		SessionTokenExpired("SessionTokenExpired"),
		UserGroupAccessError("UserGroupAccessError");
		
		private String errorCode;
		
		private ErrorCodeEnums(String errorCode) {
			this.errorCode = errorCode;
		}
		
		@Override
		public String toString() {
			return errorCode;
		}
	}
}
