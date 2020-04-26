package org.apcffl.api.controller.comm;

import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.comm.dto.MessageBoardRequest;
import org.apcffl.api.comm.dto.MessageBoardResponse;
import org.apcffl.api.comm.service.MessageBoardService;
import org.apcffl.api.controller.ApcfflController;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.service.manager.SessionManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Communication Services")
@RestController
@RequestMapping("/communications")
public class CommunicationsController extends ApcfflController {

	private final MessageBoardService messageBoardService;
	
	
	public CommunicationsController(final MessageBoardService messageBoardService, final SessionManager sessionManager) {
		this.messageBoardService = messageBoardService;
		this.sessionManager = sessionManager;
	}
	
	@ApiOperation(value="Create new Message Board message", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = MessageBoardResponse.class)
	@RequestMapping(value="/newMessageBoard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MessageBoardResponse> newMessageBoard(@RequestBody MessageBoard request) {
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			MessageBoardResponse response = new MessageBoardResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		MessageBoardResponse response = messageBoardService.newMessage(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value="Find all Message Board messages", httpMethod = "POST",  consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE, response = MessageBoardResponse.class)
	@RequestMapping(value="/allMessageBoard", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MessageBoardResponse> allMessageBoard(@RequestBody MessageBoardRequest request) {
		// validate the session token
		ErrorDto error = isValidSessionToken(request.getSecurityToken(), request.getUserName());
		if (error != null) {
			MessageBoardResponse response = new MessageBoardResponse();
			response.setError(error);
			return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
		}
		
		MessageBoardResponse response = messageBoardService.findAll(request);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
