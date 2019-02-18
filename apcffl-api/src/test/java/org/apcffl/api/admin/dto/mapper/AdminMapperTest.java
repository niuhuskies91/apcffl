package org.apcffl.api.admin.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.persistence.model.ConfigModel;
import org.apcffl.api.persistence.model.OwnerModel;
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
	
	@Test
	public void testConvertAccountModelNoLeague() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		owner.setLeagueModel(null);
		
		// invoke method
		
		AccountResponse response = AdminMapper.convertAccountModel(owner);
		
		// verify results
		
		assertEquals(response.getActiveFlag(), ApcfflTest.OWNER_ACTIVE);
		assertEquals(response.getEmail1(), ApcfflTest.OWNER_EMAIL1);
		assertEquals(response.getEmail2(), ApcfflTest.OWNER_EMAIL2);
		assertEquals(response.getEmail3(), ApcfflTest.OWNER_EMAIL3);
		assertEquals(response.getError(), null);
		assertEquals(response.getFirstName(), ApcfflTest.OWNER_FIRST_NAME);
		assertEquals(response.getLastName(), ApcfflTest.OWNER_LAST_NAME);
		assertEquals(response.getLeagueName(), null);
	}
	
	@Test
	public void testConvertAccountModel() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		
		// invoke method
		
		AccountResponse response = AdminMapper.convertAccountModel(owner);
		
		// verify results
		
		assertEquals(response.getActiveFlag(), ApcfflTest.OWNER_ACTIVE);
		assertEquals(response.getEmail1(), ApcfflTest.OWNER_EMAIL1);
		assertEquals(response.getEmail2(), ApcfflTest.OWNER_EMAIL2);
		assertEquals(response.getEmail3(), ApcfflTest.OWNER_EMAIL3);
		assertEquals(response.getError(), null);
		assertEquals(response.getFirstName(), ApcfflTest.OWNER_FIRST_NAME);
		assertEquals(response.getLastName(), ApcfflTest.OWNER_LAST_NAME);
		assertEquals(response.getLeagueName(), ApcfflTest.LEAGUE_1_NAME);
	}
	
	@Test
	public void testConvertAccountsNullOwners() {
		
		// prepare test data
		
		List<OwnerModel> owners = null;
		
		// invoke method
		
		List<AccountResponse> response = AdminMapper.convertAccounts(owners);
		
		// verify results
		
		assertEquals(response.size(), 0);
	}
	
	@Test
	public void testConvertAccountsEmptyListOwners() {
		
		// prepare test data
		
		List<OwnerModel> owners = new ArrayList<>();
		
		// invoke method
		
		List<AccountResponse> response = AdminMapper.convertAccounts(owners);
		
		// verify results
		
		assertEquals(response.size(), 0);
	}
	
	@Test
	public void testConvertAccounts() {
		
		// prepare test data
		
		List<OwnerModel> owners = new ArrayList<>();
		owners.add( ApcfflTest.buildOwnerModel() );
		
		// invoke method
		
		List<AccountResponse> response = AdminMapper.convertAccounts(owners);
		
		// verify results
		
		assertEquals(response.size(), 1);
		
		assertEquals(response.get(0).getActiveFlag(), ApcfflTest.OWNER_ACTIVE);
		assertEquals(response.get(0).getEmail1(), ApcfflTest.OWNER_EMAIL1);
		assertEquals(response.get(0).getEmail2(), ApcfflTest.OWNER_EMAIL2);
		assertEquals(response.get(0).getEmail3(), ApcfflTest.OWNER_EMAIL3);
		assertEquals(response.get(0).getError(), null);
		assertEquals(response.get(0).getFirstName(), ApcfflTest.OWNER_FIRST_NAME);
		assertEquals(response.get(0).getLastName(), ApcfflTest.OWNER_LAST_NAME);
		assertEquals(response.get(0).getLeagueName(), ApcfflTest.LEAGUE_1_NAME);
	}
}
