package org.apcffl.api.constants;

public class UIMessages {
	
	// authentication errors

	public static final String ERROR_AUTH_FAIL                     = "Authorization failed for user name: ";
	public static final String ERROR_AUTH_PSWD_RESET_USER_NOTFOUND = "The username is not found. No password reset token was generated.";
	public static final String ERROR_RESET_PSWD_PARAMS             = "Missing required parameters. The password cannot be reset.";
	public static final String ERROR_RESET_PSWD_USER_NOTFOUND      = "The username is not found. The password cannot be reset.";
	public static final String ERROR_RESET_TOKEN_INVALID           = "The password reset token is invalid. The password cannot be reset.";
	public static final String ERROR_TOKEN_EXPIRED                 = "The login session has expired.";
	public static final String ERROR_USER_GROUP_ACCESS             = "This functionality is not available at your access level.";
	public static final String ERROR_USERNAME_RECOVER_MISSING_EMAIL= "The email is not privided. The the user name cannot be recovered.";
	public static final String ERROR_USERNAME_RECOVER_NOT_FOUND    = "The username is not found. The password cannot be reset.";

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
	public static final String EMAIL_WELCOME_MESSAGE          = "Welcome to the APCFFL (Academic Probation College Fantasy Football League). Remember, college football is always in season.";
	
	// account management errors
	
	public static final String ACCOUNT_NOT_FOUND              = "The account profile could not be found.";
	
	private UIMessages() {}
}
