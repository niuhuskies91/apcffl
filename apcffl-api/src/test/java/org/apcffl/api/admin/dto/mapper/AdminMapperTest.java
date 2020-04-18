package org.apcffl.api.admin.dto.mapper;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apcffl.ApcfflTest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.persistence.model.OwnerModel;
import org.junit.Test;

public class AdminMapperTest {
	
	@Test
	public void verify_convertAccountModel_noLeague() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		owner.setLeagueModel(null);
		
		// invoke method
		
		AccountResponse response = AdminMapper.convertAccountModel(owner);
		
		// verify results
		
		assertEquals(ApcfflTest.OWNER_ACTIVE,response.getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.getEmail1());
		assertEquals(ApcfflTest.OWNER_EMAIL2, response.getEmail2());
		assertEquals(ApcfflTest.OWNER_EMAIL3, response.getEmail3());
		assertEquals(null, response.getError());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.getLastName());
		assertEquals(null, response.getLeagueName());
	}
	
	@Test
	public void verify_convertAccountModel() {
		
		// prepare test data
		
		OwnerModel owner = ApcfflTest.buildOwnerModel();
		
		// invoke method
		
		AccountResponse response = AdminMapper.convertAccountModel(owner);
		
		// verify results
		
		assertEquals(ApcfflTest.OWNER_ACTIVE,response.getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.getEmail1());
		assertEquals(ApcfflTest.OWNER_EMAIL2, response.getEmail2());
		assertEquals(ApcfflTest.OWNER_EMAIL3, response.getEmail3());
		assertEquals(null, response.getError());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.getLastName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, response.getLeagueName());
	}
	
	@Test
	public void verify_convertAccounts_nullOwners() {
		
		// prepare test data
		
		List<OwnerModel> owners = null;
		
		// invoke method
		
		List<AccountResponse> response = AdminMapper.convertAccounts(owners);
		
		// verify results
		
		assertEquals(0, response.size());
	}
	
	@Test
	public void verify_convertAccounts_emptyListOwners() {
		
		// prepare test data
		
		List<OwnerModel> owners = new ArrayList<>();
		
		// invoke method
		
		List<AccountResponse> response = AdminMapper.convertAccounts(owners);
		
		// verify results
		
		assertEquals(0, response.size());
	}
	
	@Test
	public void verify_convertAccounts() {
		
		// prepare test data
		
		List<OwnerModel> owners = new ArrayList<>();
		owners.add( ApcfflTest.buildOwnerModel() );
		
		// invoke method
		
		List<AccountResponse> response = AdminMapper.convertAccounts(owners);
		
		// verify results
		
		assertEquals(1, response.size());
		
		assertEquals(ApcfflTest.OWNER_ACTIVE, response.get(0).getActiveFlag());
		assertEquals(ApcfflTest.OWNER_EMAIL1, response.get(0).getEmail1());
		assertEquals(ApcfflTest.OWNER_EMAIL2, response.get(0).getEmail2());
		assertEquals(ApcfflTest.OWNER_EMAIL3, response.get(0).getEmail3());
		assertEquals(null, response.get(0).getError());
		assertEquals(ApcfflTest.OWNER_FIRST_NAME, response.get(0).getFirstName());
		assertEquals(ApcfflTest.OWNER_LAST_NAME, response.get(0).getLastName());
		assertEquals(ApcfflTest.LEAGUE_1_NAME, response.get(0).getLeagueName());
	}
}
