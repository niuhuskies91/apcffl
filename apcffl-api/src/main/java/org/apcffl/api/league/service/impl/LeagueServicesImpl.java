package org.apcffl.api.league.service.impl;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.LeagueError;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.Team;
import org.apcffl.api.league.dto.TeamsDivisionAssignmentRequest;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.league.service.LeagueServices;
import org.apcffl.api.league.service.helper.LeagueServiceHelper;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.TeamModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.persistence.repository.TeamRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.apcffl.api.service.ApcfflService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LeagueServicesImpl extends ApcfflService implements LeagueServices {

	private static final Logger LOG = LoggerFactory.getLogger(LeagueServicesImpl.class);

	private final LeagueListServices leagueListServices;
	private final LeagueRepository leagueRepository;
	private final TeamRepository teamRepository;
	
	public LeagueServicesImpl(final LeagueListServices leagueListServices, final LeagueRepository leagueRepository,
			final TeamRepository teamRepository) {
		this.leagueListServices = leagueListServices;
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
	}

	@Override
	public LeagueOwnersResponse teamsDivisionAssignment(TeamsDivisionAssignmentRequest request) {
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
			// get all divisions for leagueName
			
			Map<String, DivisionModel> divisionByName =
					getDivisionsByName(request.getOwnerLeagueName());
			
			error = LeagueServiceHelper.validateDivisionNames(request.getTeams(), divisionByName);
			if (error != null) {
				LOG.error(error.getMessage());
				response.setError(error);
				return response;
			}
			
			// set divisions for teams
			setDivisionsForTeams(request, divisionByName);
			
			// return all league owners after division re-alignment
			response = retrieveLeagueOwnersWithDivisionChanges(request);
			
		} catch (Exception e) {
			response.setError(new ErrorDto(LeagueError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}
	
	private void setDivisionsForTeams(TeamsDivisionAssignmentRequest request, Map<String, DivisionModel> divisionByName) {
		List<TeamModel> teamModels = teamRepository.findByLeagueName(request.getOwnerLeagueName());
		Map<String,TeamModel> teamByName =
				teamModels.stream().collect(Collectors.toMap(TeamModel::getTeamName, t -> t));
		
		for (Team team : request.getTeams()) {
			TeamModel teamModel = teamByName.get(team.getTeamName());
			String divisionName = team.getDivisionName();
			if (StringUtils.isEmpty(divisionName)) {
				teamModel.setDivisionModel(null);
			} else {
				DivisionModel divisionModel = divisionByName.get(divisionName);
				teamModel.setDivisionModel(divisionModel);
			}
		}
		teamRepository.saveAll(teamModels);
	}
	
	private LeagueOwnersResponse retrieveLeagueOwnersWithDivisionChanges(TeamsDivisionAssignmentRequest request) {

		LeagueOwnersRequest outputsRequest = new LeagueOwnersRequest();
		outputsRequest.setLeagueName(request.getLeagueName());
		outputsRequest.setSecurityToken(request.getSecurityToken());
		outputsRequest.setUserGroupName(request.getUserGroupName());
		outputsRequest.setUserName(request.getUserName());
		outputsRequest.setOwnerLeagueName(request.getOwnerLeagueName());
		
		return leagueListServices.leagueOwners(outputsRequest);
	}
	
	private Map<String, DivisionModel> getDivisionsByName(String leagueName) {
		
		Set<DivisionModel> divisions = 
				leagueRepository.findDivisionsForLeague(leagueName);
		
		return divisions.stream().collect(Collectors.toMap(DivisionModel::getDivisionName, d -> d));
	}
}
