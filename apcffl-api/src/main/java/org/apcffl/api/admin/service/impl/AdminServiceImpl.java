package org.apcffl.api.admin.service.impl;

import java.util.List;

import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.admin.dto.ConfigurationRetrievalRequest;
import org.apcffl.api.admin.dto.ConfigurationUpdateRequest;
import org.apcffl.api.admin.dto.mapper.AdminMapper;
import org.apcffl.api.admin.service.AdminService;
import org.apcffl.api.config.ApiServiceConfig;
import org.apcffl.api.exception.SecurityException;
import org.apcffl.api.exception.constants.AppAccessConstants;
import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.repository.ConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger LOG = LoggerFactory.getLogger(AdminServiceImpl.class);

	private ConfigRepository configRepository;
	
	private ApiServiceConfig config;
	
	public AdminServiceImpl(ConfigRepository configRepository, ApiServiceConfig config) {
		this.configRepository = configRepository;
		this.config = config;
	}

	@Override
	public AccountResponse accountRetrieval(AccountRequest request) {
		
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void accountCreate(AccountRequest request) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void accountUpdate(AccountRequest request) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ConfigurationDto> configurationRetrieval(ConfigurationRetrievalRequest request) {
		
		if (!AppAccessConstants.USER_GROUP_ADMIN.equals(request.getUserGroupName())) {
			String error = "You do not have Administrative access.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		List<ConfigModel> configList = configRepository.findAll();

		return AdminMapper.convertConfigModel(configList);
	}

	@Override
	public void configurationUpdate(ConfigurationUpdateRequest request) {
		
		if (!AppAccessConstants.USER_GROUP_ADMIN.equals(request.getUserGroupName())) {
			String error = "You do not have Administrative access.";
			LOG.error(error);
			throw new SecurityException(error);
		}
		// persist the configuration changes to the DB
		configRepository.saveAll( AdminMapper.convertConfigDto(request.getConfigList()) );
		
		// reload the configurations to the config cache
		config.reloadMap();
	}
}
