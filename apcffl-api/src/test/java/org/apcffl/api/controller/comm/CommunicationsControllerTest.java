package org.apcffl.api.controller.comm;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import org.apcffl.ApcfflTest;
import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.comm.dto.MessageBoardRequest;
import org.apcffl.api.comm.dto.MessageBoardResponse;
import org.apcffl.api.comm.service.MessageBoardService;
import org.apcffl.api.service.manager.SessionManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CommunicationsController.class)
public class CommunicationsControllerTest {
	
	private static final String MESSAGE_BOARD_NEW_MESSAGE_URL = "/communications/newMessageBoard";
	private static final String MESSAGE_BOARD_FIND_ALL_URL    = "/communications/allMessageBoard";

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private MessageBoardService messageBoardService;
	
	@MockBean
	SessionManager sessionManager;
	
	@Captor
	private ArgumentCaptor<MessageBoard> messageBoardCaptor;
	
	@Captor
	private ArgumentCaptor<MessageBoardRequest> messageBoardRequestCaptor;
	
	private ObjectMapper objectMapper;
	
    @Before
    public void setUp() {
		MockitoAnnotations.initMocks(this);
		
    	objectMapper = new ObjectMapper();
		
		sessionManager.init();
		when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(true);
    }
    
    @Test
    public void verify_newMessage_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	MessageBoard request = ApcfflTest.buildMessageBoard();
    	
    	when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
    	
    	// invoke and verify
		
		String jsonRequest = objectMapper.writeValueAsString(request);
    	
    	mockMvc.perform(
    			post(MESSAGE_BOARD_NEW_MESSAGE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isUnauthorized());
    	
		verify(sessionManager, times(1)).isValidSessionToken(anyString(), anyString());
		
		verify(messageBoardService, never()).newMessage(messageBoardCaptor.capture());
    }
    
    @Test
    public void verify_newMessage() throws Exception {
    	
    	// prepare test data
    	
    	MessageBoard request = ApcfflTest.buildMessageBoard();
    	
    	// invoke and verify
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		MessageBoardResponse mockResponse = new MessageBoardResponse();
		mockResponse.setMessageBoard(Arrays.asList(request));
		when(messageBoardService.newMessage(any())).thenReturn(mockResponse);
    	
    	mockMvc.perform(
    			post(MESSAGE_BOARD_NEW_MESSAGE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$.messageBoard.[0].leagueName").value(ApcfflTest.LEAGUE_1_NAME))
    			.andExpect(jsonPath("$.messageBoard.[0].userName").value(ApcfflTest.USER_NAME))
    			.andExpect(jsonPath("$.messageBoard.[0].message").value(ApcfflTest.MESSAGE_BOARD_MSG));
    	
		verify(sessionManager, times(1)).isValidSessionToken(anyString(), anyString());
		
		verify(messageBoardService, times(1)).newMessage(messageBoardCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, messageBoardCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.USER_NAME, messageBoardCaptor.getValue().getUserName());
		assertEquals(ApcfflTest.MESSAGE_BOARD_MSG, messageBoardCaptor.getValue().getMessage());
    }
    
    @Test
    public void verify_allMessageBoard_invalidSessionToken() throws Exception {
    	
    	// prepare test data
    	
    	MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
    	
    	when(sessionManager.isValidSessionToken(anyString(), anyString())).thenReturn(false);
    	
    	// invoke and verify
		
		String jsonRequest = objectMapper.writeValueAsString(request);
    	
    	mockMvc.perform(
    			post(MESSAGE_BOARD_FIND_ALL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isUnauthorized());
    	
		verify(sessionManager, times(1)).isValidSessionToken(anyString(), anyString());
		
		verify(messageBoardService, never()).findAll(messageBoardRequestCaptor.capture());
    }
    
    @Test
    public void verify_allMessageBoard() throws Exception {
    	
    	// prepare test data
    	
    	MessageBoardRequest request = ApcfflTest.buildMessageBoardRequest();
    	
    	// invoke and verify
		
		String jsonRequest = objectMapper.writeValueAsString(request);
		
		MessageBoardResponse mockResponse = new MessageBoardResponse();
		MessageBoard mockMessage = ApcfflTest.buildMessageBoard();
		mockResponse.setMessageBoard(Arrays.asList(mockMessage));
		when(messageBoardService.findAll(any())).thenReturn(mockResponse);
    	
    	mockMvc.perform(
    			post(MESSAGE_BOARD_FIND_ALL_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonRequest)
				.accept(MediaType.APPLICATION_JSON))
    			.andDo(print())
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$.messageBoard.[0].leagueName").value(ApcfflTest.LEAGUE_1_NAME))
    			.andExpect(jsonPath("$.messageBoard.[0].userName").value(ApcfflTest.USER_NAME))
    			.andExpect(jsonPath("$.messageBoard.[0].message").value(ApcfflTest.MESSAGE_BOARD_MSG));
    	
		verify(sessionManager, times(1)).isValidSessionToken(anyString(), anyString());
		
		verify(messageBoardService, times(1)).findAll(messageBoardRequestCaptor.capture());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, messageBoardRequestCaptor.getValue().getLeagueName());
		assertEquals(ApcfflTest.USER_NAME, messageBoardRequestCaptor.getValue().getUserName());
		assertEquals(ApcfflTest.TEST_DATE, messageBoardRequestCaptor.getValue().getStartDate());
		assertEquals(ApcfflTest.TEST_DATE, messageBoardRequestCaptor.getValue().getEndDate());
    }
}
