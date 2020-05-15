package org.apcffl.api.league.service.impl;

import org.apcffl.ApcfflTest;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.league.dto.LeagueOwnersRequest;
import org.apcffl.api.league.dto.LeagueOwnersResponse;
import org.apcffl.api.league.dto.TeamsDivisionAssignmentRequest;
import org.apcffl.api.league.service.LeagueListServices;
import org.apcffl.api.persistence.model.DivisionModel;
import org.apcffl.api.persistence.model.TeamModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.persistence.repository.TeamRepository;
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
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
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
	
	@Captor
	private ArgumentCaptor<String> leagueNameCaptor;
	
	@Captor
	private ArgumentCaptor<List<TeamModel>> teamModelsCaptor;
	
	@Captor
	private ArgumentCaptor<LeagueOwnersRequest> leagueOwnersRequestCaptor;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		leagueServices = 
				new LeagueServicesImpl(leagueListServices, leagueRepository, teamRepository);

	    final Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    logger.setLevel(Level.DEBUG);
	}
	
	@Test
	public void teamsDivisionAssignment_invalidGroupTier() {
		
		// prepare test data
		
		TeamsDivisionAssignmentRequest request = 
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
	public void teamsDivisionAssignment_findDivisionsForLeague_exception() {
		
		// prepare test data
		
		TeamsDivisionAssignmentRequest request = 
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
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).findByLeagueName(leagueNameCaptor.capture());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void teamsDivisionAssignment_findByLeagueName_exception() {
		
		// prepare test data
		
		TeamsDivisionAssignmentRequest request = 
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
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void teamsDivisionAssignment_invalidGroupTier() {
		
		// prepare test data
		
		TeamsDivisionAssignmentRequest request = 
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
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void teamsDivisionAssignment_invalidGroupTier() {
		
		// prepare test data
		
		TeamsDivisionAssignmentRequest request = 
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
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
	
	@Test
	public void teamsDivisionAssignment_invalidGroupTier() {
		
		// prepare test data
		
		TeamsDivisionAssignmentRequest request = 
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
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		
		assertEquals(null, response.getLeagueOwners());
		
		verify(leagueRepository, times(1)).findDivisionsForLeague(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, times(1)).findByLeagueName(leagueNameCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
		
		verify(teamRepository, never()).saveAll(teamModelsCaptor.capture());
		
		verify(leagueListServices, never()).leagueOwners(leagueOwnersRequestCaptor.capture());
	}
}
