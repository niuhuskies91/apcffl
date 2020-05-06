package org.apcffl.api.constants;

public class UIMessages {
	
	public static final String ERROR_GENERAL_INTERNAL_EXCEPTION = "A general internal error occurred. Please contact the administrator if issues persist.";

	// authentication errors

	public static final String ERROR_AUTH_FAIL                                  = "Authorization failed for user name: ";
	public static final String ERROR_AUTH_PSWD_RESET_USER_NOTFOUND              = "The username is not found. No password reset token was generated.";
	public static final String ERROR_MISSING_LEAGUE_AFFILIATION                 = "The functionality is not available as no league affiliation is present.";
	public static final String ERROR_RESET_PSWD_PARAMS                          = "Missing required parameters. The password cannot be reset.";
	public static final String ERROR_RESET_PSWD_USER_NOTFOUND                   = "The username is not found. The password cannot be reset.";
	public static final String ERROR_RESET_TOKEN_INVALID                        = "The password reset token is invalid. The password cannot be reset.";
	public static final String ERROR_TOKEN_EXPIRED                              = "The login session has expired.";
	public static final String ERROR_USER_GROUP_ACCESS                          = "This functionality is not available at your access level.";
	public static final String ERROR_USERNAME_RECOVER_MISSING_EMAIL             = "The email is not privided. The the user name cannot be recovered.";
	public static final String ERROR_USERNAME_RECOVER_NOT_FOUND                 = "The username is not found. The password cannot be reset.";
	public static final String ERROR_USERNAME_OR_PRIMARY_EMAIL_EXISTS_ON_CREATE = "The username or primary email already exists and cannot be created.";
	
	// messages
	
	public static final String MSG_GENERIC_CHECK_EMAIL = "Please check your email.";
	public static final String MSG_PSWD_RESET_SUCCESS  = "Your password has been successfully changed. Please log in.";

	// email messages
	
	public static final String EMAIL_PSWD_RESET_SUBJECT       = "Apcffl: Password reset";
	public static final String EMAIL_BODY_PSWD_RESET_1        = "Your password reset token is: ";
	public static final String EMAIL_BODY_PSWD_RESET_2        = ". Please use this when resetting your password.";
	public static final String EMAIL_USERNAME_RECOVER_SUBJECT = "Apcffl: User name recovery";
	public static final String EMAIL_BODY_USERNAME_RECOVER    = "Your user name is: ";
	public static final String EMAIL_WELCOME_SUBJECT          = "Welcome to the APCFFL";
	public static final String EMAIL_WELCOME_MESSAGE          = "Welcome to the APCFFL (Academic Probation College Fantasy Football League). Remember, college football is always in season. A message has been sent to the administrator notifying that you have created this account.";
	public static final String EMAIL_ADMIN_NEW_ACCOUNT_SUBJECT= "New account created.";
	public static final String EMAIL_ADMIN_NEW_ACCOUNT_MESSAGE= "A new account has been created. username: %1, email: %2";
	
	// account management errors
	
	public static final String ACCOUNT_ADMIN_REQUIRED                  = "Administrator privileges are required to perform this function.";
	public static final String ACCOUNT_NOT_FOUND                       = "The account profile could not be found.";
	public static final String ACCOUNT_UPDATE_PRIMARY_EMAIL_NOT_UNIQUE = "The primary email is already assigned to a different user name and cannot be duplicate.";
	
	private UIMessages() {}
}
