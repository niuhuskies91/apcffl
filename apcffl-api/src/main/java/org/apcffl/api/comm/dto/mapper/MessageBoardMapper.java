package org.apcffl.api.comm.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.api.comm.dto.MessageBoard;
import org.apcffl.api.persistence.model.MessageBoardModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.springframework.util.CollectionUtils;

public class MessageBoardMapper {
	
	private MessageBoardMapper() {}
	
	public static List<MessageBoard> map(List<MessageBoardModel> models) {
		
		List<MessageBoard> result = new ArrayList<MessageBoard>();
		
		if (CollectionUtils.isEmpty(models)) {
			return result;
		}
		
		for (MessageBoardModel model : models) {
			MessageBoard messageBoard = new MessageBoard();
			messageBoard.setCreateDate(model.getCreateDate());
			messageBoard.setMessage(model.getMessage());
			
			OwnerModel owner = model.getOwnerModel();
			messageBoard.setUserName(owner.getUserModel().getUserName());
			messageBoard.setUserGroupName(model.getOwnerModel().getUserModel().getUserGroupModel().getUserGroupName());
			if (owner.getTeamModel() != null && owner.getTeamModel().getLeagueModel() != null) {
				messageBoard.setLeagueName(owner.getTeamModel().getLeagueModel().getLeagueName());
				result.add(messageBoard);
			}
		}
		return result;
	}

}
