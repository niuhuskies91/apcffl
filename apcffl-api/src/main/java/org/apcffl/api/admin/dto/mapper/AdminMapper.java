package org.apcffl.api.admin.dto.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.springframework.util.CollectionUtils;

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
	
	public static AccountResponse convertAccountModel(OwnerModel owner) {
		AccountResponse dto = new AccountResponse();
		dto.setActiveFlag(owner.getActiveFlag());
		dto.setEmail1(owner.getEmail1());
		dto.setEmail2(owner.getEmail2());
		dto.setEmail3(owner.getEmail3());
		dto.setFirstName(owner.getFirstName());
		dto.setLastName(owner.getLastName());
		if (owner.getLeagueModel() != null) {
			dto.setLeagueName(owner.getLeagueModel().getLeagueName());
		}
		return dto;
	}
	
	public static List<AccountResponse> convertAccounts(List<OwnerModel> owners) {
		List<AccountResponse> accounts = new ArrayList<>();
		if (CollectionUtils.isEmpty(owners)) {
			return accounts;
		}
		for (OwnerModel owner : owners) {
			accounts.add( convertAccountModel(owner) );
		}
		return accounts;
	}
}
