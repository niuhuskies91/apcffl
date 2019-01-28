package org.apcffl.api.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public class GroupActivityMapDto {
	
	private String groupName;
	private Map<String,String> applicationActivity; // key : activityName , value: activityDescription
	
	public GroupActivityMapDto(String groupName) {
		this.groupName = groupName;
		applicationActivity = new LinkedHashMap<>();
	}

	public String getGroupName() {
		return groupName;
	}

	public Map<String, String> getApplicationActivity() {
		return applicationActivity;
	}

	
}
