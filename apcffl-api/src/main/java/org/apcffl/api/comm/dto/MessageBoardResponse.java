package org.apcffl.api.comm.dto;

import java.util.List;

import org.apcffl.api.dto.ApiResponse;

public class MessageBoardResponse extends ApiResponse {

	private List<MessageBoard> messageBoard;

	public List<MessageBoard> getMessageBoard() {
		return messageBoard;
	}

	public void setMessageBoard(List<MessageBoard> messageBoard) {
		this.messageBoard = messageBoard;
	}
	
}
