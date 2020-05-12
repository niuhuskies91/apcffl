package org.apcffl.api.admin.service.impl;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.*;

import java.util.Date;
import java.util.List;

import org.apcffl.api.admin.dto.AccountCreateRequest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.AllAccountsResponse;
import org.apcffl.api.admin.dto.LeagueAssignmentRequest;
import org.apcffl.api.admin.dto.mapper.AdminMapper;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.config.EmailConfig;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ApiResponse;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.model.TeamModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.model.UserModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.persistence.repository.TeamRepository;
import org.apcffl.api.persistence.repository.UserGroupRepository;
import org.apcffl.api.persistence.repository.UserRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.apcffl.api.service.ApcfflService;
import org.apcffl.api.service.manager.EmailManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl extends ApcfflService implements AdminService {

	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

	private final LeagueRepository leagueRepository;
	private final OwnerRepository ownerRepository;
	private final TeamRepository teamRepository;
	private final UserRepository userRepository;
	private final UserGroupRepository userGroupRepository;
	private final EmailManager emailManager;
	private final EmailConfig emailConfig;
	
	public AdminServiceImpl(final OwnerRepository ownerRepository, final UserRepository userRepository,
			final TeamRepository teamRepository,
			final UserGroupRepository userGroupRepository, final EmailManager emailManager,
			final EmailConfig emailConfig, final LeagueRepository leagueRepository) {
		
		this.ownerRepository = ownerRepository;
		this.userRepository = userRepository;
		this.userGroupRepository = userGroupRepository;
		this.emailManager = emailManager;
		this.emailConfig = emailConfig;
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
	}

	@Override
	public AccountResponse accountRetrieval(AccountRequest request) {
		// validate user group access
		AccountResponse response = accountAccessError(request.getUserGroupName(),
				SecurityConstants.USER_GROUP_TIER_OWNER);
		if (response.getError() != null) {
			return response;
		}
		try {
			// retrieve the owner's account
			OwnerModel owner = ownerRepository.findByUserName(request.getUserName());
			if (owner != null) {
				return AdminMapper.convertAccountModel(owner);
			} else {
				response.setError(new ErrorDto(AccountError.toString(), UIMessages.ACCOUNT_NOT_FOUND));
				LOG.error(response.getError().getMessage());
			}
		} catch (Exception e) {
			response.setError(new ErrorDto(AccountError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}

	@Override
	public AllAccountsResponse accountRetrievalAll(AccountRequest request) {
		// validate user group access
		AllAccountsResponse response = allAccountsAccessError(request.getUserGroupName(),
				SecurityConstants.USER_GROUP_TIER_ADMIN);
		if (response.getError() != null) {
			return response;
		}
		try {
			List<OwnerModel> owners = ownerRepository.findAll();
			response.setAccounts(AdminMapper.convertAccounts(owners));
		} catch (Exception e) {
			response.setError(new ErrorDto(AccountError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}
	
	@Override
	public ApiResponse ownerLeagueAssignment(LeagueAssignmentRequest request) {
		
		Date now = new Date();
	
		// validate user group access
		ApiResponse response = new ApiResponse();
		if (!SecurityConstants.USER_GROUP_ADMIN.contentEquals(request.getUserGroupName())) {
			ErrorDto error = new ErrorDto(AccountError.toString(), UIMessages.ACCOUNT_ADMIN_REQUIRED);
			response.setError(error);
			LOG.error(error.getMessage());
			return response;
		}
		try {
			
			UserGroupModel userGroup = userGroupRepository.findByUserGroupName(SecurityConstants.USER_GROUP_OWNER);
			OwnerModel owner = ownerRepository.findByUserName(request.getOwnerUserName());
			LeagueModel league = leagueRepository.findByLeagueName(request.getOwnerLeagueName());
			
			// create a new team for the owner
			TeamModel team = new TeamModel();
			team.setLeagueModel(league);
			team.setTeamName(request.getOwnerTeamName());
			team.setCreateDate(now);
			team = teamRepository.save(team);
			
			owner.getUserModel().setUserGroupModel(userGroup);
			owner.setActiveFlag(true);
			owner.setTeamModel(team);
			owner.setUpdateDate(now);
			ownerRepository.save(owner);
			
		} catch (Exception e) {
			response.setError(new ErrorDto(AccountError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}

	@Override
	public String accountCreate(AccountCreateRequest request) {
		try {
			
			// verify the username does not already exist
			if (userRepository.findByUserName(request.getUserName()) != null ||
					ownerRepository.findByEmail(request.getEmail1()) != null) {
				LOG.error(UIMessages.ERROR_USERNAME_OR_PRIMARY_EMAIL_EXISTS_ON_CREATE);
				return UIMessages.ERROR_USERNAME_OR_PRIMARY_EMAIL_EXISTS_ON_CREATE;
			}
			
			// we need the user group model for the user repo
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
			emailManager.sendEmail(request.getEmail1(), UIMessages.EMAIL_WELCOME_SUBJECT,
					UIMessages.EMAIL_WELCOME_MESSAGE);

			// notify the admin a new account has been created
			emailManager.sendEmail(emailConfig.getEmailUser(), UIMessages.EMAIL_ADMIN_NEW_ACCOUNT_SUBJECT,
					UIMessages.EMAIL_ADMIN_NEW_ACCOUNT_MESSAGE);

			return UIMessages.MSG_GENERIC_CHECK_EMAIL;
		} catch (Exception e) {
			String error = UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION;
			LOG.error(error, e);
			return error;
		}
	}

	@Override
	public AccountResponse accountUpdate(AccountRequest request) {
		// validate user group access
		AccountResponse response =
				accountAccessError(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_OWNER);
		if (response.getError() != null) {
			return response;
		}
		try {
		// retrieve the existing owner record
		OwnerModel owner = ownerRepository.findByUserName(request.getUserName());
		
		// verify the primary email to ensure it is not already assigned to a different user
		OwnerModel verifyOwner = ownerRepository.findByEmail(request.getEmail1());
		if (verifyOwner != null && 
				!verifyOwner.getUserModel().getUserName().equals(request.getUserName())) {
			ErrorDto error = 
					new ErrorDto(AccountError.toString(), UIMessages.ACCOUNT_UPDATE_PRIMARY_EMAIL_NOT_UNIQUE);
			LOG.error(error.getMessage());
			response.setError(error);
			return response;
		}

		// set the new values and save
		owner.setEmail1(request.getEmail1());
		owner.setEmail2(request.getEmail2());
		owner.setEmail3(request.getEmail3());
		owner.setFirstName(request.getFirstName());
		owner.setLastName(request.getLastName());
		owner.setUpdateDate(new Date());
		
		ownerRepository.save(owner);
		
		} catch (Exception e) {
			response.setError(new ErrorDto(AccountError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
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
