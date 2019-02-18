package org.apcffl.api.admin.service.impl;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.admin.dto.ConfigurationResponse;
import org.apcffl.api.admin.dto.ConfigurationRetrievalRequest;
import org.apcffl.api.admin.dto.ConfigurationUpdateRequest;
import org.apcffl.api.config.ApiServiceConfig;
import org.apcffl.api.constants.UIMessages;
import org.apcffl.api.exception.constants.ErrorCodeEnums;
import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.model.OwnerModel;
import org.apcffl.api.persistence.repository.ConfigRepository;
import org.apcffl.api.persistence.repository.OwnerRepository;
import org.apcffl.api.security.constants.SecurityConstants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceImplTest {

	@InjectMocks
	private AdminServiceImpl service;

	@Mock
	private ConfigRepository configRepository;
	
	@Mock
	private OwnerRepository ownerRepository;

	@Mock
	private ApiServiceConfig config;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		config.init();
	}
	
	@Test
	public void testConfigurationRetrieval_notValidGroupTier() {
		
		// prepare test data
		
		ConfigurationRetrievalRequest request = new ConfigurationRetrievalRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		// invoke
		
		ConfigurationResponse response = service.configurationRetrieval(request);
		
		// verify results
		
		assertEquals(response.getError().getErrorCode(), ErrorCodeEnums.UserGroupAccessError.toString());
		assertEquals(response.getError().getMessage(), UIMessages.ERROR_USER_GROUP_ACCESS);
		assertEquals(response.getConfigs(), null);
	}
	
	@Test
	public void testConfigurationRetrieval() {
		
		// prepare test data
		
		ConfigurationRetrievalRequest request = new ConfigurationRetrievalRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_ADMIN);
		
		when(configRepository.findAll()).thenReturn(ApcfflTest.buildConfigModel());
		
		// invoke
		
		ConfigurationResponse response = service.configurationRetrieval(request);
		List<ConfigurationDto> dtoList = response.getConfigs();
		
		// verify results
		
		assertEquals(response.getError(), null);
		assertEquals(dtoList.size(), 2);
		assertEquals(dtoList.get(0).getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(dtoList.get(0).getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
		assertEquals(dtoList.get(0).getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
		assertEquals(dtoList.get(1).getConfigDesc(), ApcfflTest.CONFIG_PSWD_RESET_DESC);
		assertEquals(dtoList.get(1).getConfigKey(), ApcfflTest.CONFIG_PSWD_RESET_KEY);
		assertEquals(dtoList.get(1).getConfigValue(), ApcfflTest.CONFIG_PSWD_RESET_VAL);
	}
	
	@Test
	public void testConfigurationUpdate_notValidGroupTier() {
		
		// prepare test data
		
		ConfigurationUpdateRequest request = new ConfigurationUpdateRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		request.setConfigList(ApcfflTest.buildConfigDto());
		
		// invoke
		
		ConfigurationResponse response = service.configurationUpdate(request);
		
		// verify results
		
		assertEquals(response.getError().getErrorCode(), ErrorCodeEnums.UserGroupAccessError.toString());
		assertEquals(response.getError().getMessage(), UIMessages.ERROR_USER_GROUP_ACCESS);
		assertEquals(response.getConfigs(), null);
	}
	
	@Test
	public void testConfigurationUpdate() {
		
		// prepare test data
		
		ConfigurationUpdateRequest request = new ConfigurationUpdateRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_ADMIN);
		
		request.setConfigList(ApcfflTest.buildConfigDto());
		
		List<ConfigModel> mockModels = new ArrayList<>(ApcfflTest.buildConfigModel());
		when(configRepository.saveAll(any())).thenReturn(mockModels);
		
		// invoke
		
		ConfigurationResponse response = service.configurationUpdate(request);
		
		// verify results
		
		assertEquals(response.getError(), null);
	}
	
	@Test
	public void testAccountRetrieval_notValidGroupTier() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_GUEST);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		// invoke
		
		AccountResponse response = service.accountRetrieval(request);
		
		// verify results
		
		assertEquals(response.getError().getErrorCode(), ErrorCodeEnums.UserGroupAccessError.toString());
		assertEquals(response.getError().getMessage(), UIMessages.ERROR_USER_GROUP_ACCESS);
		assertEquals(response.getActiveFlag(), null);
		assertEquals(response.getEmail1(), null);
		assertEquals(response.getEmail2(), null);
		assertEquals(response.getEmail3(), null);
		assertEquals(response.getFirstName(), null);
		assertEquals(response.getLastName(), null);
		assertEquals(response.getLeagueName(), null);
	}
	
	@Test
	public void testAccountRetrieval_accountNotExist() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		OwnerModel mockOwner = null;
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke
		
		AccountResponse response = service.accountRetrieval(request);
		
		// verify results
		
		assertEquals(response.getError().getErrorCode(), ErrorCodeEnums.AccountError.toString());
		assertEquals(response.getError().getMessage(), UIMessages.ACCOUNT_NOT_FOUND);
		assertEquals(response.getActiveFlag(), null);
		assertEquals(response.getEmail1(), null);
		assertEquals(response.getEmail2(), null);
		assertEquals(response.getEmail3(), null);
		assertEquals(response.getFirstName(), null);
		assertEquals(response.getLastName(), null);
		assertEquals(response.getLeagueName(), null);
	}
	
	@Test
	public void testAccountRetrieval() {

		// prepare test data
		
		AccountRequest request = new AccountRequest();
		request.setUserGroupName(SecurityConstants.USER_GROUP_OWNER);
		
		request.setUserName(ApcfflTest.USER_NAME);
		
		OwnerModel mockOwner = ApcfflTest.buildOwnerModel();
		when(ownerRepository.findByUserName(anyString())).thenReturn(mockOwner);
		
		// invoke
		
		AccountResponse response = service.accountRetrieval(request);
		
		// verify results
		
		assertEquals(response.getError(), null);
		assertEquals(response.getActiveFlag(), ApcfflTest.OWNER_ACTIVE);
		assertEquals(response.getEmail1(), ApcfflTest.OWNER_EMAIL1);
		assertEquals(response.getEmail2(), ApcfflTest.OWNER_EMAIL2);
		assertEquals(response.getEmail3(), ApcfflTest.OWNER_EMAIL3);
		assertEquals(response.getFirstName(), ApcfflTest.OWNER_FIRST_NAME);
		assertEquals(response.getLastName(), ApcfflTest.OWNER_LAST_NAME);
		assertEquals(response.getLeagueName(), ApcfflTest.LEAGUE_1_NAME);
	}
	
	@Test
	public void testAccountRetrieveAll_notValidGroupTier() {
		k
	}
}
