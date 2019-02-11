package org.apcffl.api.admin.service.impl;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.admin.dto.ConfigurationRetrievalRequest;
import org.apcffl.api.config.ApiServiceConfig;
import org.apcffl.api.persistence.repository.ConfigRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

public class AdminServiceImplTest {

	@InjectMocks
	private AdminServiceImpl service;

	@Mock
	private ConfigRepository configRepository;

	@Mock
	private ApiServiceConfig config;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		config.init();
	}
	
	@Test(expected = org.apcffl.api.exception.SecurityException.class)
	public void testConfigurationRetrievalNotAdmin() {
		
		// prepare test data
		
		ConfigurationRetrievalRequest request = new ConfigurationRetrievalRequest();
		request.setUserGroupName(ApcfflTest.USER_GROUP_OWNER);
		request.setUserName(ApcfflTest.USER_NAME);
		request.setSecurityToken(ApcfflTest.TEST_TOKEN);
		
		// invoke
		
		service.configurationRetrieval(request);
	}
	
	@Test
	public void testConfigurationRetrieval() {
		
		// prepare test data
		
		ConfigurationRetrievalRequest request = new ConfigurationRetrievalRequest();
		request.setUserGroupName(ApcfflTest.USER_GROUP_ADMIN);
		
		when(configRepository.findAll()).thenReturn(ApcfflTest.buildConfigModel());
		
		// invoke
		
		List<ConfigurationDto> dtoList = service.configurationRetrieval(request);
		
		// verify results
		
		assertEquals(dtoList.size(), 2);
		assertEquals(dtoList.get(0).getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(dtoList.get(0).getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
		assertEquals(dtoList.get(0).getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
		assertEquals(dtoList.get(1).getConfigDesc(), ApcfflTest.CONFIG_PSWD_RESET_DESC);
		assertEquals(dtoList.get(1).getConfigKey(), ApcfflTest.CONFIG_PSWD_RESET_KEY);
		assertEquals(dtoList.get(1).getConfigValue(), ApcfflTest.CONFIG_PSWD_RESET_VAL);
		
	}
}
