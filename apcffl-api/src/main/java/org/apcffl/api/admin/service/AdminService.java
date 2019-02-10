package org.apcffl.api.admin.service;

import java.util.List;

import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.ConfigurationDto;
import org.apcffl.api.admin.dto.ConfigurationRetrievalRequest;
import org.apcffl.api.admin.dto.ConfigurationUpdateRequest;

public interface AdminService {

	public AccountResponse accountRetrieval(AccountRequest request);
	
	public void accountCreate(AccountRequest request);
	
	public void accountUpdate(AccountRequest request);
	
	public List<ConfigurationDto> configurationRetrieval(ConfigurationRetrievalRequest request);
	
	public void configurationUpdate(ConfigurationUpdateRequest request);
}
