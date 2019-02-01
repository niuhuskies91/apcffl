package org.apcffl.api.security.service.impl;

import org.apcffl.api.bo.EmailManagerBo;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.security.service.AuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	private UserRepository userRepository;
	private OwnerRepository ownerRepository;
	private SessionManagerBo sessionManager;
	private EmailManagerBo emailManager;
	
	public AuthorizationServiceImpl(UserRepository userRepository, OwnerRepository ownerRepository,
			SessionManagerBo sessionManager, EmailManagerBo emailManager) {
		
		this.userRepository = userRepository;
		this.ownerRepository = ownerRepository;
		this.sessionManager = sessionManager;
		this.emailManager = emailManager;
	}

	@Override
	public UserDto login(String userName, String password) {
		
		UserModel user = userRepository.findByUserNamePassword(userName, password);
		if (user == null) {
			String error = "Authorization failed for user name: " + userName;
			LOG.warn(error);
			throw new SecurityException(error);
		}
		return new UserDto(
				userName, 
				user.getUserGroupModel().getUserGroupName(),
				sessionManager.generateTokenForUser(userName));
	}

	@Override
	public void passwordResetToken(String userName) {
		
		String subject = "Apcffl: Password reset";
		
		OwnerModel owner = ownerRepository.findByUserName(userName);
		if (owner == null) {
			String error = "The username is not found. No password reset token was generated.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		String email = owner.getEmail1();
		
		int token = sessionManager.generatePasswordResetToken(userName);
		StringBuilder sb = new StringBuilder();
		sb.append("Your password reset token is: ").append(token);
		sb.append(". Please use this when resetting your password.");
		
		emailManager.sendEmail(email, subject, sb.toString());
	}

	@Override
	public void userNameRecovery(String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetPassword(String resetToken, String userName, String password) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValidSessionToken(String sessionToken, String userName) {
		
		return sessionManager.isValidSessionToken(userName, sessionToken);
	}

}
