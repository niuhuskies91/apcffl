package org.apcffl.api.admin.dto;

import java.util.List;

import org.apcffl.api.dto.ApiResponse;

public class ConfigurationResponse extends ApiResponse {

	private List<ConfigurationDto> configs;

	public List<ConfigurationDto> getConfigs() {
		return configs;
	}

	public void setConfigs(List<ConfigurationDto> configs) {
		this.configs = configs;
	}
}
