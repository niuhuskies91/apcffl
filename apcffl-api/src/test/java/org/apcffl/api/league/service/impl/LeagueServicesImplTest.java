package org.apcffl.api.league.service.impl;

import org.apcffl.ApcfflTest;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.LeagueTeams;
import org.apcffl.api.league.dto.TeamResponse;
import org.apcffl.api.league.dto.TeamRoster;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.TeamModel;
import org.apcffl.api.persistence.model.TeamRosterModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.persistence.repository.TeamRepository;
import org.apcffl.api.persistence.repository.TeamRosterRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.apcffl.api.constants.Enums.ErrorCodeEnums.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class LeagueServicesImplTest {
	
	private LeagueServicesImpl leagueServices;

	@Mock
	private LeagueListServices leagueListServices;

	@Mock
	private LeagueRepository leagueRepository;

	@Mock
	private TeamRepository teamRepository;
	
	@Mock
	private TeamRosterRepository teamRosterRepository;
	
	@Captor
	private ArgumentCaptor<String> leagueNameCaptor;
	
	@Captor
	private ArgumentCaptor<String> userNameCaptor;
	
	@Captor
	private ArgumentCaptor<String> teamNameCaptor;
	
	@Captor
	private ArgumentCaptor<List<TeamModel>> teamModelsCaptor;
	
	@Captor
	private ArgumentCaptor<LeagueOwnersRequest> leagueOwnersRequestCaptor;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		leagueServices = 
				new LeagueServicesImpl(leagueListServices, leagueRepository, teamRepository, teamRosterRepository);

	    final Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    logger.setLevel(Level.DEBUG);
	}
	
	@Test
	public void verify_teamsDivisionAssignment_invalidGroupTier() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, never()).findDivisionsForLeague(leagueNameCaptor.capture());
		
		verify(teamRepository, never()).findByLeagueName(leagueNameCaptor.capture());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void verify_teamsDivisionAssignment_findDivisionsForLeague_exception() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		when(leagueRepository.findDivisionsForLeague(anyString()))
		.thenThrow(new NullPointerException("error"));
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify
		
		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).findByLeagueName(leagueNameCaptor.capture());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void verify_teamsDivisionAssignment_findByLeagueName_exception() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenThrow(new NullPointerException("error"));
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify

		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void verify_teamsDivisionAssignment_saveAll_exception() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenThrow(new NullPointerException("error"));
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify

		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).saveAll(teamModelsCaptor.capture());
		assertEquals(1, teamModelsCaptor.getValue().size());
		TeamModel teamModel = teamModelsCaptor.getValue().get(0);
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamModel.getTeamName());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void verify_teamsDivisionAssignment_leagueOwners_exception() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		when(leagueListServices.leagueOwners(any())).thenThrow(new NullPointerException("error"));
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify

		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).saveAll(teamModelsCaptor.capture());
		assertEquals(1, teamModelsCaptor.getValue().size());
		TeamModel teamModel = teamModelsCaptor.getValue().get(0);
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamModel.getTeamName());
		
		verify(leagueListServices, times(1)).leagueOwners(leagueOwnersRequestCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueOwnersRequestCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueOwnersRequestCaptor.getValue().getOwnerLeagueName());
		assertEquals(ApcfflTest.TEST_TOKEN, leagueOwnersRequestCaptor.getValue().getSecurityToken());
		assertEquals(ApcfflTest.USER_GROUP_ADMIN, leagueOwnersRequestCaptor.getValue().getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, leagueOwnersRequestCaptor.getValue().getUserName());
	}
	
	@Test
	public void verify_teamsDivisionAssignment_invalidAssigned_divisionNames() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		request.getTeams().get(0).setDivisionName("invalid");
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify
		
		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.LEAGUE_DIVISION_NAME_NOT_MATCH, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).findByLeagueName(leagueNameCaptor.capture());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void verify_teamsDivisionAssignment_null_divisionName() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		request.getTeams().get(0).setDivisionName(null);
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		mockLeagueOwners.getLeagueOwners().get(0).setDivisionName(null);
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify
		
		assertEquals(null, response.getError());
		
		assertEquals(1, response.getLeagueOwners().size());
		assertEquals(null, response.getLeagueOwners().get(0).getDivisionName());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.getLeagueOwners().get(0).getEmail());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.getLeagueOwners().get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.getLeagueOwners().get(0).getLastName());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, response.getLeagueOwners().get(0).getTeamName());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).saveAll(teamModelsCaptor.capture());
		assertEquals(1, teamModelsCaptor.getValue().size());
		TeamModel teamModel = teamModelsCaptor.getValue().get(0);
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamModel.getTeamName());
		
		verify(leagueListServices, times(1)).leagueOwners(leagueOwnersRequestCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueOwnersRequestCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueOwnersRequestCaptor.getValue().getOwnerLeagueName());
		assertEquals(ApcfflTest.TEST_TOKEN, leagueOwnersRequestCaptor.getValue().getSecurityToken());
		assertEquals(ApcfflTest.USER_GROUP_ADMIN, leagueOwnersRequestCaptor.getValue().getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, leagueOwnersRequestCaptor.getValue().getUserName());
	}
	
	@Test
	public void verify_teamsDivisionAssignment() {
		
		// prepare test data
		
		LeagueTeams request = 
				ApcfflTest.buildTeamsDivisionAssignmentRequest();
		
		Set<DivisionModel> mockDivisionModels = ApcfflTest.buildDivisionModels();
		when(leagueRepository.findDivisionsForLeague(anyString())).thenReturn(mockDivisionModels);
		
		List<TeamModel> mockTeamModels = Arrays.asList(ApcfflTest.buildTeamModel());
		when(teamRepository.findByLeagueName(anyString())).thenReturn(mockTeamModels);
		
		when(teamRepository.saveAll(any())).thenReturn(mockTeamModels);
		
		LeagueOwnersResponse mockLeagueOwners = ApcfflTest.buildLeagueOwnersResponse();
		when(leagueListServices.leagueOwners(any())).thenReturn(mockLeagueOwners);
		
		// invoke
		
		LeagueOwnersResponse response = leagueServices.teamsDivisionAssignment(request);
		
		// verify
		
		assertEquals(null, response.getError());
		
		assertEquals(1, response.getLeagueOwners().size());
		assertEquals(ApcfflTest.LEAGUE_1_DIV_1, response.getLeagueOwners().get(0).getDivisionName());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.getLeagueOwners().get(0).getEmail());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.getLeagueOwners().get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.getLeagueOwners().get(0).getLastName());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, response.getLeagueOwners().get(0).getTeamName());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).saveAll(teamModelsCaptor.capture());
		assertEquals(1, teamModelsCaptor.getValue().size());
		TeamModel teamModel = teamModelsCaptor.getValue().get(0);
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamModel.getTeamName());
		
		verify(leagueListServices, times(1)).leagueOwners(leagueOwnersRequestCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueOwnersRequestCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueOwnersRequestCaptor.getValue().getOwnerLeagueName());
		assertEquals(ApcfflTest.TEST_TOKEN, leagueOwnersRequestCaptor.getValue().getSecurityToken());
		assertEquals(ApcfflTest.USER_GROUP_ADMIN, leagueOwnersRequestCaptor.getValue().getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, leagueOwnersRequestCaptor.getValue().getUserName());
	}
	
	@Test
	public void verify_teamRoster_invalidGroupTier() {
		
		// prepare test data
		
		ApiRequest request = 
				ApcfflTest.buildApiRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_GUEST);
		
		List<TeamRosterModel> mockTeamRosterModels = ApcfflTest.buildTeamRosterModels();
		when(teamRosterRepository.findByUserNameAndTeamName(anyString(), anyString()))
		.thenReturn(mockTeamRosterModels);
		
		// invoke
		
		TeamResponse response = leagueServices.teamRoster(request);
		
		// verify
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getTeam());
		
		verify(teamRosterRepository, never())
		.findByUserNameAndTeamName(userNameCaptor.capture(), teamNameCaptor.capture());
	}
	
	@Test
	public void verify_teamRoster_exception() {
		
		// prepare test data
		
		ApiRequest request = 
				ApcfflTest.buildApiRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		
		when(teamRosterRepository.findByUserNameAndTeamName(anyString(), anyString()))
		.thenThrow(new NullPointerException("error"));
		
		// invoke
		
		TeamResponse response = leagueServices.teamRoster(request);
		
		// verify
		
		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION, response.getError().getMessage());
		
		assertEquals(null, response.getTeam());
		
		verify(teamRosterRepository, times(1))
		.findByUserNameAndTeamName(userNameCaptor.capture(), teamNameCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamNameCaptor.getValue());
	}
	
	@Test
	public void verify_teamRoster_nullRostersInRepository() {
		
		// prepare test data
		
		ApiRequest request = 
				ApcfflTest.buildApiRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		
		when(teamRosterRepository.findByUserNameAndTeamName(anyString(), anyString()))
		.thenReturn(null);
		
		// invoke
		
		TeamResponse response = leagueServices.teamRoster(request);
		
		// verify
		
		assertEquals(null, response.getError());
		
		assertEquals(0, response.getTeam().getRoster().size());
		
		verify(teamRosterRepository, times(1))
		.findByUserNameAndTeamName(userNameCaptor.capture(), teamNameCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamNameCaptor.getValue());
	}
	
	@Test
	public void verify_teamRoster_emptyRostersInRepository() {
		
		// prepare test data
		
		ApiRequest request = 
				ApcfflTest.buildApiRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		
		when(teamRosterRepository.findByUserNameAndTeamName(anyString(), anyString()))
		.thenReturn(Collections.emptyList());
		
		// invoke
		
		TeamResponse response = leagueServices.teamRoster(request);
		
		// verify

		assertEquals(null, response.getError());
		
		assertEquals(0, response.getTeam().getRoster().size());
		
		verify(teamRosterRepository, times(1))
		.findByUserNameAndTeamName(userNameCaptor.capture(), teamNameCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamNameCaptor.getValue());
	}
	
	@Test
	public void verify_teamRoster() {
		
		// prepare test data
		
		ApiRequest request = 
				ApcfflTest.buildApiRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		
		List<TeamRosterModel> mockTeamRosterModels = ApcfflTest.buildTeamRosterModels();
		when(teamRosterRepository.findByUserNameAndTeamName(anyString(), anyString()))
		.thenReturn(mockTeamRosterModels);
		
		// invoke
		
		TeamResponse response = leagueServices.teamRoster(request);
		
		// verify

		assertEquals(null, response.getError());
		
		assertEquals(ApcfflTest.USER_NAME, response.getTeam().getUserName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, response.getTeam().getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, response.getTeam().getTeamName());
		List<TeamRoster> resultRoster = response.getTeam().getRoster();
		assertEquals(2, resultRoster.size());
		assertEquals(ApcfflTest.SCHOLARSHIP_POINT_MIN, resultRoster.get(0).getScholarshipPoints());
		assertEquals(ApcfflTest.SCHOOL_NAME_BIG_10_1, resultRoster.get(0).getSchool().getSchoolName());
		assertEquals(ApcfflTest.SCHOLARSHIP_POINT_MIN, resultRoster.get(1).getScholarshipPoints());
		assertEquals(ApcfflTest.SCHOOL_NAME_BIG_10_2, resultRoster.get(1).getSchool().getSchoolName());
		
		verify(teamRosterRepository, times(1))
		.findByUserNameAndTeamName(userNameCaptor.capture(), teamNameCaptor.capture());
		
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_TEAM_1, teamNameCaptor.getValue());
	}
}
