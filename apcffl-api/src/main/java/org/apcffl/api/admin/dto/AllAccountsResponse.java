package org.apcffl.api.admin.dto;

import java.util.List;

import org.apcffl.api.dto.ApiResponse;

public class AllAccountsResponse extends ApiResponse {
	private List<AccountResponse> accounts;

	public List<AccountResponse> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountResponse> accounts) {
		this.accounts = accounts;
	}
}
