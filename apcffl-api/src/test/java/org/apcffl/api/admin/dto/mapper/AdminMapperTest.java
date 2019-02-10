package org.apcffl.api.admin.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.persistence.model.ConfigModel;
import org.junit.Test;

public class AdminMapperTest {

	@Test
	public void testConvertConfig() {
		
		// test convert from Model to Dto
		
		List<ConfigModel> modelList = 
				ApcfflTest.buildConfigModel();
		
		List<ConfigurationDto> dtoList = 
				AdminMapper.convertConfigModel(modelList);
		
		assertEquals(dtoList.size(), 2);
		assertEquals(dtoList.get(0).getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(dtoList.get(0).getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
		assertEquals(dtoList.get(0).getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
		assertEquals(dtoList.get(1).getConfigDesc(), ApcfflTest.CONFIG_PSWD_RESET_DESC);
		assertEquals(dtoList.get(1).getConfigKey(), ApcfflTest.CONFIG_PSWD_RESET_KEY);
		assertEquals(dtoList.get(1).getConfigValue(), ApcfflTest.CONFIG_PSWD_RESET_VAL);
		
		// test covert from Dto to Model
		
		List<ConfigModel> convertedModel =
				AdminMapper.convertConfigDto(dtoList);
		
		assertEquals(convertedModel.size(), 2);
		assertEquals(convertedModel.get(0).getConfigDesc(), ApcfflTest.CONFIG_SESSION_DESC);
		assertEquals(convertedModel.get(0).getConfigKey(), ApcfflTest.CONFIG_SESSION_KEY);
		assertEquals(convertedModel.get(0).getConfigValue(), ApcfflTest.CONFIG_SESSION_VAL);
		assertEquals(convertedModel.get(1).getConfigDesc(), ApcfflTest.CONFIG_PSWD_RESET_DESC);
		assertEquals(convertedModel.get(1).getConfigKey(), ApcfflTest.CONFIG_PSWD_RESET_KEY);
		assertEquals(convertedModel.get(1).getConfigValue(), ApcfflTest.CONFIG_PSWD_RESET_VAL);
	}
}
