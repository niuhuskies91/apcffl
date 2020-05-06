package org.apcffl.api.league.service.impl;

import static org.apcffl.api.constants.Enums.ErrorCodeEnums.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ApiRequest;
import org.apcffl.api.league.dto.LeagueListsResponse;
import org.apcffl.api.persistence.model.LeagueModel;
import org.apcffl.api.persistence.repository.LeagueRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class LeagueListServicesImplTest {

	private LeagueListServicesImpl service;

	@Mock
	private LeagueRepository leagueRepository;
	
	private ApiRequest apiRequest;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		service = new LeagueListServicesImpl(leagueRepository);
		
		apiRequest = ApcfflTest.buildApiRequest();
		apiRequest.setUserGroupName(SecurityConstants.USER_GROUP_ADMIN);
		
	    final Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    logger.setLevel(Level.DEBUG);
	}
	
	@Test
	public void verify_allLeagues_findAllException() {
		
		// prepare test data
		
		when(leagueRepository.findAll()).thenThrow(new NullPointerException("error"));
		
		// invoke
		
		LeagueListsResponse response = service.allLeagues(apiRequest);
		
		// verify
		
		assertEquals(LeagueError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION, response.getError().getMessage());
		assertEquals(null, response.getLeagues());
		
		verify(leagueRepository, times(1)).findAll();
	}
	
	@Test
	public void verify_allLeagues_userGroupNotAdmin() {
		
		// prepare test data
		
		apiRequest.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		List<LeagueModel> mockLeagues = ApcfflTest.buildLeagueModels();
		when(leagueRepository.findAll()).thenReturn(mockLeagues);
		
		// invoke
		
		LeagueListsResponse response = service.allLeagues(apiRequest);
		
		// verify
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		assertEquals(null, response.getLeagues());
		
		verify(leagueRepository, never()).findAll();
	}
	
	@Test
	public void verify_allLeagues() {
		
		// prepare test data
		
		List<LeagueModel> mockLeagues = ApcfflTest.buildLeagueModels();
		when(leagueRepository.findAll()).thenReturn(mockLeagues);
		
		// invoke
		
		LeagueListsResponse response = service.allLeagues(apiRequest);
		
		// verify
		
		assertEquals(null, response.getError());
		assertEquals(2, response.getLeagues().size());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, response.getLeagues().get(0).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_DIV, response.getLeagues().get(0).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_1_NUM_TEAMS, response.getLeagues().get(0).getNumTeams());
		assertEquals(ApcfflTest.LEAGUE_2_NAME, response.getLeagues().get(1).getLeagueName());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_DIV, response.getLeagues().get(1).getNumDivisions());
		assertEquals(ApcfflTest.LEAGUE_2_NUM_TEAMS, response.getLeagues().get(1).getNumTeams());
		
		verify(leagueRepository, times(1)).findAll();
	}
}
