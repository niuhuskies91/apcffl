package org.apcffl.api.admin.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.persistence.model.ConfigModel;

public class AdminMapper {
	
	private AdminMapper() {}
	
	public static List<ConfigurationDto> convertConfigModel(List<ConfigModel> configList) {
		List<ConfigurationDto> configs = new ArrayList<>();
		for (ConfigModel model : configList) {
			ConfigurationDto config = 
					new ConfigurationDto(model.getConfigKey(), model.getConfigValue(), model.getConfigDesc());
			configs.add(config);
		}
		return configs;
	}

	public static List<ConfigModel> convertConfigDto(List<ConfigurationDto> configs) {
		List<ConfigModel> configList = new ArrayList<>();
		for (ConfigurationDto config : configs) {
			ConfigModel model = new ConfigModel();
			configList.add(model);
			model.setConfigDesc(config.getConfigDesc());
			model.setConfigKey(config.getConfigKey());
			model.setConfigValue(config.getConfigValue());
		}
		return configList;
	}
}
