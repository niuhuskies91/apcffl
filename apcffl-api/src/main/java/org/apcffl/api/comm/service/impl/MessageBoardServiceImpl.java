package org.apcffl.api.comm.service.impl;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.comm.dto.MessageBoardRequest;
import org.apcffl.api.comm.dto.MessageBoardResponse;
import org.apcffl.api.comm.dto.mapper.MessageBoardMapper;
import org.apcffl.api.comm.service.MessageBoardService;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.dto.ErrorDto;
import org.apcffl.api.persistence.model.MessageBoardModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.repository.MessageBoardRepository;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.apcffl.api.service.ApcfflService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.apcffl.api.constants.ApcfflConstants.MESSAGE_BOARD_DAYS_DEFAULT;
import static org.apcffl.api.constants.Enums.ErrorCodeEnums.AccountError;

@Service
@Transactional
public class MessageBoardServiceImpl extends ApcfflService implements MessageBoardService {

	private static final Logger LOG = LoggerFactory.getLogger(MessageBoardServiceImpl.class);

	private final MessageBoardRepository messageBoardRepository;
	private final OwnerRepository ownerRepository;

	public MessageBoardServiceImpl(final MessageBoardRepository messageBoardRepository,
			final OwnerRepository ownerRepository) {
		this.messageBoardRepository = messageBoardRepository;
		this.ownerRepository = ownerRepository;
	}

	@Override
	public MessageBoardResponse findAll(MessageBoardRequest request) {

		String leagueName = request.getLeagueName();

		// validate user group access
		MessageBoardResponse response = accountAccessError(request.getUserGroupName(), leagueName,
				SecurityConstants.USER_GROUP_TIER_OWNER);
		if (response.getError() != null) {
			return response;
		}

		Date startDate = null;
		Date endDate = null;

		// if no date range is specified use default date range
		if (request.getStartDate() == null || request.getEndDate() == null) {
			Calendar calendar = Calendar.getInstance();
			endDate = new Date(calendar.getTimeInMillis());
			calendar.add(Calendar.DATE, -MESSAGE_BOARD_DAYS_DEFAULT);
			startDate = new Date(calendar.getTimeInMillis());
		} else {
			// use specified date range
			startDate = new Date(request.getStartDate().getTime());
			endDate = new Date(request.getEndDate().getTime());
		}
		try {
			List<MessageBoardModel> result = 
					messageBoardRepository.findByDateRange(startDate, endDate, leagueName);
			response.setMessageBoard(MessageBoardMapper.map(result));

		} catch (Exception e) {
			response.setError(new ErrorDto(AccountError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}

	@Override
	public MessageBoardResponse newMessage(MessageBoard request) {

		String leagueName = request.getLeagueName();

		// validate user group access
		MessageBoardResponse response = accountAccessError(request.getUserGroupName(), leagueName,
				SecurityConstants.USER_GROUP_TIER_OWNER);
		if (response.getError() != null) {
			return response;
		}
		try {
			OwnerModel owner = ownerRepository.findByUserName(request.getUserName());

			MessageBoardModel messageBoard = new MessageBoardModel();
			messageBoard.setCreateDate(new Date(Calendar.getInstance().getTimeInMillis()));
			messageBoard.setMessage(request.getMessage());
			messageBoard.setOwnerModel(owner);
			messageBoard.setLeagueModel(owner.getTeamModel().getLeagueModel());

			// persist the message board

			messageBoardRepository.save(messageBoard);

			// retrieve all messages within the last default time

			Calendar calendar = Calendar.getInstance();
			Date endDate = new Date(calendar.getTimeInMillis());
			calendar.add(Calendar.DATE, -MESSAGE_BOARD_DAYS_DEFAULT);
			Date startDate = new Date(calendar.getTimeInMillis());

			List<MessageBoardModel> result = messageBoardRepository.findByDateRange(startDate, endDate, leagueName);

			response.setMessageBoard(MessageBoardMapper.map(result));
		} catch (Exception e) {
			response.setError(new ErrorDto(AccountError.toString(), UIMessages.ERROR_GENERAL_INTERNAL_EXCEPTION));
			LOG.error(response.getError().getMessage(), e);
		}
		return response;
	}

	private MessageBoardResponse accountAccessError(String userGroup, String leagueName, List<String> accessLevels) {
		MessageBoardResponse response = new MessageBoardResponse();

		ErrorDto error = validateGroupRole(userGroup, leagueName, accessLevels);
		if (error != null) {
			LOG.error(error.getMessage());
			response.setError(error);
		}
		return response;
	}

}
