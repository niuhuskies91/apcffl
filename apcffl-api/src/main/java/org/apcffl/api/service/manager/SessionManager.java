package org.apcffl.api.service.manager;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apcffl.api.config.GeneralPropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionManager {

	private static final Logger LOG = LoggerFactory.getLogger(SessionManager.class);
	
	// Ensures 128 bit token
	private static final int TOKEN_SIZE = 16;

	// key: user name, value: session token
	private static Map<String, Session> sessionMap;
	
	// key: user name, value: password reset token
	private static Map<String, PasswordResetToken> passwordResetMap;
	
	private final GeneralPropertiesConfig propsConfig;
	
	public SessionManager(final GeneralPropertiesConfig propsConfig) {
		this.propsConfig = propsConfig;
	}
	
	@PostConstruct
	public void init() {
		sessionMap = new HashMap<>();
		passwordResetMap = new HashMap<>();
	}
	
	/**
	 * Generate a password reset token.
	 * 
	 * @param userName : user login
	 * @return password reset token
	 */
	public int generatePasswordResetToken(String userName) {
		PasswordResetToken resetToken = new PasswordResetToken();
		int token = resetToken.getToken();
		passwordResetMap.put(userName, resetToken);
		
		return token;
	}
	
	/**
	 * Generate a session token for the user.
	 * We are double encoding. The session map contains level 1
	 * Base 64 encoding. What we return to the user is a level 2
	 * Base 64 encoding, so that the UI user token is double encoded.
	 * 
	 * @param userName : user login
	 * @return session token
	 */
	public String generateTokenForUser(String userName) {
		Session session = new Session();
		String token = session.getToken();
		sessionMap.put(userName, session);
		
		return Base64.getEncoder().encodeToString(token.getBytes());
	}
	
	/**
	 * Validate the session token for the user.
	 * 
	 * @param userName : the user name (login)
	 * @param token : session token
	 * @return true if valid, false otherwise
	 */
	public boolean isValidSessionToken(String userName, String token) {
		// the userName is not in the session map
		if (!sessionMap.containsKey(userName)) {
			return false;
		} 
		
		// remove the double encoding to a single encoded token
		// which is stored in the map
		String decoded = new String(Base64.getDecoder().decode(token));
		
		// the session token does not match the token passed in
		Session session = sessionMap.get(userName);
		if (!session.getToken().equals(decoded)) {
			sessionMap.remove(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Token does not match cache. Removed session token for userName: " + userName);
			}
			return false;
		}
		
		// is the token expired ?
		long currTime = (new Date()).getTime();
		long sessionStart = session.getCreateTime().getTime();
		if ( (currTime - sessionStart) >= propsConfig.getSecurityTokenExp()) {
			sessionMap.remove(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Token expired. Removed session token for userName: " + userName);
			}
			return false;
		}
		// the token is valid
		return true;
	}
	
	/**
	 * Validate the password reset token for the user.
	 * 
	 * @param userName : the user name (login)
	 * @param token : session token
	 * @return true if valid, false otherwise
	 */
	public boolean isValidPasswordResetToken(String userName, int token) {
		// the userName is not in the password reset map
		if (!passwordResetMap.containsKey(userName)) {
			return false;
		} 
		// the password reset token does not match the token passed in
		PasswordResetToken reset = passwordResetMap.get(userName);
		if (reset.getToken() != token) {
			passwordResetMap.remove(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Token does not match cache. Removed password reset token for userName: " + userName);
			}
			return false;
		}
		
		// is the token expired ?
		long currTime = (new Date()).getTime();
		long sessionStart = reset.getCreateTime().getTime();
		if ( (currTime - sessionStart) >= propsConfig.getSecurityPassResetTokenExp()) {
			passwordResetMap.remove(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Token expired. Removed password reset token for userName: " + userName);
			}
			return false;
		}
		// the token is valid
		return true;
	}
	
	public class Session {
		private String token;
		private Date createTime;
		
		public Session() {
			this.token = generateTokenBase64();
			this.createTime = new Date();
		}
		
		public String getToken() {
			return token;
		}
		public Date getCreateTime() {
			return createTime;
		}
		
		private String generateTokenBase64() {
			SecureRandom random = new SecureRandom();
			byte[] token = new byte[TOKEN_SIZE];
			random.nextBytes(token);
			
			return Base64.getEncoder().encodeToString(token);
		}
	}
	
	public class PasswordResetToken {
		private static final int TOKEN_UPPER_LIMIT = 99999;
		private static final int TOKEN_LOWER_LIMIT = 10000;
		private int token;
		private Date createTime;
		
		public PasswordResetToken() {
			this.token = generateToken();
			this.createTime = new Date();
		}
		
		public int getToken() {
			return token;
		}
		public Date getCreateTime() {
			return createTime;
		}
		private int generateToken() {
			return 
				(int)(Math.random() * (TOKEN_UPPER_LIMIT - TOKEN_LOWER_LIMIT)) + TOKEN_LOWER_LIMIT;
		}
	}
}
