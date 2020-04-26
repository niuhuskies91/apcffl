package org.apcffl.api.security.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.dto.PasswordResetRequest;
import org.apcffl.api.security.dto.UserDto;
import org.apcffl.api.security.service.AuthorizationService;
import org.apcffl.api.service.manager.EmailManager;
import org.apcffl.api.service.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthorizationServiceImpl implements AuthorizationService {

	private static final Logger LOG = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	private final UserRepository userRepository;
	private final OwnerRepository ownerRepository;
	private final EmailManager emailManager;
	private final SessionManager sessionManager;
	
	public AuthorizationServiceImpl(final UserRepository userRepository, final OwnerRepository ownerRepository,
			final EmailManager emailManager, final SessionManager sessionManager) {
		
		this.userRepository = userRepository;
		this.ownerRepository = ownerRepository;
		this.emailManager = emailManager;
		this.sessionManager = sessionManager;
	}

	@Override
	public UserDto login(String userName, String password) {
		
		UserModel user = userRepository.findByUserNamePassword(userName, password);
		if (user == null) {
			String error = UIMessages.ERROR_AUTH_FAIL + userName;
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
		
		String subject = UIMessages.EMAIL_PSWD_RESET_SUBJECT;
		
		OwnerModel owner = ownerRepository.findByUserName(userName);
		if (owner == null) {
			String error = UIMessages.ERROR_AUTH_PSWD_RESET_USER_NOTFOUND;
			LOG.error(error);
			throw new SecurityException(error);
		}
		String email = owner.getEmail1();
		
		int token = sessionManager.generatePasswordResetToken(userName);
		StringBuilder sb = new StringBuilder();
		sb.append(UIMessages.EMAIL_BODY_PSWD_RESET_1).append(token);
		sb.append(UIMessages.EMAIL_BODY_PSWD_RESET_2);
		
		emailManager.sendEmail(email, subject, sb.toString());
	}

	@Override
	public void resetPassword(PasswordResetRequest request) {
		String userName = request.getUserName();
		Integer token = request.getPasswordResetToken();
		String password = request.getPassword();
		
		if (StringUtils.isEmpty(userName) || token == null || StringUtils.isEmpty(password)) {
			String error = UIMessages.ERROR_RESET_PSWD_PARAMS;
			LOG.error(error);
			throw new SecurityException(error);
		}
		OwnerModel owner = ownerRepository.findByUserName(request.getUserName());
		if (owner == null) {
			String error = UIMessages.ERROR_RESET_PSWD_USER_NOTFOUND;
			LOG.error(error);
			throw new SecurityException(error);
		}
		boolean tokenValid = sessionManager.isValidPasswordResetToken(userName, token);
		if (!tokenValid) {
			String error = UIMessages.ERROR_RESET_TOKEN_INVALID;
			throw new SecurityException(error);
		}
		UserModel user = owner.getUserModel();
		user.setPassword(password);
		userRepository.save(user);
	}

	@Override
	public void userNameRecovery(String email) {
		if (StringUtils.isEmpty(email)) {
			String error = UIMessages.ERROR_USERNAME_RECOVER_MISSING_EMAIL;
			LOG.error(error);
			throw new SecurityException(error);
		}
		// recover the user name for the given email
		OwnerModel owner = ownerRepository.findByEmail(email);
		if (owner == null || owner.getUserModel() == null || StringUtils.isEmpty(owner.getUserModel().getUserName())) {
			String error = UIMessages.ERROR_USERNAME_RECOVER_NOT_FOUND;
			LOG.error(error);
			throw new SecurityException(error);
		}
		
		// send the recovered user name to the email address
		
		String subject = UIMessages.EMAIL_USERNAME_RECOVER_SUBJECT;
		StringBuilder sb = new StringBuilder();
		sb.append(UIMessages.EMAIL_BODY_USERNAME_RECOVER).append(owner.getUserModel().getUserName());
		
		emailManager.sendEmail(email, subject, sb.toString());
	}
}
