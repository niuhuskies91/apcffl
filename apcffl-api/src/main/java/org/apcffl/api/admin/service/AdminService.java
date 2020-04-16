package org.apcffl.api.admin.service;

import org.apcffl.api.admin.dto.AccountCreateRequest;
import org.apcffl.api.admin.dto.AccountRequest;
import org.apcffl.api.admin.dto.AccountResponse;
import org.apcffl.api.admin.dto.AllAccountsResponse;

public interface AdminService {

	public AllAccountsResponse accountRetrievalAll(AccountRequest request);
	
	public AccountResponse accountRetrieval(AccountRequest request);
	
	public String accountCreate(AccountCreateRequest request);
	
	public AccountResponse accountUpdate(AccountRequest request);
}
