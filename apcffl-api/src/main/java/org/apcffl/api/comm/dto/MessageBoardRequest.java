package org.apcffl.api.comm.dto;

import java.util.Date;

import org.apcffl.api.dto.ApiRequest;

public class MessageBoardRequest extends ApiRequest {

	private Date startDate;
	private Date endDate;
	
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
