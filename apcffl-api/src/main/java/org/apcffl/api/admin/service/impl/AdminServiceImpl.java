package org.apcffl.api.admin.service.impl;

import java.util.Date;
import java.util.List;

import org.apcffl.api.admin.dto.AccountCreateRequest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.AllAccountsResponse;
import org.apcffl.api.admin.dto.mapper.AdminMapper;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.bo.EmailManagerBo;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.exception.constants.ErrorCodeEnums;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.UserGroupRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.apcffl.api.service.ApcfflService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl extends ApcfflService implements AdminService {

	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

	private final OwnerRepository ownerRepository;
	private final UserRepository userRepository;
	private final UserGroupRepository userGroupRepository;
	private final EmailManagerBo emailManager;
	
	public AdminServiceImpl(final OwnerRepository ownerRepository, final UserRepository userRepository,
			final UserGroupRepository userGroupRepository, final EmailManagerBo emailManager) {
		
		this.ownerRepository = ownerRepository;
		this.userRepository = userRepository;
		this.userGroupRepository = userGroupRepository;
		this.emailManager = emailManager;
	}

	@Override
	public AccountResponse accountRetrieval(AccountRequest request) {
		// validate user group access
		AccountResponse response =
				accountAccessError(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_OWNER);
		if (response.getError() != null) {
			return response;
		}
		// retrieve the owner's account
		OwnerModel owner = ownerRepository.findByUserName(request.getUserName());
		if (owner == null) {
			response.setError( 
					new ErrorDto(ErrorCodeEnums.AccountError.toString(), UIMessages.ACCOUNT_NOT_FOUND));
			LOG.error(response.getError().getMessage());
			return response;
		}
		return AdminMapper.convertAccountModel(owner);
	}

	@Override
	public AllAccountsResponse accountRetrievalAll(AccountRequest request) {
		// validate user group access
		AllAccountsResponse response =
				allAccountsAccessError(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_ADMIN);
		if (response.getError() != null) {
			return response;
		}
		List<OwnerModel> owners = ownerRepository.findAll();
		response.setAccounts( AdminMapper.convertAccounts(owners) );
		
		return response;
	}

	@Override
	public String accountCreate(AccountCreateRequest request) {
		UserGroupModel userGroup = userGroupRepository.findByUserGroupName(SecurityConstants.USER_GROUP_GUEST);
		
		// first create the user record
		UserModel user = new UserModel();
		user.setPassword(request.getPassword());
		user.setUserName(request.getUserName());
		user.setUserGroupModel(userGroup);
		user = userRepository.save(user);
		
		// create the owner record
		OwnerModel owner = new OwnerModel();
		Date now = new Date();
		owner.setActiveFlag(false);
		owner.setCreateDate(now);
		owner.setEmail1(request.getEmail1());
		owner.setEmail2(request.getEmail2());
		owner.setEmail3(request.getEmail3());
		owner.setFirstName(request.getFirstName());
		owner.setLastName(request.getLastName());
		owner.setUpdateDate(now);
		owner.setUserModel(user);
		ownerRepository.save(owner);
		
		// send welcome message to the newly registered user
		emailManager.sendEmail(
				request.getEmail1(), 
				UIMessages.EMAIL_WELCOME_SUBJECT, 
				UIMessages.EMAIL_WELCOME_MESSAGE);
		
		return UIMessages.MSG_GENERIC_CHECK_EMAIL;
	}

	@Override
	public AccountResponse accountUpdate(AccountRequest request) {
		// validate user group access
		AccountResponse response =
				accountAccessError(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_OWNER);
		if (response.getError() != null) {
			return response;
		}
		
		// retrieve the existing owner record
		OwnerModel owner = ownerRepository.findByUserName(request.getUserName());

		// set the new values and save
		owner.setEmail1(request.getEmail1());
		owner.setEmail2(request.getEmail2());
		owner.setEmail3(request.getEmail3());
		owner.setFirstName(request.getFirstName());
		owner.setLastName(request.getLastName());
		owner.setUpdateDate(new Date());
		ownerRepository.save(owner);
		
		return response;
	}
	
	/**
	 * Validate the group access level for account activities.
	 * 
	 * @param userGroup : user group name
	 * @param accessLevels : List<String> all user groups that have access privileges
	 * @return AccountResponse : contains an error if access is not validated
	 */
	private AccountResponse accountAccessError(String userGroup, List<String> accessLevels) {
		AccountResponse response = new AccountResponse();
		
		ErrorDto error = validateGroupRole(userGroup, accessLevels);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
		}
		return response;
	}
	
	private AllAccountsResponse allAccountsAccessError(String userGroup, List<String> accessLevels) {
		AllAccountsResponse response = new AllAccountsResponse();
		
		ErrorDto error = validateGroupRole(userGroup, accessLevels);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
		}
		return response;
	}
}
