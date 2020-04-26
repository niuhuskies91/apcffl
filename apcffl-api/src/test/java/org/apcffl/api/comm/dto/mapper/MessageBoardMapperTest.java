package org.apcffl.api.comm.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.persistence.model.MessageBoardModel;
import org.junit.Test;

public class MessageBoardMapperTest {
	
	@Test
	public void verify_map_noModelsProvided() {
		
		// prepare test data
		
		List<MessageBoardModel> input = null;
		
		// invoke
		
		List<MessageBoard> result = MessageBoardMapper.map(input);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_map_emptyModelsProvided() {
		
		// prepare test data
		
		List<MessageBoardModel> input = Collections.emptyList();
		
		// invoke
		
		List<MessageBoard> result = MessageBoardMapper.map(input);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_map_noAssociatedLeague() {
		
		// prepare test data
		
		MessageBoardModel model = ApcfflTest.buildMessageBoardModel();
		
		model.getOwnerModel().setLeagueModel(null);
		
		List<MessageBoardModel> input = Arrays.asList(model);
		
		// invoke
		
		List<MessageBoard> result = MessageBoardMapper.map(input);
		
		// verify
		
		assertEquals(0, result.size());
	}
	
	@Test
	public void verify_map() {
		
		// prepare test data
		
		MessageBoardModel model = ApcfflTest.buildMessageBoardModel();
		
		List<MessageBoardModel> input = Arrays.asList(model);
		
		// invoke
		
		List<MessageBoard> result = MessageBoardMapper.map(input);
		
		// verify
		
		assertEquals(1, result.size());
		MessageBoard messageBoard = result.get(0);
		assertEquals(ApcfflTest.TEST_DATE, messageBoard.getCreateDate());
		assertEquals(ApcfflTest.MESSAGE_BOARD_MSG, messageBoard.getMessage());
	}
}
