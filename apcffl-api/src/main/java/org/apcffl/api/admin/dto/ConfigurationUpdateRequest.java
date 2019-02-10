package org.apcffl.api.admin.dto;

import java.util.List;

import org.apcffl.api.dto.ApiRequest;

public class ConfigurationUpdateRequest extends ApiRequest {
	List<ConfigurationDto> configList;

	public List<ConfigurationDto> getConfigList() {
		return configList;
	}

	public void setConfigList(List<ConfigurationDto> configList) {
		this.configList = configList;
	}
}
