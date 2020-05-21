package org.apcffl.api.league.service.impl;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.LeagueError;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.Team;
import org.apcffl.api.league.dto.TeamResponse;
import org.apcffl.api.league.dto.mapper.LeagueMapper;
import org.apcffl.api.league.dto.LeagueTeams;
import org.apcffl.api.league.dto.LeagueTeamsResponse;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.league.service.LeagueServices;
import org.apcffl.api.league.service.helper.LeagueServiceHelper;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.TeamModel;
import org.apcffl.api.persistence.model.TeamRosterModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.persistence.repository.TeamRepository;
import org.apcffl.api.persistence.repository.TeamRosterRepository;
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
	private final TeamRosterRepository teamRosterRepository;
	
	public LeagueServicesImpl(final LeagueListServices leagueListServices, final LeagueRepository leagueRepository,
			final TeamRepository teamRepository, final TeamRosterRepository teamRosterRepository) {
		this.leagueListServices = leagueListServices;
		this.leagueRepository = leagueRepository;
		this.teamRepository = teamRepository;
		this.teamRosterRepository = teamRosterRepository;
	}

	@Override
	public LeagueOwnersResponse teamsDivisionAssignment(LeagueTeams request) {
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
	
	@Override
	public TeamResponse teamRoster(ApiRequest request) {
		TeamResponse response = new TeamResponse();
		
		// validate user group access
		
		ErrorDto error = 
			validateGroupRole(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_OWNER);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
			return response;
		}
		try {
			List<TeamRosterModel> rosterModel = 
					teamRosterRepository.findByUserNameAndTeamName(request.getUserName(), request.getTeamName());
			
			Team team = new Team();
			team.setTeamName(request.getTeamName());
			team.setUserName(request.getUserName());
			team.setLeagueName(request.getLeagueName());
			team.setRoster(LeagueMapper.convertTeamRosters(rosterModel));
			response.setTeam(team);
		} catch (Exception e) {
			response.setError(new ErrorDto(LeagueError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}
	
	@Override
	public LeagueTeamsResponse activeLeagueRosters(LeagueTeams request) {
		LeagueTeamsResponse response = new LeagueTeamsResponse();
		
		// validate user group access
		
		ErrorDto error = 
			validateGroupRole(request.getUserGroupName(), SecurityConstants.USER_GROUP_TIER_ADMIN);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
			return response;
		}
		
		
		//TODO: build
		
		
		return null;
	}
	
	private void setDivisionsForTeams(LeagueTeams request, Map<String, DivisionModel> divisionByName) {
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
	
	private LeagueOwnersResponse retrieveLeagueOwnersWithDivisionChanges(LeagueTeams request) {

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
