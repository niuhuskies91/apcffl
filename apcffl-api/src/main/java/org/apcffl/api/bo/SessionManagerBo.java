package org.apcffl.api.bo;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionManagerBo {

	private static final Logger LOG = LoggerFactory.getLogger(SessionManagerBo.class);
	
	// Security expiration token is in milliseconds. Default (21600000) is 6 hours
	private static final long TOKEN_TIMEOUT = 60000;
	
	// Ensures 128 bit token
	private static final int TOKEN_SIZE = 16;

	private static Map<String, Session> sessionMap; // key: user name, value: session token
	
	@PostConstruct
	public void init() {
		sessionMap = new HashMap<>();
	}
	
	/**
	 * Generate a session token for the user.
	 * 
	 * @param userName : user login
	 * @return : session token
	 */
	public String generateTokenForUser(String userName) {
		Session session = new Session();
		String token = session.getToken();
		sessionMap.put(userName, session);
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("userName: " + userName + ", token: " + token);
		}
		return token;
	}
	
	/**
	 * Validate the session token for the user.
	 * 
	 * @param userName : the user name (login)
	 * @param token : session token
	 * @return true if valid, false otherwise
	 */
	public boolean isValidToken(String userName, String token) {
		// the userName is not in the session map
		if (!sessionMap.containsKey(userName)) {
			return false;
		} 
		// the session token does not match the token passed in
		Session session = sessionMap.get(userName);
		if (!session.getToken().equals(token)) {
			sessionMap.remove(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Token does not match cache. Removed token for userName: " + userName);
			}
			return false;
		}
		// the token is expired
		long currTime = (new Date()).getTime();
		long sessionStart = session.getCreateTime().getTime();
		if ( (currTime - sessionStart) >= TOKEN_TIMEOUT) {
			sessionMap.remove(userName);
			if (LOG.isDebugEnabled()) {
				LOG.debug("Token expired. Removed token for userName: " + userName);
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
}
