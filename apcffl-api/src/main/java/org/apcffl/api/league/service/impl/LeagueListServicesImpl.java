package org.apcffl.api.league.service.impl;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.*;

import java.util.List;

import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.LeagueListsResponse;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.mapper.LeagueMapper;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.apcffl.api.service.ApcfflService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LeagueListServicesImpl extends ApcfflService implements LeagueListServices {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueListServicesImpl.class);
	
	private final LeagueRepository leagueRepository;
	private final OwnerRepository ownerRepository;
	
	public LeagueListServicesImpl(final LeagueRepository leagueRepository, final OwnerRepository ownerRepository) {
		this.leagueRepository = leagueRepository;
		this.ownerRepository = ownerRepository;
	}

	@Override
	public LeagueListsResponse allLeagues(ApiRequest request) {
		LeagueListsResponse response = new LeagueListsResponse();
		
		// validate user group access
		ErrorDto error = 
			validateGroupRole(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_ADMIN);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
			return response;
		}
		try {
			List<LeagueModel> models = leagueRepository.findAll();
			response.setLeagues(LeagueMapper.convertLeagueList(models));
		} catch (Exception e) {
			response.setError(new ErrorDto(LeagueError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}

	@Override
	public LeagueOwnersResponse leagueOwners(LeagueOwnersRequest request) {
		LeagueOwnersResponse response = new LeagueOwnersResponse();
		
		// validate user group access
		ErrorDto error = 
			validateGroupRole(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_ADMIN);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
			return response;
		}
		try {
			List<OwnerModel> owners = 
					ownerRepository.findByLeague(request.getOwnerLeagueName());
			response.setLeagueOwners(LeagueMapper.convertLeagueOwners(owners));
		} catch (Exception e) {
			response.setError(new ErrorDto(LeagueError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}

}
