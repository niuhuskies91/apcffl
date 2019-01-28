package org.apcffl.api.security.service.impl;

import org.apcffl.api.bo.SessionManagerBo;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.persistence.model.UserModel;
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
	private SessionManagerBo sessionManager;
	
	public AuthorizationServiceImpl(UserRepository userRepository, SessionManagerBo sessionManager) {
		this.userRepository = userRepository;
		this.sessionManager = sessionManager;
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

}
