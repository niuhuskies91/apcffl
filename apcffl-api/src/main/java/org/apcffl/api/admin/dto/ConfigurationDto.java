package org.apcffl.api.admin.dto;

import org.apcffl.api.dto.ApiRequest;

public class ConfigurationDto extends ApiRequest {

	private String configKey;
	private String configValue;
	private String configDesc;
	
	public ConfigurationDto(String configKey, String configValue, String configDesc) {
		this.configKey = configKey;
		this.configValue = configValue;
		this.configDesc = configDesc;
	}
	
	public String getConfigKey() {
		return configKey;
	}
	public String getConfigValue() {
		return configValue;
	}
	public String getConfigDesc() {
		return configDesc;
	}
}
