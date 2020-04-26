package org.apcffl.api.comm.service.impl;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.comm.dto.MessageBoardRequest;
import org.apcffl.api.comm.dto.MessageBoardResponse;
import org.apcffl.api.constants.UIMessages;

import static org.apcffl.api.exception.constants.Enums.ErrorCodeEnums.*;

import org.apcffl.api.persistence.model.MessageBoardModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.repository.MessageBoardRepository;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("local")
public class MessageBoardServiceImplTest {
	
	private MessageBoardServiceImpl service;

	@Mock
	private MessageBoardRepository messageBoardRepository;

	@Mock
	private OwnerRepository ownerRepository;
	
	@Captor
	private ArgumentCaptor<Date> startDateCaptor;
	
	@Captor
	private ArgumentCaptor<Date> endDateCaptor;
	
	@Captor
	private ArgumentCaptor<String> leagueNameCaptor;
	
	@Captor
	private ArgumentCaptor<String> userNameCaptor;
	
	@Captor
	private ArgumentCaptor<MessageBoardModel> messageBoardModelCaptor;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		service = new MessageBoardServiceImpl(messageBoardRepository, ownerRepository);
		
	    final Logger logger = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	    logger.setLevel(Level.DEBUG);
	}
	
	@Test
	public void verify_findAll_userGroupInvalid() {
		
		// prepare test data
		
		MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_GUEST);
		
		// invoke 
		
		MessageBoardResponse response = service.findAll(request);
		
		// verify
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		assertEquals(null, response.getMessageBoard());
		
		verify(messageBoardRepository, never()).findByDateRange(
				startDateCaptor.capture(), endDateCaptor.capture(), leagueNameCaptor.capture());
	}
	
	@Test
	public void verify_findAll_noAssociatedLeague() {
		
		// prepare test data
		
		MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
		
		request.setLeagueName(null);
		
		// invoke 
		
		MessageBoardResponse response = service.findAll(request);
		
		// verify
		
		assertEquals(LeagueNotAssignedError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_MISSING_LEAGUE_AFFILIATION, response.getError().getMessage());
		assertEquals(null, response.getMessageBoard());
		
		verify(messageBoardRepository, never()).findByDateRange(
				startDateCaptor.capture(), endDateCaptor.capture(), leagueNameCaptor.capture());
	}
	
	@Test
	public void verify_findAll_nullStartDate_param() {
		
		// prepare test data
		
		MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
		request.setStartDate(null);
		
		MessageBoardModel mockModel = ApcfflTest.buildMessageBoardModel();
		List<MessageBoardModel> mockModelList = Arrays.asList(mockModel);
		when(messageBoardRepository.findByDateRange(any(), any(), anyString()))
		.thenReturn(mockModelList);
		
		// invoke
		
		MessageBoardResponse response = service.findAll(request);
		
		// verify
		
		assertEquals(null, response.getError());
		assertEquals(1, response.getMessageBoard().size());
		MessageBoard result = response.getMessageBoard().get(0);
		assertEquals(ApcfflTest.TEST_DATE, result.getCreateDate());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, result.getLeagueName());
		assertEquals(ApcfflTest.MESSAGE_BOARD_MSG, result.getMessage());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, result.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, result.getUserName());
		
		verify(messageBoardRepository, times(1)).findByDateRange(
				startDateCaptor.capture(), endDateCaptor.capture(), leagueNameCaptor.capture());
		
		assertNotNull(startDateCaptor.getValue());
		assertNotEquals(ApcfflTest.TEST_DATE, startDateCaptor.getValue());
		assertNotNull(endDateCaptor.getValue());
		assertNotEquals(ApcfflTest.TEST_DATE, endDateCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
	}
	
	@Test
	public void verify_findAll_nullEndDate_param() {
		
		// prepare test data
		
		MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
		request.setEndDate(null);
		
		MessageBoardModel mockModel = ApcfflTest.buildMessageBoardModel();
		List<MessageBoardModel> mockModelList = Arrays.asList(mockModel);
		when(messageBoardRepository.findByDateRange(any(), any(), anyString()))
		.thenReturn(mockModelList);
		
		// invoke
		
		MessageBoardResponse response = service.findAll(request);
		
		// verify
		
		assertEquals(null, response.getError());
		assertEquals(1, response.getMessageBoard().size());
		MessageBoard result = response.getMessageBoard().get(0);
		assertEquals(ApcfflTest.TEST_DATE, result.getCreateDate());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, result.getLeagueName());
		assertEquals(ApcfflTest.MESSAGE_BOARD_MSG, result.getMessage());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, result.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, result.getUserName());
		
		verify(messageBoardRepository, times(1)).findByDateRange(
				startDateCaptor.capture(), endDateCaptor.capture(), leagueNameCaptor.capture());
		
		assertNotNull(startDateCaptor.getValue());
		assertNotEquals(ApcfflTest.TEST_DATE, startDateCaptor.getValue());
		assertNotNull(endDateCaptor.getValue());
		assertNotEquals(ApcfflTest.TEST_DATE, endDateCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
	}
	
	@Test
	public void verify_findAll() {
		
		// prepare test data
		
		MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
		
		MessageBoardModel mockModel = ApcfflTest.buildMessageBoardModel();
		List<MessageBoardModel> mockModelList = Arrays.asList(mockModel);
		when(messageBoardRepository.findByDateRange(any(), any(), anyString()))
		.thenReturn(mockModelList);
		
		// invoke
		
		MessageBoardResponse response = service.findAll(request);
		
		// verify
		
		assertEquals(null, response.getError());
		assertEquals(1, response.getMessageBoard().size());
		MessageBoard result = response.getMessageBoard().get(0);
		assertEquals(ApcfflTest.TEST_DATE, result.getCreateDate());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, result.getLeagueName());
		assertEquals(ApcfflTest.MESSAGE_BOARD_MSG, result.getMessage());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, result.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, result.getUserName());
		
		verify(messageBoardRepository, times(1)).findByDateRange(
				startDateCaptor.capture(), endDateCaptor.capture(), leagueNameCaptor.capture());
		
		assertNotNull(startDateCaptor.getValue());
		assertEquals(ApcfflTest.TEST_DATE, startDateCaptor.getValue());
		assertNotNull(endDateCaptor.getValue());
		assertEquals(ApcfflTest.TEST_DATE, endDateCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
	}
	
	@Test
	public void verify_newMessage_userGroupInvalid() {
		
		// prepare test data
		
		MessageBoard request = ApcfflTest.buildMessageBoard();
		
		request.setUserGroupName(ApcfflTest.USER_GROUP_GUEST);
		
		// invoke 
		
		MessageBoardResponse response = service.newMessage(request);
		
		// verify
		
		assertEquals(UserGroupAccessError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_USER_GROUP_ACCESS, response.getError().getMessage());
		assertEquals(null, response.getMessageBoard());
		
		verify(ownerRepository, never()).findByUserName(userNameCaptor.capture());
		
		verify(messageBoardRepository, never()).save(messageBoardModelCaptor.capture());
	}
	
	@Test
	public void verify_newMessage_noAssociatedLeague() {
		
		// prepare test data
		
		MessageBoard request = ApcfflTest.buildMessageBoard();
		
		request.setLeagueName(null);
		
		// invoke 
		
		MessageBoardResponse response = service.newMessage(request);
		
		// verify
		
		assertEquals(LeagueNotAssignedError.toString(), response.getError().getErrorCode());
		assertEquals(UIMessages.ERROR_MISSING_LEAGUE_AFFILIATION, response.getError().getMessage());
		assertEquals(null, response.getMessageBoard());

		verify(ownerRepository, never()).findByUserName(userNameCaptor.capture());
		
		verify(messageBoardRepository, never()).save(messageBoardModelCaptor.capture());
	}
	
	@Test
	public void verify_newMessage() {
		
		// prepare test data
		
		MessageBoard request = ApcfflTest.buildMessageBoard();
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		when(messageBoardRepository.save(any())).thenReturn(new MessageBoardModel());
		
		MessageBoardModel mockModel = ApcfflTest.buildMessageBoardModel();
		List<MessageBoardModel> mockModelList = Arrays.asList(mockModel);
		when(messageBoardRepository.findByDateRange(any(), any(), anyString()))
		.thenReturn(mockModelList);
		
		// invoke 
		
		MessageBoardResponse response = service.newMessage(request);
		
		// verify
		
		assertEquals(null, response.getError());
		assertEquals(1, response.getMessageBoard().size());
		MessageBoard result = response.getMessageBoard().get(0);
		assertEquals(ApcfflTest.TEST_DATE, result.getCreateDate());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, result.getLeagueName());
		assertEquals(ApcfflTest.MESSAGE_BOARD_MSG, result.getMessage());
		assertEquals(ApcfflTest.USER_GROUP_OWNER, result.getUserGroupName());
		assertEquals(ApcfflTest.USER_NAME, result.getUserName());

		verify(ownerRepository, times(1)).findByUserName(userNameCaptor.capture());
		assertEquals(ApcfflTest.USER_NAME, userNameCaptor.getValue());
		
		verify(messageBoardRepository, times(1)).save(messageBoardModelCaptor.capture());
		
		verify(messageBoardRepository, times(1)).findByDateRange(
				startDateCaptor.capture(), endDateCaptor.capture(), leagueNameCaptor.capture());
		
		assertNotNull(startDateCaptor.getValue());
		assertNotEquals(ApcfflTest.TEST_DATE, startDateCaptor.getValue());
		assertNotNull(endDateCaptor.getValue());
		assertNotEquals(ApcfflTest.TEST_DATE, endDateCaptor.getValue());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, leagueNameCaptor.getValue());
	}
}
