package org.apcffl.api.security.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apcffl.api.bo.EmailManagerBo;
import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.dto.PasswordResetRequest;
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
	public void resetPassword(PasswordResetRequest request) {
		String userName = request.getUserName();
		Integer token = request.getPasswordResetToken();
		String password = request.getPassword();
		
		if (StringUtils.isEmpty(userName) || token == null || StringUtils.isEmpty(password)) {
			String error = "Missing required parameters. The password cannot be reset.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		OwnerModel owner = ownerRepository.findByUserName(request.getUserName());
		if (owner == null) {
			String error = "The username is not found. The password cannot be reset.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		boolean tokenValid = sessionManager.isValidPasswordResetToken(userName, token);
		if (!tokenValid) {
			String error = "The password reset token is invalid. The password cannot be reset.";
			throw new SecurityException(error);
		}
		UserModel user = owner.getUserModel();
		user.setPassword(password);
		userRepository.save(user);
	}

	@Override
	public void userNameRecovery(String email) {
		if (StringUtils.isEmpty(email)) {
			String error = "The email is not privided. The the user name cannot be recovered.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		// recover the user name for the given email
		OwnerModel owner = ownerRepository.findByEmail(email);
		if (owner == null || owner.getUserModel() == null || StringUtils.isEmpty(owner.getUserModel().getUserName())) {
			String error = "The username is not found. The password cannot be reset.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		
		// send the recovered user name to the email address
		
		String subject = "Apcffl: User name recovery";
		StringBuilder sb = new StringBuilder();
		sb.append("Your user name is: ").append(owner.getUserModel().getUserName());
		
		emailManager.sendEmail(email, subject, sb.toString());
	}

	@Override
	public boolean isValidSessionToken(String sessionToken, String userName) {
		
		return sessionManager.isValidSessionToken(userName, sessionToken);
	}

}
