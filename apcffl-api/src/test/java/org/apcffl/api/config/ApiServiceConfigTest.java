package org.apcffl.api.config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apcffl.ApcfflTest;
import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.repository.ConfigRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ApiServiceConfigTest {

	@Mock
	ConfigRepository repository;

	@InjectMocks
	@Resource
	ApiServiceConfig config;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		List<ConfigModel> mockConfig = ApcfflTest.buildConfigModel();
		when(repository.findAll()).thenReturn(mockConfig);
		
		config.init();
	}
    
    @Test
    public void testProperties() {
    	ConfigModel model = config.retrieveProperty(ApcfflTest.CONFIG_SESSION_KEY);
    	assertEquals(model.getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
    	assertEquals(model.getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
    	assertEquals(model.getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
    }
    
    @Test
    public void testRetrieveAll() {
    	Map<String, ConfigModel> models = config.retrieveAll();
    	assertEquals(models.size(), 2);
    	String key = ApcfflTest.CONFIG_SESSION_KEY;
    	assertEquals(models.get(key).getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
    	assertEquals(models.get(key).getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
    	assertEquals(models.get(key).getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
    	key = ApcfflTest.CONFIG_PSWD_RESET_KEY;
    	assertEquals(models.get(key).getConfigKey(), ApcfflTest.CONFIG_PSWD_RESET_KEY);
    	assertEquals(models.get(key).getConfigValue(), ApcfflTest.CONFIG_PSWD_RESET_VAL);
    	assertEquals(models.get(key).getConfigDesc(), ApcfflTest.CONFIG_PSWD_RESET_DESC);
    }
    
    @Test
    public void testUpdateProperty() {
    	config.updateProperty(ApcfflTest.CONFIG_SESSION_KEY, ApcfflTest.CONFIG_SESSION_VAL, ApcfflTest.CONFIG_SESSION_DESC);
    }
}
