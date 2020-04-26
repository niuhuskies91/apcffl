package org.apcffl.api.service.manager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apcffl.api.dto.GroupActivityMapDto;
import org.apcffl.api.persistence.model.GroupActivityMapModel;
import org.apcffl.api.persistence.model.UserGroupModel;
import org.apcffl.api.persistence.repository.GroupActivityMapRepository;
import org.apcffl.api.persistence.repository.UserGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class StaticDataManager {

	@Autowired
	UserGroupRepository userGroupRepository;
	
	@Autowired
	GroupActivityMapRepository groupActivityRepository;
	
	private static Map<String, Long> userGroupMap;
	private static Map<String, GroupActivityMapDto> groupActivityMap;
	
	@PostConstruct
	public void init() {
		buildUserGroupMap();
		buildGroupActivityMap();
	}
	
	/**
	 * Static data with the complete list of User Group.
	 */
	private void buildUserGroupMap() {
		userGroupMap = new LinkedHashMap<>();
		
		List<UserGroupModel> results = userGroupRepository.findAll();
		if (!CollectionUtils.isEmpty(results)) {
			for (UserGroupModel model : results) {
				userGroupMap.put(model.getUserGroupName(), model.getUserGroupId());
			}
		}
	}
	
	/**
	 * Static data with the complete list of activities
	 * per user group.
	 */
	private void buildGroupActivityMap() {
		groupActivityMap = new LinkedHashMap<>();
		
		List<GroupActivityMapModel> results = groupActivityRepository.findAll();
		if (!CollectionUtils.isEmpty(results)) {
			for (GroupActivityMapModel model : results) {
				String groupName = model.getUserGroupModel().getUserGroupName();
				GroupActivityMapDto dto = null;
				
				if (!groupActivityMap.containsKey(groupName)) {
					 dto = new GroupActivityMapDto(groupName);
					 groupActivityMap.put(groupName, dto);
				} else {
					dto = groupActivityMap.get(groupName);
				}
				dto.getApplicationActivity().put(
						model.getApplicationActivityModel().getActivityName(),
						model.getApplicationActivityModel().getActivityDesc());
			}
		}
	}
	
	public static Map<String, Long> getUserGroupMap() {
		return userGroupMap;
	}
	
	public static Map<String, GroupActivityMapDto> getGroupActivityMap() {
		return groupActivityMap;
	}
}
