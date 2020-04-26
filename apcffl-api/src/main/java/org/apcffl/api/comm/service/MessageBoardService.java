package org.apcffl.api.comm.service;

import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.comm.dto.MessageBoardRequest;
import org.apcffl.api.comm.dto.MessageBoardResponse;

public interface MessageBoardService {

	MessageBoardResponse newMessage(MessageBoard request);
	
	MessageBoardResponse findAll(MessageBoardRequest request);
}
